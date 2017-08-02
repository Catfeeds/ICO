package com.tongwii.ico.service.impl;

import com.tongwii.ico.model.User;
import com.tongwii.ico.security.AccountCredentials;
import com.tongwii.ico.service.RoleService;
import com.tongwii.ico.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException {
        User user = userService.findByUsername( username );
        if ( user == null ) {
            throw new UsernameNotFoundException( String.format( "该'%s'用户名不存在.", username ) );
        }
        return new AccountCredentials(
                user.getId(),
                user.getEmailAccount(),
                user.getPassword(),
                user.getNickName(),
                user.getRealName(),
                user.getPhone(),
                user.getIdCard(),
                user.getEnabled(),
                roleService.getByUserId( user.getId() )
        );
    }


}










