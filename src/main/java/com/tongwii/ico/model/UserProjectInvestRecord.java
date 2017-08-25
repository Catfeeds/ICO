package com.tongwii.ico.model;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "user_project_invest_record")
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
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return project_id
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * @param projectId
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * @return token_id
     */
    public Integer getTokenId() {
        return tokenId;
    }

    /**
     * @param tokenId
     */
    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    /**
     * @return invest_value
     */
    public Double getInvestValue() {
        return investValue;
    }

    /**
     * @param investValue
     */
    public void setInvestValue(Double investValue) {
        this.investValue = investValue;
    }

    /**
     * @return lock_date
     */
    public Date getLockDate() {
        return lockDate;
    }

    /**
     * @param lockDate
     */
    public void setLockDate(Date lockDate) {
        this.lockDate = lockDate;
    }

    public TokenMoney getTokenMoney() {
        return tokenMoney;
    }

    public void setTokenMoney(TokenMoney tokenMoney) {
        this.tokenMoney = tokenMoney;
    }

    public ProjectWallet getProjectWallet() {
        return projectWallet;
    }

    public void setProjectWallet(ProjectWallet projectWallet) {
        this.projectWallet = projectWallet;
    }

    public UserWallet getUserWallet() {
        return userWallet;
    }

    public void setUserWallet(UserWallet userWallet) {
        this.userWallet = userWallet;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}