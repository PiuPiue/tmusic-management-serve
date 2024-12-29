package com.hao.tmusicmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hao.tmusicmanagement.dao.SingerDao;
import com.hao.tmusicmanagement.dao.SongDao;
import com.hao.tmusicmanagement.dao.SongListDao;
import com.hao.tmusicmanagement.dao.UserDao;
import com.hao.tmusicmanagement.pojo.home.HomeBean;
import com.hao.tmusicmanagement.pojo.song.domain.Song;
import com.hao.tmusicmanagement.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private SongDao songDao;
    @Autowired
    private SongListDao songListDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SingerDao singerDao;


    @Override
    public HomeBean getHomeData() {
        //获取歌曲总数
        Integer songCount = songDao.selectCount(null);
        //获取歌单总数
        Integer songListCount = songListDao.selectCount(null);
        //获取网站占总人数
        Integer userCount = userDao.selectCount(null);
        //获取歌手数量
        Integer singerCount = singerDao.selectCount(null);
        //获取播放量排名前十歌曲
        LambdaQueryWrapper<Song> songLambdaQueryWrapper = new LambdaQueryWrapper<Song>()
                .orderByDesc(Song::getPlayCount)
                .last("LIMIT 10"); // 添加 LIMIT 子句

        List<Song> songRank = songDao.selectList(songLambdaQueryWrapper);
        return new HomeBean(songCount,singerCount,songListCount,userCount,songRank);
    }
}
