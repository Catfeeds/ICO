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
     * @param userId
     * @return
     */
    List<UserProjectInvestRecord> findByUserId(Integer userId);
    /**
     * 根据项目Id查询所用户的交易记录
     * @param projectId
     * @return
     */
    List<UserProjectInvestRecord> findByProjectId(Integer projectId);

}
