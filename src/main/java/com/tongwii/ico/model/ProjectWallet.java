package com.tongwii.ico.model;

import javax.persistence.*;

@Table(name = "project_wallet")
public class ProjectWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "wallet_address")
    private String walletAddress;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "token_money_detail")
    private Integer tokenMoneyDetail;

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

    public Integer getTokenMoneyDetail() {
        return tokenMoneyDetail;
    }

    public void setTokenMoneyDetail(Integer tokenMoneyDetail) {
        this.tokenMoneyDetail = tokenMoneyDetail;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}