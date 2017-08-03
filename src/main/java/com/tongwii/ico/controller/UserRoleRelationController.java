package com.tongwii.ico.controller;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.UserRoleRelation;
import com.tongwii.ico.service.UserRoleRelationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/user/role/relation")
public class UserRoleRelationController {
    @Resource
    private UserRoleRelationService userRoleRelationService;

    @PostMapping
    public Result add(@RequestBody UserRoleRelation userRoleRelation) {
        userRoleRelationService.save(userRoleRelation);
        return Result.successResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userRoleRelationService.deleteById(id);
        return Result.successResult();
    }

    @PutMapping
    public Result update(@RequestBody UserRoleRelation userRoleRelation) {
        userRoleRelationService.update(userRoleRelation);
        return Result.successResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        UserRoleRelation userRoleRelation = userRoleRelationService.findById(id);
        return Result.successResult(userRoleRelation);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<UserRoleRelation> list = userRoleRelationService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }
}
