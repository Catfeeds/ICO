package com.tongwii.ico.util;

import com.tongwii.ico.interceptor.LoggingRequestInterceptor;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * rest请求工具
 *
 * @author Zeral
 * @date 2017-08-09
 */
public class RestTemplateUtil {

    /**
     * Rest template .
     *
     * @param <T>     the type parameter 请求参数类型
     * @param url     the url 请求地址
     * @param request the request 请求头信息
     * @param var     the var 返回类型
     * @param params  the params 请求参数
     * @param method  the method 请求方法
     * @return the t  返回类型
     */
    public static  <T> T restTemplate(String url, Object request, Class<T> var, Map<String,T> params, HttpMethod method) {
        RestTemplate restTemplate = new RestTemplate();

        // 日志记录
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

        switch (method) {
            case POST:
                return restTemplate.postForObject(url, request, var, params);
            case GET:
                String getParams = "?" + params.keySet().stream().map(k -> String.format("%s={%s}", k, k)).collect(Collectors.joining("&"));
                return restTemplate.getForObject(url + getParams, var, params);
            case PUT:
                restTemplate.put(url, request, params);
            case DELETE:
                restTemplate.delete(url, params);
            default:
                return restTemplate.postForObject(url, params, var);
        }
    }
}
