package com.hao.tmusicmanagement.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.tmusicmanagement.core.AjaxResult;
import com.hao.tmusicmanagement.pojo.song.domain.Song;
import com.hao.tmusicmanagement.pojo.song.vo.SongVo;
import com.hao.tmusicmanagement.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 实现歌单基础的增删改查以及歌单的收藏，歌曲等方面的管理
 */
@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping("/addSong")
    public AjaxResult addSong(@RequestBody Song song) {
        songService.addSong(song);
        return new AjaxResult("添加成功", "200");
    }

    @GetMapping("/deleteSong")
    public AjaxResult deleteSong(Long id) {
        songService.deleteSong(id);
        return new AjaxResult("删除成功", "200");
    }

    @PostMapping("/updateSong")
    public AjaxResult updateSong(@RequestBody Song song) {
        songService.updateSong(song);
        return new AjaxResult("修改成功", "200");
    }

    @GetMapping("/selectByPage")
    public AjaxResult selectByPage(Integer pageNum, Integer pageSize, String name) {
        Page<SongVo> song = songService.getSong(pageNum, pageSize, name);
        return new AjaxResult(song, "200", "查询成功");
    }





}
