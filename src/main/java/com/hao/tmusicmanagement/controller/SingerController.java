package com.hao.tmusicmanagement.controller;

import com.hao.tmusicmanagement.core.AjaxResult;
import com.hao.tmusicmanagement.pojo.artist.pojo.Artist;
import com.hao.tmusicmanagement.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 歌手管理
 */
@RestController
@RequestMapping("/singer")
public class SingerController {

    @Autowired
    private ArtistService artistService;


    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/selectByPage")
    public AjaxResult selectByPage(Integer pageNum, Integer pageSize,String name) {
       return new AjaxResult(artistService.getArtist(pageNum, pageSize, name), "获取成功", "200");
    }

    @GetMapping("/getSingerById")
    public AjaxResult getSingerById(Long id) {
        return new AjaxResult(artistService.getById(id), "获取成功", "200");
    }

    @PostMapping("/addSinger")
    public AjaxResult addSinger(@RequestBody Artist artist) {
        artistService.addArtist(artist);
        return new AjaxResult("添加成功", "200");
    }

    @GetMapping ("/deleteById")
    public AjaxResult deleteById(Long id) {
        artistService.deleteArtist(id);
        return new AjaxResult("删除成功", "200");
    }


    @PostMapping("/updateSinger")
    public AjaxResult updateSinger(@RequestBody Artist artist) {
        artistService.updateArtist(artist);
        return new AjaxResult("修改成功", "200");
    }

    @GetMapping("/getAllSong")
    public AjaxResult getAllSong(Long id) {
        return new AjaxResult(artistService.getAllSong(id), "获取成功", "200");
    }

    @GetMapping("/deleteSong")
    public AjaxResult deleteSong(Long id,Long songId){
        artistService.deleteSong(id,songId);
        return new AjaxResult("删除成功", "200");
    }

    @PostMapping("/deleteBatch")
    public AjaxResult deleteBatch(@RequestBody Long[] ids) {
        artistService.deleteBatch(ids);
        return new AjaxResult("删除成功", "200");
    }


}
