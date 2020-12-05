package com.bjtu.redis.jedis;

import com.bjtu.redis.RedisUtil;
import org.junit.Test;

import com.bjtu.redis.JedisInstance;

import redis.clients.jedis.Jedis;

public class JedisInstanceTest {

    /**
     * 基本使用
     */
    @Test
    public void test() {
        RedisUtil j=new RedisUtil();
        j.set("name","text");
        String val=j.get("name");
        System.out.println(val);
        //Jedis jedis = JedisInstance.getInstance().getResource();
        //jedis.setex("name", 20, "test");
        //String val = jedis.get("name");
        //System.out.println(val);
    }

}
