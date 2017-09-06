package com.tongwii.ico.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "project_user_wallet_relation")
@Data
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
}