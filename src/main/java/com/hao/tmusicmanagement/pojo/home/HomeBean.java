package com.hao.tmusicmanagement.pojo.home;


import com.hao.tmusicmanagement.pojo.song.domain.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeBean {
    //歌曲总数
    private Integer songCount;
    //歌手总数
    private Integer singerCount;
    //歌单总数
    private Integer songListCount;
    //用户总数
    private Integer userCount;
    //排名前十歌曲
    private List<Song> topTenSongs;
    //后续补充分类占比
}
