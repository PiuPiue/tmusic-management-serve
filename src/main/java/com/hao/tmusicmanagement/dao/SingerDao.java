package com.hao.tmusicmanagement.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hao.tmusicmanagement.pojo.artist.pojo.Artist;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SingerDao extends BaseMapper<Artist> {
    void saveOrUpDateBatch(List<Artist>artists);
}
