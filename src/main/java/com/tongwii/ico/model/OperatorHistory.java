package com.tongwii.ico.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "operator_history")
@Data
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

    @Override
    public String toString() {
        return "OperatorHistory{" +
                "id=" + id +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", ip='" + ip + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", operatorTime=" + operatorTime +
                ", des='" + des + '\'' +
                '}';
    }
}