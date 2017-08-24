package com.tongwii.ico.service.impl;

import com.tongwii.ico.exception.ServiceException;
import com.tongwii.ico.util.DesEncoder;
import com.tongwii.ico.util.EthConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ethereum.config.SystemProperties;

import org.ethereum.crypto.ECKey;
import org.ethereum.facade.Ethereum;
import org.ethereum.facade.EthereumFactory;
import org.ethereum.jsonrpc.TypeConverter;

import org.ethereum.util.ByteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * 以太坊服务
 *
 * @author Zeral
 * @date 2017-08-23
 */
public class EthService {
    private final static Logger logger = LogManager.getLogger();

    private static Ethereum ethereum;

    @PostConstruct
    public void init() {
        //Sync BlockChain
        ethereum = EthereumFactory.createEthereum();
    }


    public String sendTransaction(String fromPrivateEncoderAddress, String toAddress, String amount) {
        try {
            DesEncoder desEncoder = new DesEncoder();
            //Init Sender and Receipt Address
            ECKey sender =  ECKey.fromPrivate(Hex.decode(desEncoder.decrypt(fromPrivateEncoderAddress))) ;
            //Create tx to send ether
            org.ethereum.core.Transaction tx = new org.ethereum.core.Transaction(
                    ByteUtil.bigIntegerToBytes(ethereum.getRepository().getNonce(sender.getPubKey())),
                    ByteUtil.longToBytesNoLeadZeroes(ethereum.getGasPrice()),
                    ByteUtil.longToBytesNoLeadZeroes(200000),
                    TypeConverter.StringHexToByteArray(toAddress),
                    ByteUtil.bigIntegerToBytes(EthConverter.toWei(amount, EthConverter.Unit.ETHER).toBigInteger()),
                    new byte[0],
                    ethereum.getChainIdForNextBlock()) ;

            //Sign it
            tx.sign(sender);

            // Validate it
            tx.verify();

            //Submit it
            ethereum.submitTransaction(tx);

            return TypeConverter.toJsonHex(tx.getHash());
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
