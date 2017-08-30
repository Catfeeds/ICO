package com.tongwii.ico.listener;

import com.tongwii.ico.util.SpringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * Description: 获取spring的上下文
 * 
 * @author Zeral
 * @date 2017/08/30
 * 
 */
@Component
public class InitSpringUtilListener implements ServletContextListener {
    private final static Logger logger = LogManager.getLogger();

    /**
     * 销毁上下文
     * 
     * @param evt
     *            servlet上下文事件对象
     */
    public void contextDestroyed(ServletContextEvent evt) {

    }

    /**
     * 初始化上下文
     * 
     * @param evt
     *            servlet上下文事件对象
     */
    public void contextInitialized(ServletContextEvent evt) {
        logger.info("开始初始化SpringUtil...");
        ServletContext ctx = evt.getServletContext();
        SpringUtil.init(ctx);
        String webRootAbsPath = evt.getServletContext().getRealPath("/");
        logger.info("web root abs path=" + webRootAbsPath);
        SpringUtil.setWebRootAbsPath(webRootAbsPath);
        logger.info("SpringUtil初始化完成.");
    }

}
