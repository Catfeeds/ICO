package com.tongwii.ico.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

@Table(name = "user_wallet")
public class UserWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token_money_id")
    private Integer tokenMoneyId;

    /**
     * 需要加密
     */
    @Column(name = "token_money_url")
    private String tokenMoneyUrl;

    /**
     * 钱包密钥，加密过后的
     */
    @JSONField(serialize = false)
    @Column(name = "token_private_key")
    private String tokenPrivateKey;

    @Column(name = "user_id")
    private Integer userId;

    private Integer state;

    /**
     * - 存入钱包
     * - 转出钱包
     */
    private Integer type;

    private String des;

    @Transient
    private TokenMoney tokenMoney;

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
     * @return token_money_id
     */
    public Integer getTokenMoneyId() {
        return tokenMoneyId;
    }

    /**
     * @param tokenMoneyId
     */
    public void setTokenMoneyId(Integer tokenMoneyId) {
        this.tokenMoneyId = tokenMoneyId;
    }

    /**
     * 获取需要加密
     *
     * @return token_money_url - 需要加密
     */
    public String getTokenMoneyUrl() {
        return tokenMoneyUrl;
    }

    /**
     * 设置需要加密
     *
     * @param tokenMoneyUrl 需要加密
     */
    public void setTokenMoneyUrl(String tokenMoneyUrl) {
        this.tokenMoneyUrl = tokenMoneyUrl;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return state
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取- 存入钱包
            - 转出钱包
     *
     * @return type - - 存入钱包
            - 转出钱包
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置- 存入钱包
            - 转出钱包
     *
     * @param type - 存入钱包
            - 转出钱包
     */
    public void setType(Integer type) {
        this.type = type;
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

    public String getTokenPrivateKey() {
        return tokenPrivateKey;
    }

    public void setTokenPrivateKey(String tokenPrivateKey) {
        this.tokenPrivateKey = tokenPrivateKey;
    }

    public TokenMoney getTokenMoney() {
        return tokenMoney;
    }

    public void setTokenMoney(TokenMoney tokenMoney) {
        this.tokenMoney = tokenMoney;
    }
}