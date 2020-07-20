package com.ecjtu.hht.redis;

import com.ecjtu.hht.redis.config.RedisConfig;
import com.ecjtu.hht.redis.model.Order;
import com.ecjtu.hht.redis.model.User;
import com.ecjtu.hht.redis.pubsub.RedisPubSub;
import com.ecjtu.hht.redis.utils.LockUtil;
import com.ecjtu.hht.redis.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 相关操作 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private LockUtil lockUtil;

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
        while (true) {
            Order order = null;
            try {
                order = (Order) redisUtils.get("complexOrder");
            } catch (Exception e) {
                System.out.println("redis连接不上了——————————————————");
            }
            System.out.println("测试redis get 复杂对象：" + order);
        }

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


    @Test
    public void testDistributedLock() throws InterruptedException {
        //businessCode();
        // 模拟多个线程
        new Thread(() -> businessCode()).start();
        Thread.sleep(1);
        new Thread(() -> businessCode()).start();

    }

    /**
     * test hset
     */
    @Test
    public void testHset() {
        Object generalAlarm = stringRedisTemplate.opsForHash().get("GeneralAlarm", "DpikeyNotice:ms-resource-manager:1");
        System.out.println(generalAlarm);
    }

    /**
     * test hgetAll
     */
    @Test
    public void testHgetAll() {
        Map<Object, Object> hmget = redisUtils.hmget("upgrade:task");
        System.out.println(hmget);
    }

    /**
     * test hgetAll
     */
    @Test
    public void testHmKeys() {
        Set<Object> keys = redisTemplate.opsForHash().keys("GeneralAlarm");
        System.out.println(keys);
    }

    /**
     * test hmExists
     */
    @Test
    public void testHmExists() {
        System.out.println(TimeUnit.SECONDS);
   /*     Boolean aBoolean = redisTemplate.opsForHash().hasKey("", "");
        System.out.println(aBoolean);*/
    }

    /**
     * test lPush
     */
    @Test
    public void testlPush() {
        Long aLong = redisTemplate.opsForList().leftPush("", "");
    }

    /**
     * test lPush
     */
    @Test
    public void testrename() {
        redisTemplate.rename("", "");
    }

    public void businessCode() {
        String key = "redisDistribute";
        boolean getlock = lockUtil.tryLock(key, TimeUnit.MILLISECONDS, 0L, 10);
        //执行逻辑  假设执行时间为5秒
        if (!getlock) {
            System.out.println("其他实例正在运行————————" + Thread.currentThread().getName());
            return;
        }
        try {
            System.out.println("执行业务逻辑代码——————————" + Thread.currentThread().getName());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
