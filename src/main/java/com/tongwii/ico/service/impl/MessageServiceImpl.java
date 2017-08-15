package com.tongwii.ico.service.impl;

import com.tongwii.ico.core.AbstractService;
import com.tongwii.ico.dao.MessageMapper;
import com.tongwii.ico.model.Message;
import com.tongwii.ico.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Zeral on 2017-08-15.
 */
@Service
@Transactional
public class MessageServiceImpl extends AbstractService<Message> implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

}
