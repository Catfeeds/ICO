package com.tongwii.ico.service.impl;

import com.sun.xml.internal.bind.v2.TODO;
import com.tongwii.ico.core.AbstractService;
import com.tongwii.ico.dao.UserMapper;
import com.tongwii.ico.model.*;
import com.tongwii.ico.service.*;
import com.tongwii.ico.util.CurrentConfigEnum;
import com.tongwii.ico.util.DesEncoder;
import com.tongwii.ico.util.TokenMoneyEnum;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.ethereum.jsonrpc.TypeConverter;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;


/**
 * Created by Zeral on 2017-08-02.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleRelationService userRoleRelationService;
    @Resource
    private UserWalletService userWalletService;
    @Resource
    private TokenMoneyService tokenMoneyService;
    @Value("${spring.profiles.active}")
    private String env;//当前激活的配置文件

    @Override
    public User findByIdCard(String idCard) {
        User user = new User();
        user.setIdCard(idCard);
        List<User> users = userMapper.select(user);
        if(CollectionUtils.isEmpty(users)){
            return null;
        }
        return users.get(0);
    }

    @Override
    public User findByUsername(String username) {
        User user = new User();
        user.setEmailAccount(username);
        return userMapper.selectOne(user);
    }

    @Override
    public void register(User user) {
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 设置用户昵称
        user.setNickName(user.getEmailAccount().substring(0, user.getEmailAccount().indexOf("@")));
        // 默认激活状态
        user.setEnabled(true);
        // 保存用户
        save(user);
        UserRoleRelation userRoleRelation = new UserRoleRelation();
        userRoleRelation.setRoleId(roleService.findByRoleCode(Role.RoleCode.USER.getCode()).getId());
        userRoleRelation.setUserId(user.getId());
        // 保存用户关系
        userRoleRelationService.save(userRoleRelation);

        // 生成用户钱包，保存钱包信息
        // 比特币钱包
        TokenMoney bitCoin = tokenMoneyService.findByENShortName(TokenMoneyEnum.BTC.name());
        UserWallet bitCoinWallet = new UserWallet();
        bitCoinWallet.setTokenMoneyId(bitCoin.getId());
        bitCoinWallet.setUserId(user.getId());
        ECKey bitCoinKey = new ECKey();
        final NetworkParameters netParams;

        if(env.equals(CurrentConfigEnum.DEV)) {
            netParams = TestNet3Params.get();
        } else {
            netParams = MainNetParams.get();
        }

        Address addressFromKey = bitCoinKey.toAddress(netParams);

        bitCoinWallet.setTokenMoneyUrl(addressFromKey.toBase58());
        // 使用des加密密钥
        DesEncoder desEncoder = new DesEncoder();
        bitCoinWallet.setTokenPrivateKey(desEncoder.encrypt(bitCoinKey.getPrivateKeyAsHex()));
        bitCoinWallet.setDes("比特币钱包");
        // TODO 用枚举重写
        bitCoinWallet.setType(1);
        bitCoinWallet.setState(1);
        userWalletService.save(bitCoinWallet);

        // 以太坊钱包
        TokenMoney ethMoney = tokenMoneyService.findByENShortName(TokenMoneyEnum.ETH.name());
        UserWallet ethWallet = new UserWallet();
        ethWallet.setTokenMoneyId(ethMoney.getId());
        ethWallet.setUserId(user.getId());
        org.ethereum.crypto.ECKey ethKey = new org.ethereum.crypto.ECKey();
        ethWallet.setTokenMoneyUrl(TypeConverter.toJsonHex(ethKey.getAddress()));
        // 使用des加密密钥
        ethWallet.setTokenPrivateKey(desEncoder.encrypt(Hex.toHexString(ethKey.getPrivKeyBytes())));
        ethWallet.setDes("以太坊钱包");
        // TODO 用枚举重写
        ethWallet.setType(1);
        ethWallet.setState(1);
        userWalletService.save(ethWallet);
    }

    @Override
    public void userUploadAvator(Integer userId, String url) {
        User user = findById(userId);
        user.setAvatorUrl(url);
        update(user);
    }

    public boolean emailAccountExist(String emailAccount) {
        if(Objects.nonNull(findByUsername(emailAccount))) {
            return true;
        }
        return false;
    }
}
