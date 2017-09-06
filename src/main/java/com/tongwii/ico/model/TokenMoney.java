package com.tongwii.ico.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "token_money")
@Data
public class TokenMoney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "name_en_short")
    private String nameEnShort;

    @Column(name = "official_url")
    private String officialUrl;

    @Column(name = "twitter_url")
    private String twitterUrl;

    @Column(name = "github_url")
    private String githubUrl;

    private String des;
}