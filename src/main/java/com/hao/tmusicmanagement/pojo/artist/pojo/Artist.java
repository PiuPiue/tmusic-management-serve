package com.hao.tmusicmanagement.pojo.artist.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("artist") // 数据库表名
public class Artist {

    @TableId
    private Long id; // 歌手id

    private String name; // 歌手名称

    private String avatar; // 歌手头像

    private String description; // 歌手描述

    @TableField(fill = FieldFill.INSERT) // 自动填充创建时间
    private LocalDateTime createTime; // 创建时间
    @TableField(value = "is_delete",fill = FieldFill.INSERT)
    private Integer isDelete; // 是否删除（0-未删除，1-已删除）
}