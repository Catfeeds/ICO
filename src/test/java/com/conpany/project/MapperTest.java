package com.conpany.project;

import com.tongwii.ico.dao.TokenDetailMapper;
import com.tongwii.ico.model.TokenDetail;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-08-07
 */
public class MapperTest extends Tester {
    @Autowired
    private TokenDetailMapper mapper;

    @Test
    public void tokenDetail() {
        TokenDetail tokenDetail =  mapper.selectByPrimaryKey(1);
        System.out.println(tokenDetail.getTokenMoney().toString());

    }
}
