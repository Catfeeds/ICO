package com.tongwii.ico.service;
import com.tongwii.ico.model.ProjectUserWalletRelation;
import com.tongwii.ico.core.Service;

import java.util.List;


/**
 * Created by Zeral on 2017-08-02.
 */
public interface ProjectUserWalletRelationService extends Service<ProjectUserWalletRelation> {

    /**
     * 根据用户ID查询用户与项目之间的交易信息
     * @param userId
     * @return
     */
    List<ProjectUserWalletRelation> findUserTransactionList(Integer userId);
}
