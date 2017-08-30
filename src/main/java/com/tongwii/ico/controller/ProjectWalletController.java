package com.tongwii.ico.controller;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.ProjectWallet;
import com.tongwii.ico.service.ProjectWalletService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongwii.ico.util.OperatorRecordUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/project/wallet")
public class ProjectWalletController {
    @Resource
    private ProjectWalletService projectWalletService;

    @PostMapping
    public Result add(@RequestBody ProjectWallet projectWallet) {
        projectWalletService.save(projectWallet);
        OperatorRecordUtil.record("新增项目钱包， 钱包id" + projectWallet.getId());
        return Result.successResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        projectWalletService.deleteById(id);
        OperatorRecordUtil.record("删除项目钱包, 钱包id" + id);
        return Result.successResult();
    }

    @PutMapping
    public Result update(@RequestBody ProjectWallet projectWallet) {
        projectWalletService.update(projectWallet);
        OperatorRecordUtil.record("更新用户钱包信息，钱包id" + projectWallet.getId());
        return Result.successResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ProjectWallet projectWallet = projectWalletService.findById(id);
        return Result.successResult(projectWallet);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ProjectWallet> list = projectWalletService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }
}
