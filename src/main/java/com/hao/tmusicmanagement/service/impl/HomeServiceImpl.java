package com.hao.tmusicmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hao.tmusicmanagement.dao.SongDao;
import com.hao.tmusicmanagement.dao.SongListDao;
import com.hao.tmusicmanagement.dao.UserMapper;
import com.hao.tmusicmanagement.pojo.home.HomeBean;
import com.hao.tmusicmanagement.pojo.song.domain.Song;
import com.hao.tmusicmanagement.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private SongDao songDao;
    @Autowired
    private SongListDao songListDao;
    @Autowired
    private UserMapper userMapper;

    @Override
    public HomeBean getHomeData() {
        //获取歌曲总数
        Integer userCount = songDao.selectCount(null);
        //获取歌单总数
        Integer songListCount = songListDao.selectCount(null);
        //获取网站占总人数
        Integer singerCount = userMapper.selectCount(null);
        //获取播放量排名前十歌曲
        LambdaQueryWrapper<Song> songLambdaQueryWrapper = new LambdaQueryWrapper<Song>().orderByDesc(Song::getPlayCount);
        List<Song>  songRank = songDao.selectList(songLambdaQueryWrapper).stream().limit(10).toList();
        return new HomeBean(userCount, singerCount, songListCount, singerCount,songRank);
    }
}
