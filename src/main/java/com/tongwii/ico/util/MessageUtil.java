package com.tongwii.ico.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 发送验证工具集
 *
 * @author Zeral
 * @date 2017-08-06
 */
@Component
public class MessageUtil {
    private final static Logger logger = LogManager.getLogger();

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Value("${mail.fromMail.addr}")
    private String from;

    @Value("${domain}")
    private String domain;

    @Value("${sms.appCode}")
    private String appCode;
    @Value("${sms.host}")
    private String host;
    @Value("${sms.path}")
    private String path;
    @Value("${sms.signName}")
    private String signName;
    @Value("${sms.templateCode}")
    private String templateCode;

    public int getSixRandNum() {
        return getRandNum(1, 999999);
    }

    public int getRandNum(int min, int max) {
        int randNum = min + (int)(Math.random() * ((max - min) + 1));
        return randNum;
    }

    public boolean sendRegisterMail(String toAddress, String token) {
        MimeMessage message = mailSender.createMimeMessage();

        String register_link =  domain + "/user/validateEmail?Authorization=" + token;

        //创建邮件正文
        Context context = new Context();
        context.setVariable("register_link", register_link);
        String emailContent = templateEngine.process("UserRegisterTemplate", context);
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toAddress);
            helper.setSubject("邮箱认证");
            helper.setText(emailContent, true);
            mailSender.send(message);
            logger.info("html邮件发送成功");
            return true;
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
            return false;
        }
    }

    public void sendRegisterSMS(String phone, Integer code) throws UnsupportedEncodingException {
        //设置header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "APPCODE " + appCode);

        //设置参数
        Map<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("ParamString", "{'node':'"+ code +"'}");
        hashMap.put("RecNum", phone);
        hashMap.put("SignName", signName);
        hashMap.put("TemplateCode", templateCode);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(hashMap, httpHeaders);

        //执行请求
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(host+path, HttpMethod.GET, requestEntity, String.class);

        //获得返回值
        String body = resp.getBody();
        System.out.println(body.toString());
    }
}
