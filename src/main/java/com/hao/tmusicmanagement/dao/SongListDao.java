package com.hao.tmusicmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.hao.tmusicmanagement.pojo.playlist.domain.playlist;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SongListDao extends BaseMapper<playlist>{
    void saveOrUpDateBatch(List<playlist> playlists);
}
