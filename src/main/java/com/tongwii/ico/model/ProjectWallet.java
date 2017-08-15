package com.tongwii.ico.model;

import javax.persistence.*;

@Table(name = "project_wallet")
public class ProjectWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "wallet_address")
    private String walletAddress;

    @Column(name = "wallet_private_key")
    private String walletPrivateKey;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "token_money_id")
    private Integer tokenMoneyId;

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
     * @return wallet_address
     */
    public String getWalletAddress() {
        return walletAddress;
    }

    /**
     * @param walletAddress
     */
    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getTokenMoneyId() {
        return tokenMoneyId;
    }

    public void setTokenMoneyId(Integer tokenMoneyId) {
        this.tokenMoneyId = tokenMoneyId;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public TokenMoney getTokenMoney() {
        return tokenMoney;
    }

    public void setTokenMoney(TokenMoney tokenMoney) {
        this.tokenMoney = tokenMoney;
    }

    public String getWalletPrivateKey() {
        return walletPrivateKey;
    }

    public void setWalletPrivateKey(String walletPrivateKey) {
        this.walletPrivateKey = walletPrivateKey;
    }
}