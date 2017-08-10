package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.ProjectUserRelationMapper;
import com.tongwii.ico.model.Project;
import com.tongwii.ico.model.ProjectUserRelation;
import com.tongwii.ico.service.ProjectService;
import com.tongwii.ico.service.ProjectUserRelationService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class ProjectUserRelationServiceImpl extends AbstractService<ProjectUserRelation> implements ProjectUserRelationService {
    @Resource
    private ProjectUserRelationMapper projectUserRelationMapper;
    @Autowired
    private ProjectService projectService;

    @Override
    public List<Project> findByUserId(Integer userId) {
        // 首先先获取项目Id
        ProjectUserRelation projectUserRelation = new ProjectUserRelation();
        projectUserRelation.setUserId(userId);
        List<ProjectUserRelation> projectUserRelations = projectUserRelationMapper.select(projectUserRelation);
        if(CollectionUtils.isEmpty(projectUserRelations)){
            return null;
        }
        List<Project> projectList = new ArrayList<>();
        for (int i=0; i<projectUserRelations.size(); i++){
            Project project = projectService.findById(projectUserRelations.get(i).getProjectId());
            projectList.add(project);
        }
        return projectList;
    }
}
