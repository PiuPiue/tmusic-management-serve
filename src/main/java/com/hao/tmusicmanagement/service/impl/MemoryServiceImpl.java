package com.hao.tmusicmanagement.service.impl;

import com.hao.tmusicmanagement.service.MemoryService;
import com.hao.tmusicmanagement.util.redis.RedisCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class MemoryServiceImpl implements MemoryService {

    @Autowired
    private RedisCache redisCache;

    //删除全部缓存信息
    @Override
    public void clearMemory() {
        redisCache.deleteKeysByPattern("*");
    }

    //查询所有缓存信息
    @Override
    public Map<String, Object> showMemory() {
        Map<String, Object> memoryInfo = new HashMap<>();
        Collection<String> keys = redisCache.keys("*");

        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                Object value = redisCache.getCacheObject(key);
                memoryInfo.put(key, value);
            }
        }

        return memoryInfo;
    }

    //实现缓存的删除
    @Override
    public boolean deleteMemory(String key) {
        if (redisCache.deleteObject(key)) {
            return true;
        }
        return false;
    }

}
