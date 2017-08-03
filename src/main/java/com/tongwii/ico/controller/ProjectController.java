package com.tongwii.ico.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.Project;
import com.tongwii.ico.service.ProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Resource
    private ProjectService projectService;

    @PostMapping
    public Result add(@RequestBody Project project) {
        projectService.save(project);
        return Result.successResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        projectService.deleteById(id);
        return Result.successResult();
    }

    @PutMapping
    public Result update(@RequestBody Project project) {
        projectService.update(project);
        return Result.successResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Project project = projectService.findById(id);
        return Result.successResult(project);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Project> list = projectService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }

    /*
    * 返回给首页的数据
    */
    @GetMapping("/findProjectsAndSort")
    public Result findProjectsAndSort(){
        List<Project> projectList = projectService.findAll();

        List<Project> ICOList = new ArrayList<>(); // ICO中的数据列表
        List<Project> willICOList = new ArrayList<>(); // 即将进行ICO中的数据列表
        List<Project> finishICOList = new ArrayList<>(); // 结束ICO的数据列表
        for (int i =0; i<projectList.size();i++){
            if(projectList.get(i).getState()==0){
                ICOList.add(projectList.get(i));
            }
            if(projectList.get(i).getState()==1){
                willICOList.add(projectList.get(i));
            }
            if(projectList.get(i).getState()==2){
                finishICOList.add(projectList.get(i));
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ICO",ICOList);
        jsonObject.put("willICO", willICOList);
        jsonObject.put("finishICO", finishICOList);
        return Result.successResult(jsonObject);
    }

    /*
    * 根据项目状态查询项目信息
    */
    @RequestMapping("/findProjectByState")
    public Result findProjectsByState(@RequestParam(required = true,defaultValue = "3") Integer state,
                                      @RequestParam(required = true,defaultValue = "0") Integer page,
                                      @RequestParam(required = true,defaultValue = "1") Integer size){
        PageHelper.startPage(page, size);
        List<Project> projectList = projectService.findAll();
        if(!state.equals("3")){
            // 分类查询
            List subProjects = new ArrayList();
            for (int i = 0; i<projectList.size(); i++){
                if(projectList.get(i).getState().equals(state)){
                    subProjects.add(projectList.get(i));
                }
            }
            PageInfo pageInfo = new PageInfo(subProjects);
            return Result.successResult(pageInfo);
        }else{
            PageInfo pageInfo = new PageInfo(projectList);
            return Result.successResult(pageInfo);
        }
    }
}
