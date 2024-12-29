package com.hao.tmusicmanagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.tmusicmanagement.pojo.artist.pojo.Artist;
import com.hao.tmusicmanagement.pojo.song.domain.Song;

import java.util.List;

public interface ArtistService extends IService<Artist> {

    //新增歌手
    public void addArtist(Artist artist);

    //修改歌手内容
    public void updateArtist(Artist artist);

    //删除歌手
    public void deleteArtist(Long id);

    //查询歌手
    public Page<Artist> getArtist(Integer pageNum,Integer pageSize,String name);

    List<Song> getAllSong(Long id);

    void deleteSong(Long id, Long songId);

    void deleteBatch(Long[] ids);
}
