package com.tongwii.ico.web;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.UserWallet;
import com.tongwii.ico.service.UserWalletService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/user/wallet")
public class UserWalletController {
    @Resource
    private UserWalletService userWalletService;

    @PostMapping
    public Result add(@RequestBody UserWallet userWallet) {
        userWalletService.save(userWallet);
        return Result.successResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userWalletService.deleteById(id);
        return Result.successResult();
    }

    @PutMapping
    public Result update(@RequestBody UserWallet userWallet) {
        userWalletService.update(userWallet);
        return Result.successResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        UserWallet userWallet = userWalletService.findById(id);
        return Result.successResult(userWallet);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<UserWallet> list = userWalletService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }
}
