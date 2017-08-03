package com.tongwii.ico.web;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.User;
import com.tongwii.ico.security.JwtTokenUtil;
import com.tongwii.ico.security.JwtUser;
import com.tongwii.ico.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Value( "${jwt.header}" )
    private String tokenHeaderKey;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
    public Result add(@RequestBody User user) {
        userService.save(user);
        return Result.successResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return Result.successResult();
    }

    @PutMapping
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.successResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        User user = userService.findById(id);
        return Result.successResult(user);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }

    /**
     * 创建身份验证token
     *
     * @param user
     * @return
     * @throws AuthenticationException
     */
    @PostMapping("/login")
    public Result createAuthenticationToken ( @RequestBody User user) throws AuthenticationException {
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
        // 返回
        return Result.successResult().add( "token", token );
    }

    /**
     * 刷新并认证token
     *
     * @param request
     * @return
     */
    @PutMapping("/authentication")
    public Result refreshAndGetAuthenticationToken ( HttpServletRequest request ) {
        String  token    = request.getHeader( tokenHeaderKey );
        String  username = jwtTokenUtil.getUsernameFromToken( token );
        JwtUser user     = ( JwtUser ) userDetailsService.loadUserByUsername( username );

        if ( jwtTokenUtil.canTokenBeRefreshed(token) ) {
            String refreshedToken = jwtTokenUtil.refreshToken( token );
            return Result.successResult().add( "token", refreshedToken );
        } else {
            return Result.failResult( "token 无法刷新,未认证通过" );
        }
    }

}
