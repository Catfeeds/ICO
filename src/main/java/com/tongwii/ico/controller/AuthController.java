package com.tongwii.ico.controller;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.User;
import com.tongwii.ico.model.UserWallet;
import com.tongwii.ico.security.JwtTokenUtil;
import com.tongwii.ico.security.JwtUser;
import com.tongwii.ico.service.UserService;
import com.tongwii.ico.service.UserWalletService;
import com.tongwii.ico.util.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 授权controller
 *
 * @author Zeral
 * @date 2017-08-03
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Value( "${jwt.header}" )
    private String tokenHeaderKey;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserWalletService userWalletService;

    /**
     * 创建身份验证token
     */
    @PostMapping("/login")
    public Result createAuthenticationToken (@RequestBody User user) throws AuthenticationException {
        // 执行安全
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmailAccount(),
                        user.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication( authentication );
        final UserDetails userDetails = ( UserDetails ) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateToken( userDetails);
        // 通过email查询数据库中的唯一记录
        User userInfo = userService.findByUsername(user.getEmailAccount());
        // 通过用户Id查询用户钱包
        List<Object> userWallets = userWalletService.findWalletByUserId(userInfo.getId());
        // 给用户信息中添加用户钱包信息
        userInfo.setUserWallets(userWallets);
        // 返回
        return Result.successResult().add( "token", token ).add("userInfo", userInfo);
    }

    /**
     * 刷新并认证token
     */
    @PutMapping("/refresh")
    public Result refreshAndGetAuthenticationToken ( HttpServletRequest request ) {
        String  token    = request.getHeader( tokenHeaderKey );
        String  username = jwtTokenUtil.getUsernameFromToken( token );
        JwtUser user     = ( JwtUser ) userDetailsService.loadUserByUsername( username );

        if ( jwtTokenUtil.canTokenBeRefreshed(token) && ContextUtils.getJwtUser().getId().equals(user.getId())) {
            String refreshedToken = jwtTokenUtil.refreshToken( token );
            return Result.successResult().add( "token", refreshedToken );
        } else {
            return Result.failResult( "token 无法刷新,未认证通过" );
        }
    }
}
