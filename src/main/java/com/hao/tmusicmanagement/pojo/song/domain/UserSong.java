package com.hao.tmusicmanagement.pojo.song.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_like_song")
public class UserSong {

    @TableId
    private Long id;
    private Long userId;
    private Long songId;
    @TableField(fill = FieldFill.INSERT) // 自动填充创建时间
    private LocalDateTime createTime; // 创建时间
    @TableField(value = "is_delete",fill = FieldFill.INSERT)
    private Integer isDelete; // 是否删除（0-未删除，1-已删除）

}
