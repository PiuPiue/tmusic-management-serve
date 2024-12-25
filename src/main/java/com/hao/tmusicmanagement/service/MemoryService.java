package com.hao.tmusicmanagement.service;

import java.util.Map;

/**
 * 实现redis的内存管理
 */
public interface MemoryService {
    //仅仅是清空全部内存
    public void clearMemory();

    //实现所有缓存信息的查看
    public Map<String, Object> showMemory();

    //根据key删除缓存信息
    public boolean deleteMemory(String key);

}
