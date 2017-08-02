package com.tongwii.ico.web;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.core.ResultGenerator;
import com.tongwii.ico.model.ProjectUserWalletRelation;
import com.tongwii.ico.service.ProjectUserWalletRelationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        projectUserWalletRelationService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody ProjectUserWalletRelation projectUserWalletRelation) {
        projectUserWalletRelationService.update(projectUserWalletRelation);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ProjectUserWalletRelation projectUserWalletRelation = projectUserWalletRelationService.findById(id);
        return ResultGenerator.genSuccessResult(projectUserWalletRelation);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ProjectUserWalletRelation> list = projectUserWalletRelationService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
