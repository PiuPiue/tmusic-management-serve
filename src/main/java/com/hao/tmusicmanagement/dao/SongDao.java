package com.hao.tmusicmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.tmusicmanagement.pojo.song.domain.Song;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SongDao extends BaseMapper<Song> {
    void saveOrUpDateBatch(List<Song>songs);
}
