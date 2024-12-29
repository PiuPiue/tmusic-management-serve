package com.hao.tmusicmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hao.tmusicmanagement.Exception.TMusicException;
import com.hao.tmusicmanagement.dao.*;
import com.hao.tmusicmanagement.pojo.artist.pojo.Artist;
import com.hao.tmusicmanagement.pojo.playlist.domain.UserPlaylist;
import com.hao.tmusicmanagement.pojo.playlist.domain.playlist;
import com.hao.tmusicmanagement.pojo.song.domain.Song;
import com.hao.tmusicmanagement.pojo.song.domain.UserSong;
import com.hao.tmusicmanagement.pojo.user.domain.User;
import com.hao.tmusicmanagement.pojo.user.domain.UserArtist;
import com.hao.tmusicmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserSongDao userSongDao;
    @Autowired
    private UserPlaylistDao userPlaylistDao;
    @Autowired
    private UserArtistDao userArtistDao;
    @Autowired
    private SingerDao singerDao;
    @Autowired
    private SongListDao songListDao;
    @Autowired
    private SongDao songDao;


    @Override
    public Page<User> getUserByPage(Integer pageNum, Integer pageSize, String name) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> like = new LambdaQueryWrapper<User>().like(StringUtils.hasText(name), User::getNickname, name);
        Page<User> userPage = userDao.selectPage(page,like);
        if(userPage.getRecords().size()!=0){
            return userPage;
        }else {
            return new Page<>();
        }
    }

    @Override
    public void addUser(User user) {
        //首先查找是否有相同account
        List<User> users = userDao.selectList(new LambdaQueryWrapper<User>().eq(User::getAccount, user.getAccount()));
        if(users.size()>=1){
            throw new TMusicException("账号已存在",410);
        }
        user.setCreateTime(LocalDateTime.now());
        user.setIsDelete(0);
        userDao.insert(user);
    }

    @Override
    public void updateUser(User user) {
        //查找user是否存在
        checkUser(user.getId());
        userDao.updateById(user);
    }

    @Override
    public List<Artist> getUserFollowSinger(Long id) {
        //查找user是否存在
        checkUser(id);
        //获取用户收藏的歌手
        LambdaQueryWrapper<UserArtist> eq = new LambdaQueryWrapper<UserArtist>().eq(UserArtist::getUserId, id);
        List<UserArtist> userArtists = userArtistDao.selectList(eq);
        //获取所有歌手id
        List<Long> list = userArtists.stream().map(userArtist -> userArtist.getArtistId()).toList();
        if(list.size()==0){
            return null;
        }
        List<Artist> artists = singerDao.selectList(new LambdaQueryWrapper<Artist>().in(Artist::getId, list));
        if(artists.size()!=0){
            return artists;
        }
        return null;
    }

    @Override
    public List<playlist> getUserFollowSongList(Long id) {
        checkUser(id);
        LambdaQueryWrapper<UserPlaylist> eq = new LambdaQueryWrapper<UserPlaylist>().eq(UserPlaylist::getUserId, id);
        List<UserPlaylist> userPlaylists = userPlaylistDao.selectList(eq);
        List<Long> list = userPlaylists.stream().map(userPlaylist -> userPlaylist.getPlaylistId()).toList();
        if(list.size()==0){
            return null;
        }
        LambdaQueryWrapper<playlist> in = new LambdaQueryWrapper<playlist>().in( playlist::getId, list);
        List<playlist> playlists = songListDao.selectList(in);
        if(playlists.size()!=0){
            return playlists;
        }
        return null;
    }

    @Override
    public List<Song> getUserLikeSong(Long id) {
        checkUser(id);
        LambdaQueryWrapper<UserSong> eq = new LambdaQueryWrapper<UserSong>().eq(UserSong::getUserId, id);
        List<UserSong> userSongs = userSongDao.selectList(eq);
        List<Long> list = userSongs.stream().map(userSong -> userSong.getSongId()).toList();
        if (list.size()==0){
            return null;
        }
        LambdaQueryWrapper<Song> in = new LambdaQueryWrapper<Song>().in( Song::getId, list);
        List<Song> songs = songDao.selectList(in);
        if(songs.size()!=0){
            return songs;
        }
        return null;
    }

    @Override
    public void banUser(Long id) {
        //查找user是否存在
        User user1 = userDao.selectById(id);
        if(user1==null){
            throw new TMusicException("用户不存在",410);
        }
        //0变1 1变0
        user1.setIsDelete(user1.getIsDelete()==0?1:0);
        LambdaUpdateWrapper<User> set = new LambdaUpdateWrapper<User>().eq(User::getId, id);
        userDao.update(user1,set);
    }

    private void checkUser(Long id){
        User user1 = userDao.selectById(id);
        if(user1==null){
            throw new TMusicException("用户不存在",410);
        }
    }
}
