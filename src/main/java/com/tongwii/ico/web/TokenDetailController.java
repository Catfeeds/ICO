package com.tongwii.ico.web;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.core.ResultGenerator;
import com.tongwii.ico.model.TokenDetail;
import com.tongwii.ico.service.TokenDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        tokenDetailService.save(tokenDetail);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        tokenDetailService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody TokenDetail tokenDetail) {
        tokenDetailService.update(tokenDetail);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        TokenDetail tokenDetail = tokenDetailService.findById(id);
        return ResultGenerator.genSuccessResult(tokenDetail);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TokenDetail> list = tokenDetailService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
