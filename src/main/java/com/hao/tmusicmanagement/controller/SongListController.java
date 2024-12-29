package com.hao.tmusicmanagement.controller;

import com.hao.tmusicmanagement.core.AjaxResult;
import com.hao.tmusicmanagement.pojo.playlist.domain.playlist;
import com.hao.tmusicmanagement.pojo.playlist.dto.PlayListSongsDto;
import com.hao.tmusicmanagement.pojo.song.vo.SongVo;
import com.hao.tmusicmanagement.service.SongListService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 实现歌单的增删改查以及歌曲曲目管理
 */
@RestController
@RequestMapping("/songlist")
public class SongListController {

    @Autowired
    private SongListService songListService;

    @PostMapping("/addSongList")
    public AjaxResult addSongList(@RequestBody playlist playlist) {
        songListService.addSongList(playlist);
        return new AjaxResult("添加成功", "200");
    }

    @PostMapping("/updateSongList")
    public AjaxResult updateSongList(@RequestBody playlist playlist) {
        songListService.updateSongList(playlist);
        return new AjaxResult("修改成功", "200");
    }

    @GetMapping("/deleteById")
    public AjaxResult deleteById(Long id) {
        songListService.deleteSongList(id);
        return new AjaxResult("删除成功", "200");
    }

    @PostMapping("/deleteBatch")
    public AjaxResult deleteBatch(@RequestBody List<Long> ids) {
        songListService.deleteBatch(ids);
        return new AjaxResult("删除成功", "200");
    }

    @GetMapping("/getSongListByPage")
    public AjaxResult getSongListByPage(Integer page, Integer pageSize,String name) {
        return new AjaxResult(songListService.getSongListByPage(page,pageSize,name),"查询成功", "200");
    }

    /**
     * 查询歌单中的所有歌曲
     * @param id
     * @return
     */
    @GetMapping("/getSongsById")
    public AjaxResult getSongListById(Long id){
        return new AjaxResult(songListService.getSongByPlayList(id),"查询成功", "200");
    }

    @GetMapping ("/deleteSongs")
    public AjaxResult deleteSong(Long songId,Long songListId){
        songListService.deleteSong(songId,songListId);
        return new AjaxResult("删除成功", "200");
    }

    @GetMapping("/addSongs")
    public AjaxResult addSong(Long songId,Long songListId){
        songListService.addSong(songId,songListId);
        return new AjaxResult("添加成功", "200");
    }

    @GetMapping("/getAllSongs")
    public AjaxResult getAllSongs(Long songListId){
        List<SongVo> songByPlayList = songListService.getSongByPlayList(songListId);
        return new AjaxResult(songByPlayList,"查询成功", "200");
    }



}
