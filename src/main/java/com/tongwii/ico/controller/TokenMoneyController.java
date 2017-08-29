package com.tongwii.ico.controller;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.*;
import com.tongwii.ico.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongwii.ico.util.ContextUtils;
import com.tongwii.ico.util.TokenMoneyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
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
    private UserProjectInvestRecordService userProjectInvestRecordService;

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

    /**
     * @author Yamo ...
     * 锁定项目接口
     *
     * @param projectInvestmentInfo
     * @return
     */
    @PostMapping("/transaction")
    public Result projectInvestment(@RequestBody Map<Object,String> projectInvestmentInfo){
        try{
            Integer userId = ContextUtils.getUserId();
            Integer coinId = Integer.parseInt(projectInvestmentInfo.get("coinId"));
            Integer projectId = Integer.valueOf(projectInvestmentInfo.get("projectId"));
            String investmentMoney = projectInvestmentInfo.get("investmentMoney");
            // **
            userProjectInvestRecordService.saveRecordAndUpdateProjectProjess(userId, coinId, projectId, investmentMoney);
            return Result.successResult("锁定项目成功");
        }catch (Exception e){
            return Result.errorResult("锁定项目失败!");
        }
    }
}
