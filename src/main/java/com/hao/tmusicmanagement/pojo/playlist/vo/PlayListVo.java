package com.hao.tmusicmanagement.pojo.playlist.vo;

import com.music.common.pojo.song.vo.SongVo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 歌单表
 */
@Data
public class PlayListVo {
    private Long id;
    private String title;
    private String coverUrl;
    //标签组
    private List<String> tags;
    //作者名称
    private String artist;
    private String description;
    private Timestamp createTime;
    //歌曲列表
    private List<SongVo> songList;
    //收藏数
    private Integer collectCount;
    //播放量
    private Integer playCount;
    //评论内容(后续开发，先实现主要功能)

    //登录人是否收藏
    private Boolean isCollect;


}
