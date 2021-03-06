package com.tongwii.ico.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email_account")
    private String emailAccount;

    @JSONField(serialize = false)
    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "real_name")
    private String realName;

    private String phone;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "is_validate_email")
    private Boolean isValidateEmail;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "is_validate_phone")
    private Boolean isValidatePhone;

    @Column(name = "is_validate_user")
    private Boolean isValidateUser;

    @Column(name = "avator_url")
    private String avatorUrl;

    @Column(name = "verify_code")
    private Integer verifyCode;

    @Column(name = "expire_date")
    private Date expireDate;

    private String des;

    /**
     * 用户钱包信息
     */
    @Transient
    private List<Object> userWallets;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return email_account
     */
    public String getEmailAccount() {
        return emailAccount;
    }

    /**
     * @param emailAccount
     */
    public void setEmailAccount(String emailAccount) {
        this.emailAccount = emailAccount;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return nick_name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return real_name
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return id_card
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * @param idCard
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * @return is_enabled
     */
    public Boolean getEnabled() {
        return isEnabled;
    }

    /**
     * @param isEnabled
     */
    public void setEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * @return avator_url
     */
    public String getAvatorUrl() {
        return avatorUrl;
    }

    /**
     * @param avatorUrl
     */
    public void setAvatorUrl(String avatorUrl) {
        this.avatorUrl = avatorUrl;
    }

    /**
     * @return des
     */
    public String getDes() {
        return des;
    }

    /**
     * @param des
     */
    public void setDes(String des) {
        this.des = des;
    }

    public Integer getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(Integer verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public List<Object> getUserWallets() {
        return userWallets;
    }

    public void setUserWallets(List<Object> userWallets) {
        this.userWallets = userWallets;
    }

    public Boolean getValidateEmail() {
        return isValidateEmail;
    }

    public void setValidateEmail(Boolean validateEmail) {
        isValidateEmail = validateEmail;
    }

    public Boolean getValidatePhone() {
        return isValidatePhone;
    }

    public void setValidatePhone(Boolean validatePhone) {
        isValidatePhone = validatePhone;
    }

    public Boolean getValidateUser() {
        return isValidateUser;
    }

    public void setValidateUser(Boolean validateUser) {
        isValidateUser = validateUser;
    }
}