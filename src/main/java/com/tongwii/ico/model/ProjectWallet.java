package com.tongwii.ico.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "project_wallet")
@Data
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
}