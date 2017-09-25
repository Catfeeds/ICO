package com.tongwii.ico.security;

import com.alibaba.fastjson.annotation.JSONField;
import com.tongwii.ico.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证对象
 *
 * @author Zeral
 * @date 2017-08-02
 */
public class JwtUser implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private boolean enabled;
    private List<Role> roles;

    @Override
    public String getUsername () {
        return this.username;
    }

    @Override
    public boolean isEnabled () {
        return this.enabled;
    }

    @Override
    public Collection< ? extends GrantedAuthority > getAuthorities () {
        if ( CollectionUtils.isEmpty( this.getRoles() ) ) {
            return null;
        }
        return this.getRoles().parallelStream()
                .map( role -> new SimpleGrantedAuthority( role.getRoleNameCode() ) )
                .collect( Collectors.toList() );
    }

    @JSONField(serialize=false)
    @Override
    public String getPassword () {
        return this.password;
    }

    @JSONField(serialize=false)
    @Override
    public boolean isAccountNonExpired () {
        return true;
    }

    @JSONField(serialize=false)
    @Override
    public boolean isAccountNonLocked () {
        return true;
    }

    @JSONField(serialize=false)
    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }

    public JwtUser(Integer id, String username, String password, boolean enabled, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
