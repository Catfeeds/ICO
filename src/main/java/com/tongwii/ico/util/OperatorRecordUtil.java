package com.tongwii.ico.util;

import com.tongwii.ico.dao.OperatorHistoryMapper;
import com.tongwii.ico.model.OperatorHistory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


/**
 * 操作记录工具
 *
 * @author Zeral
 * @date 2017-08-30
 */
public class OperatorRecordUtil {
    private final static Logger logger = LogManager.getLogger();

    private static OperatorHistoryMapper operatorHistoryMapper;

    static {
        operatorHistoryMapper = (OperatorHistoryMapper) SpringUtil.getBean("operatorHistoryMapper");
    }

    public static void record(String operatorMessage){
        try {
            OperatorHistory operatorHistory = new OperatorHistory();
            operatorHistory.setUserId(ContextUtils.getUserId());
            operatorHistory.setIp(IPUtil.getIpAddress());
            operatorHistory.setIpAddress(IPUtil.getIpAddressInfo(IPUtil.getIpAddress()));
            operatorHistory.setMessage(operatorMessage);
            operatorHistory.setOperatorTime(new Date());
            operatorHistoryMapper.insert(operatorHistory);
            logger.info("操作记录{}", operatorHistory.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("操作记录记录失败, 错误消息：{}", e.getMessage() );
        }
    }

}
