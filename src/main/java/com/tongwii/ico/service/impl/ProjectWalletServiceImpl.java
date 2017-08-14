package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.ProjectWalletMapper;
import com.tongwii.ico.model.ProjectWallet;
import com.tongwii.ico.service.ProjectWalletService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class ProjectWalletServiceImpl extends AbstractService<ProjectWallet> implements ProjectWalletService {
    @Resource
    private ProjectWalletMapper projectWalletMapper;

    @Override
    public List<ProjectWallet> findWalletByProjectId(Integer projectId) {
        ProjectWallet projectWallet = new ProjectWallet();
        projectWallet.setProjectId(projectId);
        List<ProjectWallet> projectWallets = projectWalletMapper.select(projectWallet);
        if(CollectionUtils.isEmpty(projectWallets)){
            return null;
        }
        return projectWallets;
    }
}
