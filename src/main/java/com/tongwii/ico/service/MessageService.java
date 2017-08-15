package com.tongwii.ico.service;
import com.tongwii.ico.model.Message;
import com.tongwii.ico.core.Service;

import java.util.List;


/**
 * Created by Zeral on 2017-08-15.
 */
public interface MessageService extends Service<Message> {
    /**
     * 获取所有正常状态下的message信息
     * @return
     */
    List<Message> findOfficalMessages();
}
