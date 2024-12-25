package com.hao.tmusicmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.tmusicmanagement.pojo.song.domain.UserSong;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserSongDao extends BaseMapper<UserSong> {
}
