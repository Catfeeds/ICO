package com.tongwii.ico.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权验证过滤器
 *
 * @author Zeral
 * @date 2017-08-02
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final static Logger logger = LogManager.getLogger();
    ///////////////////////////////////////////////////////////////////////////
    // 在log4j2.xml配置文件通过 %X{xxx} 获取
    ///////////////////////////////////////////////////////////////////////////
    /** 用户ID */
    private static final String USER_ID   = "userId";
    /** 用户姓名 */
    private static final String USER_NAME = "userName";
    /** 用户ip */
    public static final String USER_IP = "userIP";

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil       jwtTokenUtil;
    @Value( "${jwt.header}" )
    private String             tokenHeader;


    @Override
    protected void doFilterInternal ( HttpServletRequest request,
                                      HttpServletResponse response,
                                      FilterChain chain ) throws ServletException, IOException {
        String token = request.getHeader( this.tokenHeader );
        if(StringUtils.isBlank(token)) {
            token = request.getParameter(this.tokenHeader);
        }
        final String authToken = token;
        final String username  = jwtTokenUtil.getUsernameFromToken( authToken );

        if ( logger.isDebugEnabled() ) {
            logger.debug( "authToken : {},username : {}", authToken, username );
        }

        if ( username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
            // 对于简单的验证，只需检查令牌的完整性即可。 您不必强制调用数据库。 由你自己决定
            // 是否查询数据看情况,目前是查询数据库
            // 因为后台项目,用户信息较多,特别是权限信息,每次查询的话,需要递归组织上下关系,比较麻烦
            // 所以这里是每次从缓存中获取,放到 spring security 的 context 中,拦截器中就可以直接获取了
            // 注意 : 这里并没有使用到session
            UserDetails userDetails = this.userDetailsService.loadUserByUsername( username );
            if ( jwtTokenUtil.validateToken( authToken, userDetails ) ) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                ThreadContext.put( USER_ID, String.valueOf( ( (JwtUser) userDetails ).getId() ) );
                ThreadContext.put( USER_IP, getIpAddress(request));
                ThreadContext.put( USER_NAME, username );

                authentication.setDetails( new WebAuthenticationDetailsSource().buildDetails( request ) );

                if ( logger.isDebugEnabled() ) {
                    logger.debug( "该{}用户已认证, 设置安全上下文, 登陆ip:{}", username, getIpAddress(request) );
                }
                SecurityContextHolder.getContext().setAuthentication( authentication );
            }
        }
        chain.doFilter( request, response );
        return;
    }


    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        return ip;
    }
}
