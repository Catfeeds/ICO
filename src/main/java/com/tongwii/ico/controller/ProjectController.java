package com.tongwii.ico.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.Project;
import com.tongwii.ico.model.TokenDetail;
import com.tongwii.ico.model.TokenMoney;
import com.tongwii.ico.model.User;
import com.tongwii.ico.service.ProjectService;
import com.tongwii.ico.service.TokenDetailService;
import com.tongwii.ico.service.TokenMoneyService;
import com.tongwii.ico.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Resource
    private ProjectService projectService;
    @Autowired
    private TokenDetailService tokenDetailService;
    @Autowired
    private TokenMoneyService tokenMoneyService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public Result add(@RequestBody Project project) {
        projectService.save(project);
        return Result.successResult();
    }

    /**
     * Update input token money of Project.
     *
     * @param id          the id
     * @param tokenDetail the token detail
     * @return the result
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/inputTokenMoney")
    public Result updateInputTokenMoney(@PathVariable Integer id, @RequestParam("tokenDetail") TokenDetail tokenDetail) {
        try {
            // TODO 需要重写，目标代币为多个
//            projectService.updateInputTokenMoney(id, tokenDetail);
        } catch (Exception e) {
            return Result.errorResult("更新目标代币信息失败");
        }
        return Result.successResult("更新目标代币信息成功");
    }

    /**
     * Update out put token money of Project.
     *
     * @param id          the id
     * @param tokenDetail the token detail
     * @return the result
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/outputTokenMoney")
    public Result updateOutPutTokenMoney(@PathVariable Integer id, @RequestParam("tokenDetail") TokenDetail tokenDetail) {
        try {
            projectService.updateOutputTokenMoney(id, tokenDetail);
        } catch (Exception e) {
            return Result.errorResult("更新发行代币信息失败");
        }
        return Result.successResult("更新发行代币信息成功");
    }

    /**
     * Update create user of Project.
     *
     * @param id   the id
     * @param user the user
     * @return the result
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/createUser")
    public Result updateCreateUser(@PathVariable Integer id, @RequestParam("user") User user) {
        try {
            projectService.updateCreateUser(id, user);
        } catch (Exception e) {
            return Result.errorResult("更新用户信息失败");
        }
        return Result.successResult("更新用户信息成功");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        projectService.deleteById(id);
        return Result.successResult();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public Result update(@RequestBody Project project) {
        projectService.update(project);
        return Result.successResult();
    }

    /**
     * 查看项目详情
     *
     * @param id the id
     * @return the result
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Project project = projectService.findById(id);
        // TODO 需要重写，目标代币为多个
        // 首先需要通过项目id查询token_details表数据
        List<TokenDetail> tokenDetails = tokenDetailService.findByProjectId(id);
//        List inputMoneyList = new ArrayList();
        List<TokenDetail> inputTokenDetails = new ArrayList<>();
        if(!CollectionUtils.isEmpty(tokenDetails)){
            for(int i=0; i<tokenDetails.size(); i++){
                if(!tokenDetails.get(i).getTokenMoneyId().equals(project.getOutputTokenMoneyDetailId())){
                    TokenMoney inputMoney = tokenMoneyService.findById(tokenDetails.get(i).getTokenMoneyId());
                    tokenDetails.get(i).setTokenMoney(inputMoney);
//                    inputMoneyList.add(inputMoney);
                    inputTokenDetails.add(tokenDetails.get(i));
                    project.setInputTokenDetails(inputTokenDetails);
                }
            }
        }

        if(Objects.nonNull(project.getOutputTokenMoneyDetailId())) {
            // 发行代币
            TokenDetail outPutTokenDetail = tokenDetailService.findById(project.getOutputTokenMoneyDetailId());
            TokenMoney tokenMoney = tokenMoneyService.findById(outPutTokenDetail.getTokenMoneyId());
            outPutTokenDetail.setTokenMoney(tokenMoney);
            project.setOutPutTokenDetail(outPutTokenDetail);
        }
        if(Objects.nonNull(project.getCreateUserId())) {
            User createUser = userService.findById(project.getCreateUserId());
            project.setCreateUser(createUser);
        }
        return Result.successResult(project);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Project> list = projectService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }

    /**
     * 返回给首页的数据
     *
     * @return the result
     */
    @GetMapping("/findProjectsAndSort")
    public Result findProjectsAndSort(){
        List<Project> projectList = projectService.findAll();

        List<Project> ICOList = new ArrayList<>(); // ICO中的数据列表
        List<Project> willICOList = new ArrayList<>(); // 即将进行ICO中的数据列表
        List<Project> finishICOList = new ArrayList<>(); // 结束ICO的数据列表
        for (int i =0; i<projectList.size();i++){
            Project p = projectList.get(i);
            if(p.getThirdEndorsement() != null && p.getOutputTokenMoneyDetailId()!=null){
                // 判断时间，设定state的值
                //获取系统当前时间，获取项目的开始时间以及结束时间
                Date date=new Date();
                DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=format.format(date);

                Date startTime = p.getStartTime();
                Date endTime = p.getEndTime();
                //分离已结束的项目
                if(endTime.before(date)){
                    p.setState(2);
                    finishICOList.add(p);
                }
                // 分离即将开始的项目
                if(date.before(startTime)){
                    p.setState(1);
                    willICOList.add(p);
                }
                // 分离正在进行中的项目
                if(startTime.before(date) && date.before(endTime)){
                    p.setState(0);
                    ICOList.add(p);
                }
            }else{
                //删除项目
                projectService.deleteById(p.getId());
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
 /*   @RequestMapping("/findProjectByState")
    admin Result findProjectsByState(@RequestParam(required = true,defaultValue = "3") Integer state,
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
    }*/
}
