package com.hao.tmusicmanagement.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.tmusicmanagement.core.AjaxResult;
import com.hao.tmusicmanagement.pojo.playlist.domain.playlist;
import com.hao.tmusicmanagement.pojo.song.vo.SongVo;

import java.util.List;


public interface SongListService extends IService<playlist> {

    //相关查询条件，根据名称查询，根据作者查询，根据日期进行查询等
    public List<playlist> getSongListByPage(Integer page, Integer pageSize,String name);
    //实现歌单的新增、删除、修改等
    public void addSongList(playlist playlist);
    public AjaxResult deleteSongList(Integer id);
    public AjaxResult updateSongList(playlist playlist);

    //实现歌单中单个歌曲或多个歌曲的删除、添加功能
    public AjaxResult deleteSong(List<Integer> songId,Integer songListId);
    public AjaxResult addSong(Integer songId,Integer songListId);
    //实现歌单中所有歌曲的查询
    public List<SongVo> getSongByPlayList(Integer id);
}
