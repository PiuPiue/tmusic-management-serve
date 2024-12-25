package com.hao.tmusicmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.tmusicmanagement.pojo.LogInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogDao extends BaseMapper<LogInfo> {
}
