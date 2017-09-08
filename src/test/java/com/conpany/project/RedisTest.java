package com.conpany.project;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * redis测试类
 *
 * @author Zeral
 * @date 2017-09-07
 */
public class RedisTest extends Tester {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testObj() throws Exception {
        ValueOperations<String, Object> operations=redisTemplate.opsForValue();
        operations.set("user.zxx", "{'username':'zxx', 'password':'zxx'}");
        operations.set("com.neo.f", "{'username':'zxx', 'password':'zxx'}",60, TimeUnit.SECONDS);
        //redisTemplate.delete("com.neo.f");
        boolean exists=redisTemplate.hasKey("com.neo.f");
        if(exists){
            System.out.println("exists is true");
        }else{
            System.out.println("exists is false");
        }
        Assert.assertEquals("zxx", JSON.parseObject(operations.get("com.neo.f").toString()).getString("username"));
    }
}
