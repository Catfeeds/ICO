package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.UserProjectInvestRecordMapper;
import com.tongwii.ico.model.UserProjectInvestRecord;
import com.tongwii.ico.service.UserProjectInvestRecordService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * Created by Zeral on 2017-08-25.
 */
@Service
@Transactional
public class UserProjectInvestRecordServiceImpl extends AbstractService<UserProjectInvestRecord> implements UserProjectInvestRecordService {
    @Autowired
    private UserProjectInvestRecordMapper userProjectInvestRecordMapper;

    @Override
    public List<UserProjectInvestRecord> findByUserId(Integer userId) {
        UserProjectInvestRecord userProjectInvestRecord = new UserProjectInvestRecord();
        userProjectInvestRecord.setUserId(userId);
        List<UserProjectInvestRecord> userProjectInvestRecords = userProjectInvestRecordMapper.select(userProjectInvestRecord);
        if(CollectionUtils.isEmpty(userProjectInvestRecords)){
            return null;
        }
        return userProjectInvestRecords;
    }
}
