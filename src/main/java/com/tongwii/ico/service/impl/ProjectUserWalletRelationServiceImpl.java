package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.ProjectUserWalletRelationMapper;
import com.tongwii.ico.model.ProjectUserWalletRelation;
import com.tongwii.ico.model.UserWallet;
import com.tongwii.ico.service.ProjectUserWalletRelationService;
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
public class ProjectUserWalletRelationServiceImpl extends AbstractService<ProjectUserWalletRelation> implements ProjectUserWalletRelationService {
    @Resource
    private ProjectUserWalletRelationMapper projectUserWalletRelationMapper;
    @Autowired
    private UserWalletServiceImpl userWalletService;

    @Override
    public List<ProjectUserWalletRelation> findUserTransactionList(Integer userId) {
        //首先通过userId查询userWallet，得到userWallet列表
        List<UserWallet> userWallets = userWalletService.findByUserId(userId);
        List userWalletTransaction = new ArrayList();
        if(!CollectionUtils.isEmpty(userWallets)){
            for(int i=0; i<userWallets.size();i++){
                ProjectUserWalletRelation projectUserWalletRelation = new ProjectUserWalletRelation();
                projectUserWalletRelation.setUserWallet(userWallets.get(i).getId());
                List<ProjectUserWalletRelation> projectUserWalletRelations = projectUserWalletRelationMapper.select(projectUserWalletRelation);
                if(!CollectionUtils.isEmpty(projectUserWalletRelations)){
                    userWalletTransaction.add(projectUserWalletRelations);
                }
            }

        }else{
            return null;
        }

        if(CollectionUtils.isEmpty(userWalletTransaction)){
            return null;
        }
        return userWalletTransaction;
    }
}
