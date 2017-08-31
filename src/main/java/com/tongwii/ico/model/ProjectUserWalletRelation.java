package com.tongwii.ico.model;

import javax.persistence.*;

@Table(name = "project_user_wallet_relation")
public class ProjectUserWalletRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_wallet")
    private Integer userWallet;

    @Column(name = "project_wallet")
    private Integer projectWallet;

    @Transient
    private String walletType;
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
     * @return user_wallet
     */
    public Integer getUserWallet() {
        return userWallet;
    }

    /**
     * @param userWallet
     */
    public void setUserWallet(Integer userWallet) {
        this.userWallet = userWallet;
    }

    /**
     * @return project_wallet
     */
    public Integer getProjectWallet() {
        return projectWallet;
    }

    /**
     * @param projectWallet
     */
    public void setProjectWallet(Integer projectWallet) {
        this.projectWallet = projectWallet;
    }

    /**
     * @return walletType
     */
    public String getWalletType() {
        return walletType;
    }

    /**
     * @param walletType
     */
    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }
}