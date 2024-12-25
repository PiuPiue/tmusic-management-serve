package com.hao.tmusicmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.tmusicmanagement.pojo.user.domain.UserArtist;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserArtistDao extends BaseMapper<UserArtist> {
}
