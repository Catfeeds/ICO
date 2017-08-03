package com.tongwii.ico.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tongwii.ico.util.CustomDateSerializer;

import java.util.Date;
import javax.persistence.*;

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

    @Column(name = "output_token_money_detail_id")
    private Integer outputTokenMoneyDetailId;

    @Column(name = "input_token_money_datail_id")
    private Integer inputTokenMoneyDatailId;

    @Column(name = "part_person_number")
    private Integer partPersonNumber;

    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "project_wallet_id")
    private Integer projectWalletId;

    private Byte state;

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
    public Byte getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Byte state) {
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
}