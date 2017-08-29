package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.ProjectMapper;
import com.tongwii.ico.model.Project;
import com.tongwii.ico.model.TokenDetail;
import com.tongwii.ico.model.User;
import com.tongwii.ico.service.ProjectService;
import com.tongwii.ico.core.AbstractService;
import com.tongwii.ico.service.TokenDetailService;
import com.tongwii.ico.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

import static com.tongwii.ico.model.TokenDetail.TokenDetailType.OUTPUT_ICON;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class ProjectServiceImpl extends AbstractService<Project> implements ProjectService {
    @Resource
    private ProjectMapper projectMapper;
    @Autowired
    private TokenDetailService tokenDetailService;
    @Autowired
    private UserService userService;

    @Override
    public void updateOutputTokenMoney(Integer id, TokenDetail tokenDetail) {
        tokenDetail.setType(OUTPUT_ICON.getCode());
        tokenDetail.setTokenMoneyProjectId(id);
        tokenDetailService.save(tokenDetail);
    }

    @Override
    public void updateCreateUser(Integer id, User user) {
        userService.save(user);
        Project project = findById(id);
        project.setCreateUserId(user.getId());
        update(project);

    }

    @Override
    public List<Project> findOfficalProject() {
        List<Project> projectList = projectMapper.selectOfficalProject();
        if(CollectionUtils.isEmpty(projectList)){
            return null;
        }
        return projectList;
    }

    @Override
    public List<Project> findProjectByState(Integer state) {
        Project project = new Project();
        project.setState(state);
        List<Project> projectList = projectMapper.select(project);
        if(CollectionUtils.isEmpty(projectList)){
            return null;
        }
        return projectList;
    }

    @Override
    public List<Project> test() {
        List<Project> projectList = projectMapper.selectOfficalProject();
        if(CollectionUtils.isEmpty(projectList)){
            return null;
        }
        return projectList;
    }
}
