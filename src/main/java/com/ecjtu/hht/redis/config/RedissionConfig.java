package com.ecjtu.hht.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author hht
 * @date 2020/7/17 14:40
 */
@Configuration
@Slf4j
public class RedissionConfig {


    @Bean
    public RedissonClient getRedisson(Environment environment) {
        String redisClusters = environment.getProperty("spring.redis.cluster.nodes");
        String redisPassword = environment.getProperty("spring.redis.password");
        long initializationTimeOut = 100000;
        String[] nodes = redisClusters.split(",");
        //redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = "redis://" + nodes[i];
        }
        Config config = new Config();
        config.useClusterServers() //这是用的集群server
                .setScanInterval(10000) //设置集群状态扫描时间
                .addNodeAddress(nodes)
                .setTimeout(1000)
                .setPassword(redisPassword);
        config.setThreads(10);
        config.setNettyThreads(10);
        RedissonClient redisson = null;
        long startTime = 0;
        do {
            try {
                redisson = Redisson.create(config);
            } catch (Exception e) {
                log.error("RedissonClient连接出错,等待5s重试", "RedissonManager", e);
                quietlySleep(5000L);
                startTime += 5000;
            }
            if (redisson != null) {
                return redisson;
            }
        } while (startTime < initializationTimeOut);
        return redisson;

    }

    private void quietlySleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
