package com.tongwii.ico.controller;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.TokenMoney;
import com.tongwii.ico.service.TokenMoneyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/token/money")
public class TokenMoneyController {
    @Resource
    private TokenMoneyService tokenMoneyService;

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

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TokenMoney> list = tokenMoneyService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }
}
