package com.hao.tmusicmanagement.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.tmusicmanagement.core.AjaxResult;
import com.hao.tmusicmanagement.pojo.user.domain.User;
import com.hao.tmusicmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping ("/getUser")
    public AjaxResult getUsers(Integer page, Integer pageSize,String name){
        Page<User> userByPage = userService.getUserByPage(page, pageSize, name);
        return new AjaxResult(userByPage, "查询成功", "200");
    }

    @PostMapping ("/addUser")
    public AjaxResult addUser(@RequestBody User user){
        userService.addUser(user);
        return new AjaxResult("添加成功", "200");
    }

    @PostMapping ("/updateUser")
    public AjaxResult updateUser(@RequestBody User user){
        userService.updateUser(user);
        return new AjaxResult("修改成功", "200");
    }

    @GetMapping ("/banUser")
    public AjaxResult banUser(Long id){
        userService.banUser(id);
        return new AjaxResult("封禁成功", "200");
    }

    //获取用户收藏音乐
    @GetMapping("/getUserLikeSong")
    public AjaxResult getUserLikeSong(Long id){
        return new AjaxResult(userService.getUserLikeSong(id), "查询成功", "200");
    }

    //获取用户喜欢歌单
    @GetMapping("/getUserFollowSongList")
    public AjaxResult getUserFollowSongList(Long id){
        return new AjaxResult(userService.getUserFollowSongList(id), "查询成功", "200");
    }

    //获取用户关注歌手
    @GetMapping("/getUserFollowSinger")
    public AjaxResult getUserFollowSinger(Long id){
        return new AjaxResult(userService.getUserFollowSinger(id), "查询成功", "200");
    }

}
