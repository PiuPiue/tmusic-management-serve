package com.hao.tmusicmanagement.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.tmusicmanagement.pojo.song.domain.Song;
import com.hao.tmusicmanagement.pojo.song.dto.SongDto;
import com.hao.tmusicmanagement.pojo.song.vo.SongVo;

public interface SongService extends IService<Song> {

    //新增，删除，修改歌曲
    public void addSong(SongDto song);
    public void deleteSong(Long id);
    public void updateSong(Song song);

    //分页查询歌曲
    public Page<SongVo> getSong(Integer pageNum, Integer pageSize, String name);

    void deleteSongs(Long[] ids);
}
