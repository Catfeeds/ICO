package com.tongwii.ico.model;

import javax.persistence.*;

@Table(name = "project_wallet")
public class ProjectWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "wallet_address")
    private String walletAddress;

    @Column(name = "token_money_detail_id")
    private Integer tokenMoneyDetailId;

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

    /**
     * @return token_money_detail_id
     */
    public Integer getTokenMoneyDetailId() {
        return tokenMoneyDetailId;
    }

    /**
     * @param tokenMoneyDetailId
     */
    public void setTokenMoneyDetailId(Integer tokenMoneyDetailId) {
        this.tokenMoneyDetailId = tokenMoneyDetailId;
    }
}