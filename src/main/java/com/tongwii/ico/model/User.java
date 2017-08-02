package com.tongwii.ico.model;

import javax.persistence.*;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    @Column(name = "real_name")
    private String realName;

    private String phone;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "is_validate_email")
    private Boolean isValidateEmail;

    @Column(name = "is_validate_phone")
    private Boolean isValidatePhone;

    @Column(name = "avator_url")
    private String avatorUrl;

    @Column(name = "idCard_front_url")
    private String idcardFrontUrl;

    @Column(name = "idCard_back_url")
    private String idcardBackUrl;

    @Column(name = "idCard_all_url")
    private String idcardAllUrl;

    private String des;

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
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
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
     * @return is_validate_email
     */
    public Boolean getIsValidateEmail() {
        return isValidateEmail;
    }

    /**
     * @param isValidateEmail
     */
    public void setIsValidateEmail(Boolean isValidateEmail) {
        this.isValidateEmail = isValidateEmail;
    }

    /**
     * @return is_validate_phone
     */
    public Boolean getIsValidatePhone() {
        return isValidatePhone;
    }

    /**
     * @param isValidatePhone
     */
    public void setIsValidatePhone(Boolean isValidatePhone) {
        this.isValidatePhone = isValidatePhone;
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
     * @return idCard_front_url
     */
    public String getIdcardFrontUrl() {
        return idcardFrontUrl;
    }

    /**
     * @param idcardFrontUrl
     */
    public void setIdcardFrontUrl(String idcardFrontUrl) {
        this.idcardFrontUrl = idcardFrontUrl;
    }

    /**
     * @return idCard_back_url
     */
    public String getIdcardBackUrl() {
        return idcardBackUrl;
    }

    /**
     * @param idcardBackUrl
     */
    public void setIdcardBackUrl(String idcardBackUrl) {
        this.idcardBackUrl = idcardBackUrl;
    }

    /**
     * @return idCard_all_url
     */
    public String getIdcardAllUrl() {
        return idcardAllUrl;
    }

    /**
     * @param idcardAllUrl
     */
    public void setIdcardAllUrl(String idcardAllUrl) {
        this.idcardAllUrl = idcardAllUrl;
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
}