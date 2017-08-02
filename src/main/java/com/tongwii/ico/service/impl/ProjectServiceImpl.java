package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.ProjectMapper;
import com.tongwii.ico.model.Project;
import com.tongwii.ico.service.ProjectService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class ProjectServiceImpl extends AbstractService<Project> implements ProjectService {
    @Resource
    private ProjectMapper projectMapper;

}
