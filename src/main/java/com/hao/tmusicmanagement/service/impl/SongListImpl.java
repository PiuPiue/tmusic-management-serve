package com.hao.tmusicmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.tmusicmanagement.Exception.TMusicException;
import com.hao.tmusicmanagement.core.AjaxResult;
import com.hao.tmusicmanagement.dao.*;
import com.hao.tmusicmanagement.pojo.artist.pojo.Artist;
import com.hao.tmusicmanagement.pojo.playlist.domain.UserPlaylist;
import com.hao.tmusicmanagement.pojo.playlist.domain.playlist;
import com.hao.tmusicmanagement.pojo.song.domain.PlayListSong;
import com.hao.tmusicmanagement.pojo.song.domain.Song;
import com.hao.tmusicmanagement.pojo.song.vo.SongVo;
import com.hao.tmusicmanagement.service.SongListService;
import com.hao.tmusicmanagement.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SongListImpl extends ServiceImpl<SongListDao, playlist> implements SongListService {

    @Autowired
    private PlayListSongDao playListSongDao;
    @Autowired
    private SongDao songDao;

    @Autowired
    private SingerDao singerDao;

    @Autowired
    private UserPlaylistDao userPlaylistDao;


    /**
     * 歌单的分页查询
     * 1.根据名称模糊搜索
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<playlist> getSongListByPage(Integer page, Integer pageSize,String name) {
        Page<playlist> playlistPage = new Page<>(page, pageSize);
        if(StringUtils.hasText(name)){
            LambdaQueryWrapper<playlist> eq = new LambdaQueryWrapper<playlist>().eq(playlist::getTitle, name);
            playlistPage = this.baseMapper.selectPage(playlistPage, eq);
        }else{
            playlistPage = this.baseMapper.selectPage(playlistPage, null);
        }
        return playlistPage.getRecords();
    }

    @Override
    public void addSongList(playlist playlist) {
        //首先查询当前登录人是否具有权限,如果没有相关权限则抛出异常

        //随后进行新增
        this.baseMapper.insert(playlist);
    }

    @Override
    public AjaxResult deleteSongList(Integer id) {
        //首先查询是否有
        playlist playlist = this.baseMapper.selectById(id);
        if(playlist==null){
            throw new TMusicException("歌单不存在",410);
        }
        //首先需要删除歌单中的所有歌曲记录
        LambdaQueryWrapper<PlayListSong> eq = new LambdaQueryWrapper<PlayListSong>().eq(PlayListSong::getPlaylistId, id);
        playListSongDao.delete(eq);
        //还需要删除所有关于该歌单的数仓记录
        LambdaQueryWrapper<UserPlaylist> eq1 = new LambdaQueryWrapper<UserPlaylist>().eq(UserPlaylist::getPlaylistId, id);
        userPlaylistDao.delete(eq1);
        //再删除歌单
        this.baseMapper.deleteById(id);
        return new AjaxResult("删除成功","200");
    }

    @Override
    public AjaxResult updateSongList(playlist playlist) {
        UpdateWrapper<playlist> updateWrapper = new UpdateWrapper<>();
        // 只更新不为空的字段
        if (playlist.getTitle() != null&&!"".equals(playlist.getTitle())) {
            updateWrapper.set("name", playlist.getTitle());
        }
        if (playlist.getDescription() != null&&!"".equals(playlist.getDescription())) {
            updateWrapper.set("description", playlist.getDescription());
        }
        if (playlist.getCoverUrl() != null&&!"".equals(playlist.getCoverUrl())) {
            updateWrapper.set("cover_url", playlist.getCoverUrl());
        }
        // 指定要更新的记录
        updateWrapper.eq("id", playlist.getId());
        // 执行更新操作
        this.baseMapper.update(null, updateWrapper);
        return new AjaxResult("修改成功", "200");
    }

    @Override
    public AjaxResult deleteSong(List<Integer> songId, Integer songListId) {
        //首先查询songId是否为空
        if(songId==null||songId.size()==0){
            throw new TMusicException("歌曲id为空",410);
        }
        //随后查询歌曲是否存在于歌单之中
        if(playListSongDao.selectList(new LambdaQueryWrapper<PlayListSong>().eq(PlayListSong::getPlaylistId, songListId).in(PlayListSong::getSongId, songId))==null){
            throw new TMusicException("歌曲不存在于歌单之中",410);
        }

        //随后可以进行歌曲删除
        playListSongDao.delete(new LambdaQueryWrapper<PlayListSong>().eq(PlayListSong::getPlaylistId, songListId).in(PlayListSong::getSongId, songId));
        return new AjaxResult("删除成功", "200");
    }

    @Override
    public AjaxResult addSong(Integer songId, Integer songListId) {
        //查询歌曲是否已经存在
        if(playListSongDao.selectOne(new LambdaQueryWrapper<PlayListSong>().eq(PlayListSong::getPlaylistId, songListId).eq(PlayListSong::getSongId, songId))!=null){
            throw new TMusicException("歌曲已经存在于歌单之中",410);
        }
        //在此处，需要前端传入的是歌曲id
        PlayListSong playListSong = new PlayListSong();
        playListSong.setPlaylistId(Long.valueOf(songListId));
        playListSong.setSongId(Long.valueOf(songId));
        playListSongDao.insert(playListSong);
        return new AjaxResult("添加成功", "200");
    }

    @Override
    public List<SongVo> getSongByPlayList(Integer id) {
        //首先根据id查询出所有歌曲，随后查询歌曲详细信息
        LambdaQueryWrapper<PlayListSong> playListSongLambdaQueryWrapper = new LambdaQueryWrapper<PlayListSong>().eq(PlayListSong::getPlaylistId, id);
        List<PlayListSong> playListSongs = playListSongDao.selectList(playListSongLambdaQueryWrapper);
        //随后根据获取到的歌曲id获取相关的全部歌曲,需要实现相关信息的转换，如歌曲的作者信息
        List<Song> songs = songDao.selectBatchIds(playListSongs.stream().map(PlayListSong::getSongId).toList());
        List<SongVo> songVos = BeanUtils.copyToList(songs, SongVo.class);
        for(Song song: songs){
            String name = singerDao.selectOne(new LambdaQueryWrapper<Artist>().eq(Artist::getId, song.getArtist())).getName();
            SongVo songVo = BeanUtils.copyBean(song, SongVo.class);
            songVo.setArtist(name);
            songVos.add(songVo);
        }
        return songVos;
    }
}
