package com.tongwii.ico.controller;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.Project;
import com.tongwii.ico.model.ProjectUserRelation;
import com.tongwii.ico.service.ProjectService;
import com.tongwii.ico.service.ProjectUserRelationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongwii.ico.service.impl.projectFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/project/user/relation")
public class ProjectUserRelationController {
    @Resource
    private ProjectUserRelationService projectUserRelationService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private projectFileServiceImpl projectFileService;

    @PostMapping
    public Result add(@RequestBody ProjectUserRelation projectUserRelation) {
        projectUserRelationService.save(projectUserRelation);
        return Result.successResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        projectUserRelationService.deleteById(id);
        return Result.successResult();
    }

    @PutMapping
    public Result update(@RequestBody ProjectUserRelation projectUserRelation) {
        projectUserRelationService.update(projectUserRelation);
        return Result.successResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ProjectUserRelation projectUserRelation = projectUserRelationService.findById(id);
        return Result.successResult(projectUserRelation);
    }

    @GetMapping("/getUserProject/{userId}")
    public Result getUserProject(@RequestParam(required = true,defaultValue = "0") Integer page,
                                 @RequestParam(required = true,defaultValue = "4") Integer size,
                                 @PathVariable Integer userId) {

        List<ProjectUserRelation> projectUserRelations = projectUserRelationService.findByUserId(userId);
        PageHelper.startPage(page, size);
        List<Project> projectList = new ArrayList<>();
        for (int i=0; i<projectUserRelations.size(); i++){
            Project project = projectService.findById(projectUserRelations.get(i).getProjectId());
            Integer projectId = project.getId();
            // 获取项目图片
            String pictureUrl = projectFileService.findProjectFileByType(projectId,"image").getFileUrl();
            project.setPictureUrl(pictureUrl);
            projectList.add(project);
        }
        PageInfo pageInfo = new PageInfo(projectList);
        return Result.successResult(pageInfo);
    }
    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ProjectUserRelation> list = projectUserRelationService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }
}
