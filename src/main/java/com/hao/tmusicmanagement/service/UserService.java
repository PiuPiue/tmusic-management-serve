package com.hao.tmusicmanagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.tmusicmanagement.pojo.artist.pojo.Artist;
import com.hao.tmusicmanagement.pojo.playlist.domain.playlist;
import com.hao.tmusicmanagement.pojo.song.domain.Song;
import com.hao.tmusicmanagement.pojo.user.domain.User;

import java.util.List;

public interface UserService {
    Page<User> getUserByPage(Integer pageNum, Integer pageSize,String name);

    //新增用户
    void addUser(User user);

    //修改用户
    void updateUser(User user);

    //查看用户关注歌手
    List<Artist> getUserFollowSinger(Long id);

    //查看用户关注歌单
    List<playlist> getUserFollowSongList(Long id);

    //查看用户喜欢音乐
    List<Song> getUserLikeSong(Long id);

    //封禁用户
    void banUser(Long id);


}
