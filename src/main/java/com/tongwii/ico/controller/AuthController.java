package com.tongwii.ico.controller;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.exception.StorageFileNotFoundException;
import com.tongwii.ico.model.User;
import com.tongwii.ico.security.JwtTokenUtil;
import com.tongwii.ico.security.JwtUser;
import com.tongwii.ico.service.UserService;
import com.tongwii.ico.service.UserWalletService;
import com.tongwii.ico.util.ContextUtils;
import com.tongwii.ico.util.MessageUtil;
import com.tongwii.ico.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 创建身份验证token
     *
     * @param user
     * @return
     * @throws AuthenticationException
     */
    @PostMapping("/login")
    public Result createAuthenticationToken (@RequestBody User user) throws AuthenticationException {
        try {
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
        } catch (AuthenticationException e) {
            return Result.failResult("用户名或密码错误");
        }
    }

    /**
     * 刷新并认证token
     *
     * @param request
     * @return
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

    /**
     * 忘记密码
     * 根据用户邮箱与手机号判断用户是否存在
     */
    @GetMapping("/userExist/{emailAccount}&{phone}")
    @ResponseBody
    public Result userExist(@PathVariable(value = "emailAccount") String emailAccount, @PathVariable(value = "phone") String phone){
        try {
            User u = userService.findByUsername(emailAccount);
            if(u!=null){
                if(u.getPhone()!=null){
                    if(u.getPhone().equals(phone)){
                        return Result.successResult("匹配成功!").add("userInfo",u);
                    }else{
                        return Result.errorResult("邮箱与手机号码不匹配，如有疑问请联系客服!");
                    }
                }else{
                    return Result.errorResult("您未手机认证，请联系客服进行修改!");
                }
            }else{
                return Result.errorResult("该邮箱未注册!");
            }
        }catch (Exception e){
            return Result.errorResult("邮箱与电话号码不可为空!");
        }
    }

    /**
     * @author Yamo
     * 忘记密码——》验证手机号
     * @param user
     * @return
     */
    @PostMapping("/validatePhone")
    @ResponseBody
    public Result validatePhone(@RequestBody User user) {
        if(!ValidateUtil.validateMobile(user.getPhone())) {
            return Result.failResult("手机号码格式不正确");
        }
        User u = null;
        try {
            u = userService.findById(user.getId());
            Integer code = messageUtil.getSixRandNum();
            u.setVerifyCode(code);
            u.setExpireDate(jwtTokenUtil.generateExpirationDate(new Date(), 1800));
            messageUtil.sendRegisterSMS(u.getPhone(), code);
            userService.update(u);
        } catch (Exception e) {
            return Result.errorResult("发送验证码失败");
        }
        return Result.successResult("验证码发送成功!").add("userInfo",u);
    }

    /**
     * @author Yamo
     * 忘记用户密码
     * @param user
     * @return
     */
    @PutMapping("/forgetPassword")
    @ResponseBody
    public Result forgetPassword(@RequestBody User user) {
        // 通过userID查询用户信息
        try{
            User u = userService.findById(user.getId());
            BCryptPasswordEncoder encoderPassword = new BCryptPasswordEncoder();
            u.setPassword(encoderPassword.encode(user.getPassword()));
            userService.update(u);
            return Result.successResult().add("userInfo",u);
        }catch (Exception e){
            return Result.errorResult("重置密码失败!");
        }
    }
}
