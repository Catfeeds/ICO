package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.UserProjectInvestRecordMapper;
import com.tongwii.ico.model.UserProjectInvestRecord;
import com.tongwii.ico.service.UserProjectInvestRecordService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * Created by Zeral on 2017-08-25.
 */
@Service
@Transactional
public class UserProjectInvestRecordServiceImpl extends AbstractService<UserProjectInvestRecord> implements UserProjectInvestRecordService {
    @Autowired
    private UserProjectInvestRecordMapper userProjectInvestRecordMapper;

}
