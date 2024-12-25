package com.hao.tmusicmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.tmusicmanagement.pojo.playlist.domain.UserPlaylist;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPlaylistDao extends BaseMapper<UserPlaylist> {
}
