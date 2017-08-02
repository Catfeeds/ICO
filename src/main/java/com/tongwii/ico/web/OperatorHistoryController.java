package com.tongwii.ico.web;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.core.ResultGenerator;
import com.tongwii.ico.model.OperatorHistory;
import com.tongwii.ico.service.OperatorHistoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/operator/history")
public class OperatorHistoryController {
    @Resource
    private OperatorHistoryService operatorHistoryService;

    @PostMapping
    public Result add(@RequestBody OperatorHistory operatorHistory) {
        operatorHistoryService.save(operatorHistory);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        operatorHistoryService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody OperatorHistory operatorHistory) {
        operatorHistoryService.update(operatorHistory);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        OperatorHistory operatorHistory = operatorHistoryService.findById(id);
        return ResultGenerator.genSuccessResult(operatorHistory);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<OperatorHistory> list = operatorHistoryService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
