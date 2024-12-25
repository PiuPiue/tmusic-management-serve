package com.hao.tmusicmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.tmusicmanagement.pojo.song.domain.PlayListSong;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.annotation.Id;

import java.lang.reflect.Type;
import java.util.List;

@Mapper
public interface PlayListSongDao extends BaseMapper<PlayListSong> {


    void saveOrUpDateBatch(List<PlayListSong> playListSongs);
}
