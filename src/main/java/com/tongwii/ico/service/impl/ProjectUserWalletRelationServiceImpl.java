package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.ProjectUserWalletRelationMapper;
import com.tongwii.ico.model.ProjectUserWalletRelation;
import com.tongwii.ico.service.ProjectUserWalletRelationService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class ProjectUserWalletRelationServiceImpl extends AbstractService<ProjectUserWalletRelation> implements ProjectUserWalletRelationService {
    @Resource
    private ProjectUserWalletRelationMapper projectUserWalletRelationMapper;

}
