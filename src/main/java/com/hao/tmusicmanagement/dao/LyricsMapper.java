package com.hao.tmusicmanagement.dao;

import com.hao.tmusicmanagement.pojo.song.domain.Lyrics;
import com.hao.tmusicmanagement.pojo.song.vo.LyricsVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Mapper
public interface LyricsMapper extends MongoRepository<Lyrics,String> {
    List<Lyrics> findLyricsBySongId(String songId);
}
