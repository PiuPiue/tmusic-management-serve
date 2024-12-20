package com.hao.tmusicmanagement.pojo.playlist.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_playlist")
public class UserPlaylist {
    @TableId
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("playlist_id")
    private Long playlistId;
    @TableField(value = "is_delete",fill = FieldFill.INSERT)
    private Integer isDelete;
}
