package com.tongwii.ico.util;

import com.tongwii.ico.interceptor.LoggingRequestInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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

    @Async
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
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "APPCODE " + appCode);
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);


        //设置参数
        Map<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("ParamString", "{'msg':'"+ code +"'}");
        hashMap.put("RecNum", phone);
        hashMap.put("SignName", signName);
        hashMap.put("TemplateCode", templateCode);
        String getParams = "?" + hashMap.keySet().stream().map(k -> String.format("%s={%s}", k, k)).collect(Collectors.joining("&"));

        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);

        //执行请求
        RestTemplate restTemplate = new RestTemplate();
        ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
        List<ClientHttpRequestInterceptor> ris = new ArrayList<>();
        ris.add(ri);
        restTemplate.setInterceptors(ris);
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        // 编码设置
        FormHttpMessageConverter fc = new FormHttpMessageConverter();
        StringHttpMessageConverter s = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        List<HttpMessageConverter<?>> partConverters = new ArrayList<>();
        partConverters.add(s);
        partConverters.add(new ResourceHttpMessageConverter());
        fc.setPartConverters(partConverters);
        restTemplate.getMessageConverters().addAll(Arrays.asList(fc, new MappingJackson2HttpMessageConverter()));

        ResponseEntity<String> resp = restTemplate.exchange(host+path+getParams, HttpMethod.GET, requestEntity, String.class, hashMap);

        //获得返回值
        String body = resp.getBody();
        System.out.println(body.toString());
    }
}
