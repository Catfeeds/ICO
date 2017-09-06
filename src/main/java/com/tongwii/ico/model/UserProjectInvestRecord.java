package com.tongwii.ico.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "user_project_invest_record")
@Data
public class UserProjectInvestRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "token_id")
    private Integer tokenId;

    @Column(name = "invest_value")
    private Double investValue;

    @Column(name = "lock_date")
    private Date lockDate;

    @Transient
    private TokenMoney tokenMoney;

    @Transient
    private Project project;

    @Transient
    private ProjectWallet projectWallet;

    @Transient
    private UserWallet userWallet;
}