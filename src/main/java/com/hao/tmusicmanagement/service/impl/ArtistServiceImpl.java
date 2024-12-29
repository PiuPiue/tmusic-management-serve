package com.hao.tmusicmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.hao.tmusicmanagement.Exception.TMusicException;
import com.hao.tmusicmanagement.dao.SingerDao;
import com.hao.tmusicmanagement.dao.SongDao;
import com.hao.tmusicmanagement.dao.UserArtistDao;
import com.hao.tmusicmanagement.pojo.artist.pojo.Artist;
import com.hao.tmusicmanagement.pojo.song.domain.Song;
import com.hao.tmusicmanagement.pojo.user.domain.UserArtist;
import com.hao.tmusicmanagement.service.ArtistService;
import org.apache.commons.math3.analysis.function.Sin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArtistServiceImpl extends ServiceImpl<SingerDao, Artist> implements ArtistService {

    @Autowired
    private SingerDao singerDao;
    @Autowired
    private UserArtistDao userArtistDao;
    @Autowired
    private SongDao songDao;

    @Override
    public void addArtist(Artist artist) {
        artist.setIsDelete(0);
        artist.setCreateTime(LocalDateTime.now());
        singerDao.insert(artist);
    }

    @Override
    public void updateArtist(Artist artist) {
        //首先查看是否存在该歌手,删除歌手不需要删除歌曲，改成未知歌手即可
        Artist artist1 = singerDao.selectById(artist.getId());
        if(artist1==null){
            throw new TMusicException("歌手不存在",410);
        }
        singerDao.updateById(artist);
    }

    @Override
    public void deleteArtist(Long id) {
        //删除该歌手的歌曲
        deleteById(id);
    }

    /**
     * 分页查询歌手
     * 根据名称查询，根据性别获取等方式,除此之外
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<Artist> getArtist(Integer pageNum, Integer pageSize,String name) {
        Page<Artist> page = new Page<>(pageNum, pageSize);
        Page<Artist> artistPage = null;
        LambdaQueryWrapper<Artist> queryWrapper = new LambdaQueryWrapper<Artist>().like(StringUtils.hasText(name),Artist::getName,name);
        artistPage = singerDao.selectPage(page, queryWrapper);
        if(artistPage.getRecords().size()!=0){
            return artistPage;
        }else {
            return new Page<>();
        }
    }

    @Override
    public List<Song> getAllSong(Long id) {
        //首先查看歌手是否存在
        Artist artist = singerDao.selectById(id);
        if(artist==null){
            throw new TMusicException("歌手不存在",410);
        }
        List<Song> songs = songDao.selectList(new LambdaQueryWrapper<Song>().eq(Song::getArtist, id));
        return songs;
    }


    @Override
    public void deleteSong(Long id, Long songId) {
        //查看歌手是否存在，查看歌曲是否存在
        Artist artist = singerDao.selectById(id);
        Song song = songDao.selectById(songId);
        if(artist==null||song==null){
            throw new TMusicException("歌手或歌曲不存在",410);
        }
        //不需要删除歌曲，只需要修改歌曲为0号歌手（未知歌手即可）
        songDao.update(null,new LambdaUpdateWrapper<Song>().eq(Song::getId,songId).set(Song::getArtist,0));
    }

    @Override
    public void deleteBatch(Long[] ids) {
        for (Long id : ids){
            deleteById(id);
        }
    }

    private void deleteById(Long id){
        //首先查看是否存在该歌手
        Artist artist1 = singerDao.selectById(id);
        if(artist1==null){
            throw new TMusicException("歌手不存在",410);
        }
        //随后删除该歌手的关注信息
        userArtistDao.delete(new LambdaQueryWrapper<UserArtist>().eq(UserArtist::getArtistId,id));
        //删除该歌手，同时需要把已经存在的歌曲修改为未知歌手
        LambdaUpdateWrapper<Song> updateWrapper = new LambdaUpdateWrapper<Song>().eq(Song::getArtist, id).set(Song::getArtist, 0);
        songDao.update(null, updateWrapper);
        //最后才可以删除该歌手
        singerDao.deleteById(id);
    }
}
