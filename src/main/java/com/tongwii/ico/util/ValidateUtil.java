package com.tongwii.ico.util;

import com.tongwii.ico.exception.ApplicationException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * 验证工具类
 *
 * @author Zeral
 * @date 2017-08-06
 */
public class ValidateUtil {
    static Properties regular = null;
    static {
        Resource resource = new ClassPathResource(
                "/config/regular_experssion.properties");
        try {
            regular = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException("加载配置文件出错");
        }
    }

    /**
     *
     * Description: 验证邮箱
     *
     * @param
     * @return boolean
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 上午10:59:49
     */
    public static boolean validateEmail(String param) {
        String reg = regular.getProperty("input.email");
        boolean rs = Pattern.compile(reg).matcher(param).find();
        return rs;
    }

    /**
     *
     * Description: 验证手机
     *
     * @param
     * @return boolean
     * @throws
     * @Author haolingfeng
     * Create Date: 2014-12-9 上午10:59:49
     */
    public static boolean validateMobile(String param) {
        String reg = regular.getProperty("input.mobile");
        boolean rs = Pattern.compile(reg).matcher(param).find();
        return rs;
    }

}
