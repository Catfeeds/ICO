package com.tongwii.ico.model;

import javax.persistence.*;

@Table(name = "token_detail")
public class TokenDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token_money_id")
    private Integer tokenMoneyId;

    @Column(name = "current_number")
    private Integer currentNumber;

    @Column(name = "ico_number")
    private Integer icoNumber;

    @Column(name = "min_target_number")
    private Integer minTargetNumber;

    @Column(name = "target_number")
    private Integer targetNumber;

    @Column(name = "token_money_whitePaper_cn_url")
    private String tokenMoneyWhitepaperCnUrl;

    @Column(name = "token_money_whitePaper_en_url")
    private String tokenMoneyWhitepaperEnUrl;

    @Column(name = "input_token_money_project_id")
    private Integer inputTokenMoneyProjectId;

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
     * @return current_number
     */
    public Integer getCurrentNumber() {
        return currentNumber;
    }

    /**
     * @param currentNumber
     */
    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
    }

    /**
     * @return ico_number
     */
    public Integer getIcoNumber() {
        return icoNumber;
    }

    /**
     * @param icoNumber
     */
    public void setIcoNumber(Integer icoNumber) {
        this.icoNumber = icoNumber;
    }

    /**
     * @return min_target_number
     */
    public Integer getMinTargetNumber() {
        return minTargetNumber;
    }

    /**
     * @param minTargetNumber
     */
    public void setMinTargetNumber(Integer minTargetNumber) {
        this.minTargetNumber = minTargetNumber;
    }

    /**
     * @return target_number
     */
    public Integer getTargetNumber() {
        return targetNumber;
    }

    /**
     * @param targetNumber
     */
    public void setTargetNumber(Integer targetNumber) {
        this.targetNumber = targetNumber;
    }

    /**
     * @return token_money_whitePaper_cn_url
     */
    public String getTokenMoneyWhitepaperCnUrl() {
        return tokenMoneyWhitepaperCnUrl;
    }

    /**
     * @param tokenMoneyWhitepaperCnUrl
     */
    public void setTokenMoneyWhitepaperCnUrl(String tokenMoneyWhitepaperCnUrl) {
        this.tokenMoneyWhitepaperCnUrl = tokenMoneyWhitepaperCnUrl;
    }

    /**
     * @return token_money_whitePaper_en_url
     */
    public String getTokenMoneyWhitepaperEnUrl() {
        return tokenMoneyWhitepaperEnUrl;
    }

    /**
     * @param tokenMoneyWhitepaperEnUrl
     */
    public void setTokenMoneyWhitepaperEnUrl(String tokenMoneyWhitepaperEnUrl) {
        this.tokenMoneyWhitepaperEnUrl = tokenMoneyWhitepaperEnUrl;
    }

    public TokenMoney getTokenMoney() {
        return tokenMoney;
    }

    public void setTokenMoney(TokenMoney tokenMoney) {
        this.tokenMoney = tokenMoney;
    }

    public Integer getInputTokenMoneyProjectId() {
        return inputTokenMoneyProjectId;
    }

    public void setInputTokenMoneyProjectId(Integer inputTokenMoneyProjectId) {
        this.inputTokenMoneyProjectId = inputTokenMoneyProjectId;
    }
}