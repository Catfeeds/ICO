package com.tongwii.ico.security;

import com.alibaba.fastjson.JSON;
import com.tongwii.ico.core.Result;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * {@link AuthenticationEntryPoint} 拒绝所有请求与未经授权的错误消息。
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = - 8970718410437077606L;

    @Override
    public void commence ( HttpServletRequest request, HttpServletResponse response, AuthenticationException authException ) throws IOException {
        response.setHeader( "Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE );
        response.setCharacterEncoding( StandardCharsets.UTF_8.displayName() );
        try ( PrintWriter out = response.getWriter() ) {
            out.print( JSON.toJSONString(Result.unauthorized( "认证失败:身份验证令牌丢失、过期或无效。" ) ) );
        }
    }
}