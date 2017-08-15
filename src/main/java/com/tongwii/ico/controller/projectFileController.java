package com.tongwii.ico.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.projectFile;
import com.tongwii.ico.service.projectFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* Created by Zeral on 2017-08-15.
*/
@RestController
@RequestMapping("/project/file")
public class projectFileController {
    @Autowired
    private projectFileService projectFileService;

    @PostMapping
    public Result add(@RequestBody projectFile projectFile) {
        projectFileService.save(projectFile);
        return Result.successResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        projectFileService.deleteById(id);
        return Result.successResult();
    }

    @PutMapping
    public Result update(@RequestBody projectFile projectFile) {
        projectFileService.update(projectFile);
        return Result.successResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        projectFile projectFile = projectFileService.findById(id);
        return Result.successResult(projectFile);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<projectFile> list = projectFileService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }
}
