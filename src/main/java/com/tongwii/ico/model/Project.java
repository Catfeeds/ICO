package com.tongwii.ico.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tongwii.ico.util.CustomDateSerializer;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "third_endorsement")
    private Boolean thirdEndorsement;

    @JSONField(serialize = false)
    @Column(name = "output_token_money_detail_id")
    private Integer outputTokenMoneyDetailId;

    @JSONField(serialize = false)
    @Column(name = "input_token_money_datail_id")
    private Integer inputTokenMoneyDatailId;

    @Column(name = "part_person_number")
    private Integer partPersonNumber;

    @JSONField(serialize = false)
    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "project_wallet_id")
    private Integer projectWalletId;

    @Column(name = "content")
    private String content;

    private Integer state;

    private String des;

    @Transient
    private TokenDetail inputTokenDetail;

    @Transient
    private TokenDetail outPutTokenDetail;

    @Transient
    private User createUser;

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
     * @return output_token_money_detail_id
     */
    public Integer getOutputTokenMoneyDetailId() {
        return outputTokenMoneyDetailId;
    }

    /**
     * @param outputTokenMoneyDetailId
     */
    public void setOutputTokenMoneyDetailId(Integer outputTokenMoneyDetailId) {
        this.outputTokenMoneyDetailId = outputTokenMoneyDetailId;
    }

    /**
     * @return input_token_money_datail_id
     */
    public Integer getInputTokenMoneyDatailId() {
        return inputTokenMoneyDatailId;
    }

    /**
     * @param inputTokenMoneyDatailId
     */
    public void setInputTokenMoneyDatailId(Integer inputTokenMoneyDatailId) {
        this.inputTokenMoneyDatailId = inputTokenMoneyDatailId;
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
     * @return project_wallet_id
     */
    public Integer getProjectWalletId() {
        return projectWalletId;
    }

    /**
     * @param projectWalletId
     */
    public void setProjectWalletId(Integer projectWalletId) {
        this.projectWalletId = projectWalletId;
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

    public TokenDetail getInputTokenDetail() {
        return inputTokenDetail;
    }

    public void setInputTokenDetail(TokenDetail inputTokenDetail) {
        this.inputTokenDetail = inputTokenDetail;
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