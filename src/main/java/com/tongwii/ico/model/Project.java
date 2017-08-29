package com.tongwii.ico.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tongwii.ico.util.CustomDateSerializer;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "name_cn")
    private String nameCn;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "start_time")
    private Date startTime;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Column(name = "end_time")
    private Date endTime;

    @Transient
    private Double targetInvest;

    @Column(name = "third_endorsement")
    private Boolean thirdEndorsement;

    @Column(name = "part_person_number")
    private Integer partPersonNumber;

    @JSONField(serialize = false)
    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "content")
    private String content;

    private Integer state;

    private String des;

    @Transient
    private TokenDetail outPutTokenDetail;

    @Transient
    private User createUser;

    @Transient
    private String pictureUrl;

    /**
     * 接受代币
     */
    @Transient
    private List<TokenDetail> inputTokenDetails;

    /**
     * 项目对应钱包集合
     */
    @Transient
    private List<ProjectWallet> projectWallets;

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
     * @return name_cn
     */
    public String getNameCn() {
        return nameCn;
    }

    /**
     * @param nameCn
     */
    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    /**
     * @return targetInvest
     */
    public Double getTargetInvest() {
        return targetInvest;
    }

    /**
     * @param targetInvest
     */
    public void setTargetInvest(Double targetInvest) {
        this.targetInvest = targetInvest;
    }

    /**
     * @return start_time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return end_time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return third_endorsement
     */
    public Boolean getThirdEndorsement() {
        return thirdEndorsement;
    }

    /**
     * @param thirdEndorsement
     */
    public void setThirdEndorsement(Boolean thirdEndorsement) {
        this.thirdEndorsement = thirdEndorsement;
    }

    /**
     * @return part_person_number
     */
    public Integer getPartPersonNumber() {
        return partPersonNumber;
    }

    /**
     * @param partPersonNumber
     */
    public void setPartPersonNumber(Integer partPersonNumber) {
        this.partPersonNumber = partPersonNumber;
    }

    /**
     * @return create_user_id
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * @return pictureUrl
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * @param pictureUrl
     */
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }


    /**
     * @return state
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * @return des
     */
    public String getDes() {
        return des;
    }

    public List<TokenDetail> getInputTokenDetails() {
        return inputTokenDetails;
    }

    public void setInputTokenDetails(List<TokenDetail> inputTokenDetails) {
        this.inputTokenDetails = inputTokenDetails;
    }

    public List<ProjectWallet> getProjectWallets() {
        return projectWallets;
    }

    public void setProjectWallets(List<ProjectWallet> projectWallets) {
        this.projectWallets = projectWallets;
    }

    /**
     * @param des
     */
    public void setDes(String des) {
        this.des = des;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public TokenDetail getOutPutTokenDetail() {
        return outPutTokenDetail;
    }

    public void setOutPutTokenDetail(TokenDetail outPutTokenDetail) {
        this.outPutTokenDetail = outPutTokenDetail;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public enum State {
        NOW(0),        //当前众筹
        UN_COMING(1),   //未开始
        END(2);        //已结束

        private Integer state;

        public Integer getState() {
            return state;
        }

        State(Integer state) {
            this.state = state;
        }
    }
}