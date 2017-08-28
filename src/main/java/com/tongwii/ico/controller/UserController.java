package com.tongwii.ico.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.User;
import com.tongwii.ico.security.JwtTokenUtil;
import com.tongwii.ico.security.JwtUser;
import com.tongwii.ico.service.FileService;
import com.tongwii.ico.service.UserService;
import com.tongwii.ico.service.UserWalletService;
import com.tongwii.ico.util.ContextUtils;
import com.tongwii.ico.util.MessageUtil;
import com.tongwii.ico.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* Created by Zeral on 2017-08-02.
*/
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserWalletService userWalletService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value( "${jwt.header}" )
    private String tokenHeader;

    @PostMapping
    @ResponseBody
    public Result add(@RequestBody User user) {
        userService.save(user);
        return Result.successResult();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Result delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return Result.successResult();
    }

    @PutMapping("/state")
    @ResponseBody
    public Result updateState(@RequestBody User user) {

        userService.update(user);
        User u = userService.findById(user.getId());
        u.setUserWallets(userWalletService.findWalletByUserId(u.getId()));
        return Result.successResult().add("userInfo",u);
    }
    @PutMapping
    @ResponseBody
    public Result update(@RequestBody User user) {
        //设置身份证信息的唯一性更改
        if(user.getIdCard()!= null){
            if(!user.getIdCard().trim().isEmpty()){
                //校验身份证是否在数据库中存在
                if(userService.findByIdCard(user.getIdCard()) != null){
                    return Result.failResult("身份证信息不可修改!");
                }
            }
        }

        userService.update(user);
        User u = userService.findById(user.getId());
        u.setUserWallets(userWalletService.findWalletByUserId(u.getId()));
        return Result.successResult().add("userInfo",u);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Result detail(@PathVariable Integer id) {
        User user = userService.findById(id);
        return Result.successResult(user);
    }

    @GetMapping("/findByIdCard/{idCard}")
    @ResponseBody
    public Result findUserByIdCard(@PathVariable String idCard) {
        User user = userService.findByIdCard(idCard);
        return Result.successResult(user);
    }

    @GetMapping
    @ResponseBody
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestBody User user) {
        if(user.getPassword() == null){
            user.setPassword("11111111");
        }

        if(user.getIdCard() != null){
            User u = userService.findByIdCard(user.getIdCard());
            if(u!=null){
                return Result.failResult("此身份用户已存在!");
            }
        }

        if(!ValidateUtil.validateEmail(user.getEmailAccount())) {
            return Result.failResult("邮箱格式不正确!");
        }

        if(userService.emailAccountExist(user.getEmailAccount())) {
            return Result.failResult("该账号已存在:" + user.getEmailAccount());
        }

        try {
            userService.register(user);
        } catch (Exception e) {
            return Result.errorResult("注册失败");
        }

        return Result.successResult("注册成功");
    }

    /**
     * 修改用户密码
     */
    @PutMapping("/ ")
    @ResponseBody
    public Result updatePassword(@RequestBody Map<Object,String> passwordInfo) {
        Integer userId = ContextUtils.getUserId();
        String oldPassword = passwordInfo.get("oldPassword");
        String newPassword = passwordInfo.get("newPassword");
        String verifycode = passwordInfo.get("verifycode");
        // 通过userID查询用户信息
        User u = userService.findById(userId);
        // 判断旧密码是否正确
        if(passwordEncoder.matches(oldPassword,u.getPassword())){
            // 如果旧密码验证成功，接着验证验证码
            if(verifycode.equals(u.getVerifyCode().toString())){
                // 如果验证码验证成功则进行修改密码操作
                User user = new User();
                user.setId(userId);
                BCryptPasswordEncoder encoderPassword = new BCryptPasswordEncoder();
                user.setPassword(encoderPassword.encode(newPassword));
                userService.update(user);
                user = userService.findById(userId);
                user.setUserWallets(userWalletService.findWalletByUserId(user.getId()));
                return Result.successResult("密码修改成功!").add("userInfo",user);
            }
            else{
                return Result.errorResult("验证码错误!");
            }
        }else{
            return Result.errorResult("旧密码错误!");
        }

    }

    /**
     * 发送认证邮箱
     */
    @GetMapping("/sendVerifyEmail")
    @ResponseBody
    public Result register(HttpServletRequest request) {
        JwtUser user = ContextUtils.getJwtUser();
        String token = request.getHeader(tokenHeader);
        messageUtil.sendRegisterMail(user.getUsername(), token);
        return Result.successResult("发送成功");
    }

    /**
     * 验证手机号
     */
    @PostMapping("/validatePhone")
    @ResponseBody
    public Result validatePhone(@RequestBody Map<String, String> map) {
        String phone = "";
        if(map.containsKey("phone")) {
            phone = map.get("phone");
        }
        if(!ValidateUtil.validateMobile(phone)) {
            return Result.failResult("手机号码格式不正确");
        }
        User user = null;
        try {
            user = userService.findById(ContextUtils.getUserId());
            user.setPhone(phone);
            Integer code = messageUtil.getSixRandNum();
            user.setVerifyCode(code);
            user.setExpireDate(jwtTokenUtil.generateExpirationDate(new Date(), 1800));
            messageUtil.sendRegisterSMS(user.getPhone(), code);
            userService.update(user);
            user.setUserWallets(userWalletService.findWalletByUserId(user.getId()));
        } catch (Exception e) {
            return Result.errorResult("发送验证码失败");
        }
        return Result.successResult("验证码发送成功!").add("userInfo",user);
    }

    /**
     * 修改验证手机成功字段
     */
    @PostMapping("/phoneValidated")
    @ResponseBody
    public Result phoneValidated(@RequestBody User user) {
        User oldUser = userService.findById(ContextUtils.getUserId());
        oldUser.setValidatePhone(true);
        userService.update(oldUser);
        return Result.successResult("验证成功").add("userInfo",oldUser);
    }


    /**
     * 验证邮箱
     */
    @GetMapping("/validateEmail")
    public String validateEmail() {
        Integer userId = ContextUtils.getUserId();
        User user = userService.findById(userId);
        user.setValidateEmail(true);
        userService.update(user);
        return "redirect: /sign.html";
    }

    /**
     * 获取用户数据
     */
    @GetMapping("/userInfo")
    @ResponseBody
    public Result userInfo() {
        User user = userService.findById(ContextUtils.getUserId());
        return Result.successResult(user);
    }

}
