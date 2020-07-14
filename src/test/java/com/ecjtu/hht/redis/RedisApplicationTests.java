package com.ecjtu.hht.redis;

import com.ecjtu.hht.redis.config.RedisConfig;
import com.ecjtu.hht.redis.model.Order;
import com.ecjtu.hht.redis.model.User;
import com.ecjtu.hht.redis.pubsub.RedisPubSub;
import com.ecjtu.hht.redis.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * redis 相关操作 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableScheduling
public class RedisApplicationTests {

    @Autowired
    private RedisUtils redisUtils;


    @Autowired
    private RedisPubSub redisPubSub;

    //-----------------------------------------------1 redis 的基本操作

    /**
     * 测试redis 简单的set字符串
     */
    @Test
    public void testRedisSetString() {
        System.out.println("测试redis 简单的set字符串");
        redisUtils.set("aa", "123", 1000);
    }

    /**
     * 测试redis  简单的get
     */
    @Test
    public void testRedisGetString() {
        System.out.println("测试redis  简单的get");
        System.out.println(redisUtils.get("aa"));
    }

    /**
     * 测试redis set 简单对象
     */
    @Test
    public void testRedisSetObject() {
        User user = new User("1", "zhangsan");
        redisUtils.set("user", user);
    }

    /**
     * 测试redis get 简单对象
     */
    @Test
    public void testRedisGetObject() {
        User user = (User) redisUtils.get("user");
        System.out.println("测试redis get 简单对象:" + user);
    }

    /**
     * 测试redis set 复杂对象
     */
    @Test
    public void testRedisSetComplexObject() {
        User user = new User("1", "zhangsan");
        Order order = new Order("1", "电风扇", new BigDecimal(1000), user);
        System.out.println("测试redis set 复杂对象:" + order);
        redisUtils.set("complexOrder", order);
    }

    /**
     * 测试redis get 复杂对象
     */
    @Test
    public void testRedisGetComplexObject() {
        Order order = (Order) redisUtils.get("complexOrder");
        System.out.println("测试redis get 复杂对象：" + order);
    }
    //------------------------------------------- 2 高级操作

    /**
     * 测试redis 发布订阅
     */
    @Test
    public void testRedisPubSub() throws Exception {
        System.out.println("测试发布订阅");
        redisPubSub.publish("hello");
        String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

}
