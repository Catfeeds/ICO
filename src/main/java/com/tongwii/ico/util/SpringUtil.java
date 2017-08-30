package com.tongwii.ico.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * spring手动获取bean
 *
 * @author Zeral
 * @date 2017/08/30
 */
public class SpringUtil {

    private final static Logger logger = LogManager.getLogger();

    private static ApplicationContext context;
    static String webRootAbsPath;

    /**
     * 构造
     */
    public SpringUtil() {

    }

    /**
     * 得到上下文
     * 
     * @return 应用上下文
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * 加载上下文
     * 
     * @param sc
     *            servlet上下文
     */
    public static void init(ServletContext sc) {
        logger.info("加载上下文");
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
    }

    /**
     * 加载配置
     * 
     * @param configLocations
     *            配置文件集合
     */
    public static void init(String... configLocations) {
        logger.info("加载配置");
        context = new ClassPathXmlApplicationContext(configLocations);
    }

    /**
     * 根据beanId得到bean
     * 
     * @param beanName
     *            beanId
     * @return bean实体
     */
    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    /**
     * 直接传入applicationContext
     * 
     * @param actx
     *            应用程序上下文对象
     */
    public static void init(ApplicationContext actx) {
        context = actx;
    }

    public static void setWebRootAbsPath(String webRootAbsPath) {
        SpringUtil.webRootAbsPath = webRootAbsPath;
    }

    public static String getWebRootAbsPath() {
        return webRootAbsPath;
    }
}