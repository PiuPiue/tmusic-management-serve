package com.hao.tmusicmanagement.pojo.song.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("song")
public class Song {

    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("name")
    private String title;
    @TableField("cover_url")
    private String cover;
    @TableField("author_id")
    private Long artist;
    @TableField("song_url")
    private String url;
    private String tagId;
    private Long lyrics;
    private Integer duration;
    private Integer playCount;
    private Integer downloadCount;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "is_delete",fill = FieldFill.INSERT)
    private Boolean isDelete;



}
