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
public class JwtUser implements UserDetails {
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

    @JsonIgnore
    @Override
    public String getPassword () {
        return this.password;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired () {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked () {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }

    public JwtUser(Integer id, String username, String password, String nickName, String realName, String phone, String idCard, boolean enabled, List<Role> roles) {
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

    public String getNickName() {
        return nickName;
    }

    public String getRealName() {
        return realName;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JwtUser jwtUser = (JwtUser) o;

        if (isEnabled() != jwtUser.isEnabled()) return false;
        if (!getId().equals(jwtUser.getId())) return false;
        if (!getUsername().equals(jwtUser.getUsername())) return false;
        if (!getPassword().equals(jwtUser.getPassword())) return false;
        if (getNickName() != null ? !getNickName().equals(jwtUser.getNickName()) : jwtUser.getNickName() != null)
            return false;
        if (getRealName() != null ? !getRealName().equals(jwtUser.getRealName()) : jwtUser.getRealName() != null)
            return false;
        if (getPhone() != null ? !getPhone().equals(jwtUser.getPhone()) : jwtUser.getPhone() != null) return false;
        if (getIdCard() != null ? !getIdCard().equals(jwtUser.getIdCard()) : jwtUser.getIdCard() != null) return false;
        return getRoles() != null ? getRoles().equals(jwtUser.getRoles()) : jwtUser.getRoles() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getUsername().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + (getNickName() != null ? getNickName().hashCode() : 0);
        result = 31 * result + (getRealName() != null ? getRealName().hashCode() : 0);
        result = 31 * result + (getPhone() != null ? getPhone().hashCode() : 0);
        result = 31 * result + (getIdCard() != null ? getIdCard().hashCode() : 0);
        result = 31 * result + (isEnabled() ? 1 : 0);
        result = 31 * result + (getRoles() != null ? getRoles().hashCode() : 0);
        return result;
    }
}
