package com.tongwii.ico.service.impl;

import com.tongwii.ico.core.AbstractService;
import com.tongwii.ico.dao.MessageMapper;
import com.tongwii.ico.model.Message;
import com.tongwii.ico.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * Created by Zeral on 2017-08-15.
 */
@Service
@Transactional
public class MessageServiceImpl extends AbstractService<Message> implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Message> findOfficalMessages() {
        Message message = new Message();
        message.setState(1);
        List<Message> messages = messageMapper.select(message);
        if(CollectionUtils.isEmpty(messages)){
            return null;
        }
        return messages;
    }
}
