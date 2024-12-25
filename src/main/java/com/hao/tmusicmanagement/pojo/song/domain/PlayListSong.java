package com.hao.tmusicmanagement.pojo.song.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "playlist_song")
public class PlayListSong {

    @TableId(type = IdType.AUTO)
    private Long id; // 主键id

    private Long playlistId; // 歌单id

    private Long songId; // 歌曲id

    @TableField(value = "is_delete",fill = FieldFill.INSERT)
    private Integer isDelete; // 是否删除
}
