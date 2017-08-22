package com.tongwii.ico.controller;

import com.alibaba.druid.support.logging.Log;
import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.ProjectUserWalletRelation;
import com.tongwii.ico.service.ProjectUserWalletRelationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongwii.ico.util.ContextUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/project/user/wallet/relation")
public class ProjectUserWalletRelationController {
    @Resource
    private ProjectUserWalletRelationService projectUserWalletRelationService;

    @PostMapping
    public Result add(@RequestBody ProjectUserWalletRelation projectUserWalletRelation) {
        projectUserWalletRelationService.save(projectUserWalletRelation);
        return Result.successResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        projectUserWalletRelationService.deleteById(id);
        return Result.successResult();
    }

    @PutMapping
    public Result update(@RequestBody ProjectUserWalletRelation projectUserWalletRelation) {
        projectUserWalletRelationService.update(projectUserWalletRelation);
        return Result.successResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ProjectUserWalletRelation projectUserWalletRelation = projectUserWalletRelationService.findById(id);
        return Result.successResult(projectUserWalletRelation);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ProjectUserWalletRelation> list = projectUserWalletRelationService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }
    @GetMapping("/getUserTransaction")
    public Result getUserTransaction(){
//        Integer userId = ContextUtils.getUserId();
        Integer userId = 3;
        // 根据用户Id查询用户项目钱包的交易信息
        List<ProjectUserWalletRelation> projectUserWalletRelationList = projectUserWalletRelationService.findUserTransactionList(userId);
        if(!CollectionUtils.isEmpty(projectUserWalletRelationList)){
           for(int i=0; i<projectUserWalletRelationList.size(); i++){
                try{
                   //TODO 此处需要根据交易编号查询交易记录的详细信息
                    projectUserWalletRelationList.get(i).getTransactionNumber();
                }catch (Exception e){
                    System.out.print("交易单号不存在或非法!");
                }
            }

            return Result.successResult(projectUserWalletRelationList);
        }else{
            return Result.failResult("投资记录获取失败!");
        }

    }
}
