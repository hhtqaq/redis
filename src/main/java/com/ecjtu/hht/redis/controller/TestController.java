package com.ecjtu.hht.redis.controller;

import com.ecjtu.hht.redis.model.Order;
import com.ecjtu.hht.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.cache.annotation.CacheRemove;
import javax.websocket.server.PathParam;

/**
 * @author hht
 * @date 2020/7/20 10:46
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/order/{key}")
    public Object getOrder(@PathVariable("key") String key) {
        Object obj = null;
        try {
            obj = redisUtils.get(key);
            log.info(obj.toString());
        } catch (Exception e) {
            log.error("redis连接不上了——————————————————", e);
        }
        return obj;
    }
}
