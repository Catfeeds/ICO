package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.ProjectUserRelationMapper;
import com.tongwii.ico.model.ProjectUserRelation;
import com.tongwii.ico.service.ProjectUserRelationService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class ProjectUserRelationServiceImpl extends AbstractService<ProjectUserRelation> implements ProjectUserRelationService {
    @Resource
    private ProjectUserRelationMapper projectUserRelationMapper;

}
