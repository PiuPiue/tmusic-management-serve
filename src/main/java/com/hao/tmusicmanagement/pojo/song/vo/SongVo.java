package com.hao.tmusicmanagement.pojo.song.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SongVo {

    private Long id;
    private String title;
    private String cover;
    private String artist;
    private String url;
    //标签可以有多个
    private List<String> tags;
    //歌词
    private List<LyricsVo> lyricslist;
    private Integer duration;
    private Integer playCount;
    private Integer downloadCount;
    private LocalDateTime createTime;
    //喜欢数量
    private Integer likeCount;
    //当前登录人是否喜欢
    private Boolean isLike;

}
