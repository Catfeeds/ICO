package com.tongwii.ico.util;

import com.tongwii.ico.model.User;
import com.tongwii.ico.security.JwtUser;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * 上下文
 *
 * @author : Zeral
 * @date : 2017/8/3
 */
public final class ContextUtils {

    private static final SimpleGrantedAuthority ROLE_ANONYMOUS                  = new SimpleGrantedAuthority(
            "ROLE_ANONYMOUS" );
    private static final String                 ANONYMOUS_USER_PRINCIPAL        = "anonymousUser";

    /**
     * 是否登录
     * <p>
     * {@link Authentication#isAuthenticated()}
     *
     * @return <code>true</code> , 匿名用户(未登录)返回 <code>false</code>
     */
    public static boolean isLogin () {
        Authentication authentication = getAuthentication();
        if ( authentication.getAuthorities().contains( ROLE_ANONYMOUS )
                || ANONYMOUS_USER_PRINCIPAL.equals( authentication.getPrincipal() ) ) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    /**
     * ! {@link #isLogin()}
     *
     * @return
     */
    public static boolean isNotLogin () {
        return ! isLogin();
    }

    /**
     * 得到jwt对象
     *
     * @return
     */
    public static JwtUser getJwtUser () {
        return ( JwtUser ) getAuthentication().getPrincipal();
    }


    /**
     * 得到User对象
     *
     * @return
     */
    public static User getUser () {
        final JwtUser jwtUser = getJwtUser();
        User          user    = new User();
        BeanUtils.copyProperties( jwtUser, user );
        return user;
    }

    /**
     * 得到用户id
     *
     * @return {@link User#id}
     */
    public static Integer getUserId () {
        return getJwtUser().getId();
    }

    /**
     * 得到用户详细信息
     *
     * @return {@link UserDetails}
     */
    public static UserDetails getUserDetails () {
        return ( UserDetails ) getAuthentication().getPrincipal();
    }


    /**
     * 得到凭证
     */
    private static Authentication getAuthentication () {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( null == authentication ) {
            throw new AuthenticationCredentialsNotFoundException( "未经授权:身份验证令牌丢失或无效。" );
        }
        return authentication;
    }


}
