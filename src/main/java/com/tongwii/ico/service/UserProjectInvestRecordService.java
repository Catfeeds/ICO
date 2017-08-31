package com.tongwii.ico.service;
import com.tongwii.ico.model.UserProjectInvestRecord;
import com.tongwii.ico.core.Service;

import java.util.List;


/**
 * Created by Zeral on 2017-08-25.
 */
public interface UserProjectInvestRecordService extends Service<UserProjectInvestRecord> {

    /**
     * 根据用户Id查询所有此用户的交易记录
     *
     * @param userId 用户id
     * @return list
     */
    List<UserProjectInvestRecord> findByUserId(Integer userId);

    /**
     * 根据项目Id查询所用户的交易记录
     *
     * @param projectId 项目id
     * @return list
     */
    List<UserProjectInvestRecord> findByProjectId(Integer projectId);

    /**
     * 保存锁定记录并更新项目进度
     *
     * @param userId          用户id
     * @param coinId          代币id
     * @param projectId       项目id
     * @param investmentMoney 投资金额
     */
    void saveRecordAndUpdateProjectProjess(Integer userId, Integer coinId, Integer projectId, String investmentMoney);

    /**
     * 根据项目id和用户id获取交易记录
     *
     * @param userId    the user id
     * @param projectId 项目id
     * @return list
     */
    List<UserProjectInvestRecord> findByUserIdAndProjectId(Integer userId, Integer projectId);
}
