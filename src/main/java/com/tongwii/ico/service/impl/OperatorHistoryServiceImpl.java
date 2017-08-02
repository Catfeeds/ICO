package com.tongwii.ico.service.impl;

import com.tongwii.ico.dao.OperatorHistoryMapper;
import com.tongwii.ico.model.OperatorHistory;
import com.tongwii.ico.service.OperatorHistoryService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class OperatorHistoryServiceImpl extends AbstractService<OperatorHistory> implements OperatorHistoryService {
    @Resource
    private OperatorHistoryMapper operatorHistoryMapper;

}
