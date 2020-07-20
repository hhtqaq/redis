package com.ecjtu.hht.redis.utils;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

/**
 * @author hht
 * @date 2020/7/17 14:32
 */
public class RedissionUtil {
    private RedissonClient redisson;

    //操作Hash集合
    public void hadd(String key, String f, Object v) {
        redisson.getMap(key).put(f, v);
    }

    public boolean checkHKey(String key, String f) {
        return redisson.getMap(key).containsKey(f);
    }

    public boolean checkKey(String key) {
        return redisson.getKeys().countExists(key) > 0;
    }

    public void set(String key, Object value) {
        redisson.getBucket(key).set(value);
    }

    public String get(String key) {
        return (String) redisson.getBucket(key).get();
    }

    public boolean checkSet(String key, int id) {
        return redisson.getSet(key).contains(id);
    }

    public String getHashV(String key, String f) {
        return (String) redisson.getMap(key).get(f);
    }

    public int getHashVInt(String key, String f) {
        return (Integer) redisson.getMap(key).get(f);
    }
}
