package com.tongwii.ico.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tongwii.ico.util.CustomDateSerializer;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
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