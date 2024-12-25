package com.hao.tmusicmanagement.controller;

import com.hao.tmusicmanagement.core.AjaxResult;
import com.hao.tmusicmanagement.pojo.playlist.domain.playlist;
import com.hao.tmusicmanagement.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现歌单的增删改查以及歌曲曲目管理
 */
@RestController
@RequestMapping("/songlist")
public class SongListController {

    @Autowired
    private SongListService songListService;

    @PostMapping("/addSongList")
    public AjaxResult addSongList(playlist playlist) {
        songListService.addSongList(playlist);
        return new AjaxResult("添加成功", "200");
    }

    @PostMapping("/updateSongList")
    public AjaxResult updateSongList(playlist playlist) {
        songListService.updateSongList(playlist);
        return new AjaxResult("修改成功", "200");
    }

    @GetMapping("/deleteById")
    public AjaxResult deleteById(Integer id) {
        songListService.deleteSongList(id);
        return new AjaxResult("删除成功", "200");
    }

    @GetMapping("/getSongListByPage")
    public AjaxResult getSongListByPage(Integer page, Integer pageSize,String name) {
        return new AjaxResult(songListService.getSongListByPage(page,pageSize,name),"查询成功", "200");
    }

}
