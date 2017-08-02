package com.tongwii.ico.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "operator_history")
public class OperatorHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    private String message;

    private String ip;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "operator_time")
    private Date operatorTime;

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
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return ip_address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return operator_time
     */
    public Date getOperatorTime() {
        return operatorTime;
    }

    /**
     * @param operatorTime
     */
    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
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