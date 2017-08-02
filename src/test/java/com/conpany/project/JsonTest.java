package com.conpany.project;

import com.alibaba.fastjson.JSON;
import com.tongwii.ico.core.Result;
import org.junit.Test;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-08-02
 */
public class JsonTest extends Tester {

    @Test
    public void test1() {
        System.out.println(JSON.toJSONString(Result.successResult()));
    }
}
