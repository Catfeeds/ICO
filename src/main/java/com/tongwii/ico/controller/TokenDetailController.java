package com.tongwii.ico.controller;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.TokenDetail;
import com.tongwii.ico.service.TokenDetailService;
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
@RequestMapping("/token/detail")
public class TokenDetailController {
    @Resource
    private TokenDetailService tokenDetailService;

    @PostMapping
    public Result add(@RequestBody TokenDetail tokenDetail) {
        // 通过传来的tokenmoneyId来获取
        tokenDetailService.save(tokenDetail);
        OperatorRecordUtil.record("新增代币详情， 代币id" + tokenDetail.getId());
        return Result.successResult(tokenDetail);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        tokenDetailService.deleteById(id);
        OperatorRecordUtil.record("删除代币详情， 代币id" + id);
        return Result.successResult();
    }

    @PutMapping
    public Result update(@RequestBody TokenDetail tokenDetail) {
        tokenDetailService.update(tokenDetail);
        OperatorRecordUtil.record("修改代币详情， 代币id" + tokenDetail.getId());
        return Result.successResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        TokenDetail tokenDetail = tokenDetailService.findById(id);
        return Result.successResult(tokenDetail);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TokenDetail> list = tokenDetailService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }
}
