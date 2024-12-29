package com.hao.tmusicmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.tmusicmanagement.Exception.TMusicException;
import com.hao.tmusicmanagement.dao.*;
import com.hao.tmusicmanagement.pojo.artist.pojo.Artist;
import com.hao.tmusicmanagement.pojo.song.domain.Lyrics;
import com.hao.tmusicmanagement.pojo.song.domain.PlayListSong;
import com.hao.tmusicmanagement.pojo.song.domain.Song;
import com.hao.tmusicmanagement.pojo.song.domain.UserSong;
import com.hao.tmusicmanagement.pojo.song.dto.SongDto;
import com.hao.tmusicmanagement.pojo.song.vo.LyricsVo;
import com.hao.tmusicmanagement.pojo.song.vo.SongVo;
import com.hao.tmusicmanagement.service.SongService;
import com.hao.tmusicmanagement.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
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
    @Autowired
    private LyricsMapper lyricsMapper;

    @Override
    public void addSong(SongDto song) {

        //初始化参数
        Song song1 = BeanUtils.copyBean(song, Song.class);
        song1.setLyrics(1L);
        song1.setCreateTime(LocalDateTime.now());
        song1.setDownloadCount(100);
        song1.setPlayCount(100);
        song1.setTagId("1");
        song1.setIsDelete(false);
        //将歌词插入到MongoDB,需要预处理歌词
        String lyrics = song.getLyrics1();
        songDao.insert(song1);
        lyricsMapper.saveAll(parseLyrics(lyrics, String.valueOf(song1.getId())));

    }

    private List<Lyrics> parseLyrics(String lyrics,String songId){
        List<Lyrics> collect = Arrays.stream(lyrics.split("\n")).map(
                line -> {
                    String[] split = line.split("]");
                    if (split.length == 2) {
                        Lyrics lyrics1 = new Lyrics();
                        lyrics1.setText(split[1]);
                        lyrics1.setTime(split[0].replace("[", ""));
                        lyrics1.setSongId(songId);
                        return lyrics1;
                    }
                    return null;
                }
        ).filter(l -> l != null).collect(Collectors.toList());
        return collect;
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
        //歌曲相似直接查询即可，但歌手需要先找出歌手再查找
        List<Artist> artists = singerDao.selectList(new LambdaQueryWrapper<Artist>().like(Artist::getName, name));
        Page<Song> songPage = new Page<>(pageNum, pageSize);
        new Page<SongVo>(pageNum,pageSize);
        if(StringUtils.hasText(name)){
            LambdaQueryWrapper<Song> eq = new LambdaQueryWrapper<Song>().like(Song::getTitle, name).or().in(artists.size()!=0,Song::getArtist, artists.stream().map(Artist::getId).collect(Collectors.toList())).orderByDesc(Song::getId);
            songPage = songDao.selectPage(songPage, eq);
        }else{
            LambdaQueryWrapper<Song> songLambdaQueryWrapper = new LambdaQueryWrapper<Song>().orderByDesc(Song::getId);
            songPage = songDao.selectPage(songPage, songLambdaQueryWrapper);
        }
        if(songPage.getRecords().size()==0){
            return new Page<>();
        }
        List<SongVo> collect = songPage.getRecords().stream().map(song -> convert(song)).collect(Collectors.toList());
        List<SongVo> collect1 = collect.stream().map(songVo -> {
            List<Lyrics> lyricsBySongId = lyricsMapper.findLyricsBySongId(String.valueOf(songVo.getId()));
            if (lyricsBySongId != null) {
                List<LyricsVo> lyricsVos = BeanUtils.copyList(lyricsBySongId, LyricsVo.class);
                songVo.setLyricslist(lyricsVos);
            }
            return songVo;
        }).collect(Collectors.toList());
        Page<SongVo> songVoPage = new Page<>();
        songVoPage.setRecords(collect1);
        songVoPage.setCurrent(songPage.getCurrent());
        songVoPage.setSize(songPage.getSize());
        songVoPage.setTotal(songPage.getTotal());
        return songVoPage;
    }

    @Override
    public void deleteSongs(Long[] ids) {
        for (Long id : ids){
            deleteSong(id);
        }
    }

    private SongVo convert(Song song){
        SongVo songVo = BeanUtils.copyBean(song, SongVo.class);
        Artist artist = singerDao.selectById(song.getArtist());
        if(artist==null){
            songVo.setArtist("未知歌手");
            return songVo;
        }
        songVo.setArtist(artist.getName());
        return songVo;
    }
}
