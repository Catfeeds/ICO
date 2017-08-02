package com.tongwii.ico.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class AccountCredentials implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private String nickName;
    private String realName;
    private String phone;
    private String idCard;
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

    @JsonIgnore
    @Override
    public Collection< ? extends GrantedAuthority > getAuthorities () {
        if ( CollectionUtils.isEmpty( this.getRoles() ) ) {
            return null;
        }
        return this.getRoles().parallelStream()
                .map( role -> new SimpleGrantedAuthority( role.getRoleNameCode() ) )
                .collect( Collectors.toList() );
    }

    @Override
    public String getPassword () {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired () {
        return true;
    }

    @Override
    public boolean isAccountNonLocked () {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }

    public AccountCredentials(Integer id, String username, String password, String nickName, String realName, String phone, String idCard, boolean enabled, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.realName = realName;
        this.phone = phone;
        this.idCard = idCard;
        this.enabled = enabled;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
