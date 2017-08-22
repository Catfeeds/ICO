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
    @Autowired
    private TokenMoneyServiceImpl tokenMoneyService;

    @Override
    public List<ProjectUserWalletRelation> findUserTransactionList(Integer userId) {
        //首先通过userId查询userWallet，得到userWallet列表
        List<UserWallet> userWallets = userWalletService.findByUserId(userId);
        List userWalletTransaction = new ArrayList();
        if(!CollectionUtils.isEmpty(userWallets)){
            for(int i=0; i<userWallets.size();i++){
                // 通过userWallets中的tokenmoneyId查询每一条数据的tokenmoney名
                String walletType = tokenMoneyService.findById(userWallets.get(i).getTokenMoneyId()).getNameEnShort();
                // 根据用户钱包ID查询用户的所有投资项目记录
                ProjectUserWalletRelation projectUserWalletRelation = new ProjectUserWalletRelation();
                projectUserWalletRelation.setUserWallet(userWallets.get(i).getId());
                List<ProjectUserWalletRelation> projectUserWalletRelations = projectUserWalletRelationMapper.select(projectUserWalletRelation);
                if(!CollectionUtils.isEmpty(projectUserWalletRelations)){
                    // 遍历projectUserWalletRelations给每条记录都添加walletType属性
                    for(int j=0;j<projectUserWalletRelations.size();j++){
                        projectUserWalletRelations.get(j).setWalletType(walletType);
                    }
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
