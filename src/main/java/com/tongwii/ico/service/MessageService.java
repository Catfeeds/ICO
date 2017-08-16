package com.tongwii.ico.service;
import com.tongwii.ico.model.Message;
import com.tongwii.ico.core.Service;

import java.util.List;


/**
 * Created by Zeral on 2017-08-15.
 */
public interface MessageService extends Service<Message> {

    /**
     * 根据消息类型查找信息
     * @param type
     * @return
     */
    List<Message> findMessagesByType(int type);
}
