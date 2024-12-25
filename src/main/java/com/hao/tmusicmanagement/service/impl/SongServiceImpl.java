package com.hao.tmusicmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.tmusicmanagement.Exception.TMusicException;
import com.hao.tmusicmanagement.dao.PlayListSongDao;
import com.hao.tmusicmanagement.dao.SingerDao;
import com.hao.tmusicmanagement.dao.SongDao;
import com.hao.tmusicmanagement.dao.UserSongDao;
import com.hao.tmusicmanagement.pojo.song.domain.PlayListSong;
import com.hao.tmusicmanagement.pojo.song.domain.Song;
import com.hao.tmusicmanagement.pojo.song.domain.UserSong;
import com.hao.tmusicmanagement.pojo.song.vo.SongVo;
import com.hao.tmusicmanagement.service.SongService;
import com.hao.tmusicmanagement.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongServiceImpl extends ServiceImpl<SongDao, Song> implements SongService {

    @Autowired
    private SongDao songDao;
    @Autowired
    private UserSongDao userSongDao;
    @Autowired
    private PlayListSongDao playListSongDao;
    @Autowired
    private SingerDao singerDao;

    @Override
    public void addSong(Song song) {
        songDao.insert(song);
    }

    @Override
    public void deleteSong(Long id) {
        //先查询歌曲是否存在
        Song song1 = songDao.selectById(id);
        if(song1==null){
            throw new TMusicException("歌曲不存在",410);
        }
        //随后删除喜欢记录
        userSongDao.delete(new LambdaQueryWrapper<UserSong>().eq(UserSong::getSongId,id));
        //再删除歌单记录
        playListSongDao.delete(new LambdaQueryWrapper<PlayListSong>().eq(PlayListSong::getSongId,id));
        //最后删除该歌曲
        songDao.deleteById(id);

    }

    @Override
    public void updateSong(Song song) {
        //先查询歌曲是否存在
        Song song1 = songDao.selectById(song.getId());
        if(song1==null){
            throw new TMusicException("歌曲不存在",410);
        }
        songDao.updateById(song);
    }

    @Override
    public Page<SongVo> getSong(Integer pageNum, Integer pageSize, String name) {
        Page<Song> songPage = new Page<>(pageNum, pageSize);
        new Page<SongVo>(pageNum,pageSize);
        if(StringUtils.hasText(name)){
            LambdaQueryWrapper<Song> eq = new LambdaQueryWrapper<Song>().eq(Song::getTitle, name).or().eq(Song::getArtist, name);
            songPage = songDao.selectPage(songPage, eq);
        }else{
            songPage = songDao.selectPage(songPage, null);
        }
        List<SongVo> collect = songPage.getRecords().stream().map(song -> convert(song)).collect(Collectors.toList());
        Page<SongVo> songVoPage = new Page<>();
        songVoPage.setRecords(collect);
        songVoPage.setCurrent(songPage.getCurrent());
        songVoPage.setSize(songPage.getSize());
        songVoPage.setTotal(songPage.getTotal());
        return songVoPage;
    }

    private SongVo convert(Song song){
        SongVo songVo = BeanUtils.copyBean(song, SongVo.class);
        songVo.setArtist(singerDao.selectById(song.getId()).getName());
        return songVo;
    }
}
