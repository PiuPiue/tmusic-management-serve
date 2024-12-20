package com.hao.tmusicmanagement.pojo.song.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "playlist_song")
public class PlayListSong {

    @TableId
    private Long id; // 主键id

    private Long playlistId; // 歌单id

    private Long songId; // 歌曲id

    @TableField(value = "is_delete",fill = FieldFill.INSERT)
    private Integer isDelete; // 是否删除
}
