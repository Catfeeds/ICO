package com.tongwii.ico.service.impl;

import com.tongwii.ico.model.User;
import com.tongwii.ico.security.JwtUser;
import com.tongwii.ico.service.UserRoleRelationService;
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
    private UserRoleRelationService userRoleRelationService;

    @Override
    public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException {
        User user = userService.findByUsername( username );
        if ( user == null ) {
            throw new UsernameNotFoundException( String.format( "该'%s'用户名不存在.", username ) );
        }
        return new JwtUser(
                user.getId(),
                user.getEmailAccount(),
                user.getPassword(),
                user.getEnabled(),
                userRoleRelationService.getByUserId( user.getId() )
        );
    }


}










