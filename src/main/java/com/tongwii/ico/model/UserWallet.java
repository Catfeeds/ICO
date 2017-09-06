package com.tongwii.ico.model;


import lombok.Data;

import javax.persistence.*;

@Table(name = "user_wallet")
@Data
public class UserWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token_money_id")
    private Integer tokenMoneyId;

    /**
     * 需要加密
     */
    @Column(name = "token_money_url")
    private String tokenMoneyUrl;

    /**
     * 钱包密钥，加密过后的
     */
//    @JSONField(serialize = false)
    @Column(name = "token_private_key")
    private String tokenPrivateKey;

    @Column(name = "user_id")
    private Integer userId;

    private Integer state;

    /**
     * - 存入钱包
     * - 转出钱包
     */
    private Integer type;

    private String des;

    @Transient
    private TokenMoney tokenMoney;

    @Transient
    private String userWalletBalance;

    public enum WalletType {
        IN_PUT(1),
        OUT_PUT(2);

        private Integer value;

        WalletType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}