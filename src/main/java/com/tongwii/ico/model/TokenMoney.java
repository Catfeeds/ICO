package com.tongwii.ico.model;

import javax.persistence.*;

@Table(name = "token_money")
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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return name_en_short
     */
    public String getNameEnShort() {
        return nameEnShort;
    }

    /**
     * @param nameEnShort
     */
    public void setNameEnShort(String nameEnShort) {
        this.nameEnShort = nameEnShort;
    }

    /**
     * @return official_url
     */
    public String getOfficialUrl() {
        return officialUrl;
    }

    /**
     * @param officialUrl
     */
    public void setOfficialUrl(String officialUrl) {
        this.officialUrl = officialUrl;
    }

    /**
     * @return twitter_url
     */
    public String getTwitterUrl() {
        return twitterUrl;
    }

    /**
     * @param twitterUrl
     */
    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    /**
     * @return github_url
     */
    public String getGithubUrl() {
        return githubUrl;
    }

    /**
     * @param githubUrl
     */
    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
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