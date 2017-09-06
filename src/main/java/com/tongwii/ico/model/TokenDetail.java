package com.tongwii.ico.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "token_detail")
@Data
public class TokenDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token_money_id")
    private Integer tokenMoneyId;

    @Column(name = "current_number")
    private Double currentNumber;

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

    @Column(name = "token_money_project_id")
    private Integer tokenMoneyProjectId;

    @Column(name = "type")
    private Integer type;

    @Transient
    private TokenMoney tokenMoney;

    public enum TokenDetailType {
        INPUT_CION(1),
        OUTPUT_ICON(2);

        private Integer code;

        public Integer getCode() {
            return code;
        }

        TokenDetailType(Integer code) {
            this.code = code;
        }
    }
}