package com.tongwii.ico.web;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.core.ResultGenerator;
import com.tongwii.ico.model.ProjectUserRelation;
import com.tongwii.ico.service.ProjectUserRelationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/project/user/relation")
public class ProjectUserRelationController {
    @Resource
    private ProjectUserRelationService projectUserRelationService;

    @PostMapping
    public Result add(@RequestBody ProjectUserRelation projectUserRelation) {
        projectUserRelationService.save(projectUserRelation);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        projectUserRelationService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody ProjectUserRelation projectUserRelation) {
        projectUserRelationService.update(projectUserRelation);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ProjectUserRelation projectUserRelation = projectUserRelationService.findById(id);
        return ResultGenerator.genSuccessResult(projectUserRelation);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ProjectUserRelation> list = projectUserRelationService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
