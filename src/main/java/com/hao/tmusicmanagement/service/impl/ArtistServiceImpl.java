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
        singerDao.insert(artist);
    }

    @Override
    public void updateArtist(Artist artist) {
        //首先查看是否存在该歌手
        Artist artist1 = singerDao.selectById(artist.getId());
        if(artist1==null){
            throw new TMusicException("歌手不存在",410);
        }
        singerDao.updateById(artist);
    }

    @Override
    public void deleteArtist(List<Long> ids) {
        for(Long id:ids){
            deleteById(id);
        }
    }

    /**
     * 分页查询歌手
     * 根据名称查询，根据性别获取等方式
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<Artist> getArtist(Integer pageNum, Integer pageSize,String name) {
        Page<Artist> page = new Page<>(pageNum, pageSize);
        Page<Artist> artistPage = null;
        if(StringUtils.hasText(name)){
            LambdaQueryWrapper<Artist> queryWrapper = new LambdaQueryWrapper<Artist>().like(Artist::getName,name);
            artistPage = singerDao.selectPage(page, queryWrapper);
        }else{
            artistPage = singerDao.selectPage(page, null);
        }
        return artistPage;
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
