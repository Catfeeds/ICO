package com.tongwii.ico.controller;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.*;
import com.tongwii.ico.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongwii.ico.util.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/token/money")
public class TokenMoneyController {
    @Resource
    private TokenMoneyService tokenMoneyService;
    @Autowired
    private ProjectUserRelationService projectUserRelationService;
    @Autowired
    private ProjectWalletService projectWalletService;
    @Autowired
    private UserWalletService userWalletService;
    @Autowired
    private ProjectUserWalletRelationService projectUserWalletRelationService;

    @PostMapping
    public Result add(@RequestBody TokenMoney tokenMoney) {
        tokenMoneyService.save(tokenMoney);
        return Result.successResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        tokenMoneyService.deleteById(id);
        return Result.successResult();
    }

    @PutMapping
    public Result update(@RequestBody TokenMoney tokenMoney) {
        tokenMoneyService.update(tokenMoney);
        return Result.successResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        TokenMoney tokenMoney = tokenMoneyService.findById(id);
        return Result.successResult(tokenMoney);
    }

    @GetMapping("/ICO/{ICOname}")
    public Result detail(@PathVariable String ICOname) {
        TokenMoney tokenMoney = tokenMoneyService.findByName(ICOname);
        return Result.successResult(tokenMoney);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TokenMoney> list = tokenMoneyService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }

    @GetMapping("/findAll")
    public Result ICOList() {
        List<TokenMoney> list = tokenMoneyService.findAll();
        return Result.successResult(list);
    }

    // 交易接口
    @GetMapping("/transaction")
    public Result projectInvestment(@RequestParam Map<Object,Object> projectInvestmentInfo){
        try{
            Integer userId = ContextUtils.getUserId();
            String cionType = projectInvestmentInfo.get("cionType").toString();
            Integer projectId = (Integer) projectInvestmentInfo.get("projectId");
            // 给project_user表中增加记录
            ProjectUserRelation projectUserRelation = new ProjectUserRelation();
            projectUserRelation.setUserId(userId);
            projectUserRelation.setProjectId(projectId);
            projectUserRelationService.save(projectUserRelation);
            // 获取币种的id
            Integer cionId = tokenMoneyService.findByName(cionType).getId();
            // 根据币种id与projectId查询projectWalletURL
            ProjectWallet projectWallet = projectWalletService.findWalletByCionId(cionId,projectId);
            Integer projectWalletId = projectWallet.getId();
            // 根据币种ID与用户ID查询userWalletUrl
            UserWallet userWallet = userWalletService.findWalletByCionId(cionId, userId);
            Integer userWalletId = userWallet.getId();
            // TODO 此处估计要对用户与项目钱包各自进行增值与减值操作
            // 给user_project_wallet表中添加数据
            ProjectUserWalletRelation projectUserWalletRelation = new ProjectUserWalletRelation();
            projectUserWalletRelation.setProjectWallet(projectWalletId);
            projectUserWalletRelation.setUserWallet(userWalletId);
            return Result.successResult("锁定项目成功!");
        }catch (Exception e){
            return Result.errorResult("锁定项目失败!");
        }
    }
}
