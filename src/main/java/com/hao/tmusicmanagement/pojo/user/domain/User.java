package com.hao.tmusicmanagement.pojo.user.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@TableName("user") // 数据库表名
public class User {

    @TableId
    private Long id; // 用户id

    private String account; // 账号

    @TableField(select = false)
    private String password; // 密码

    private String nickname; // 昵称

    private String avatar; // 头像

    private String background; // 背景

    private String description; // 描述

    @TableField(fill = FieldFill.INSERT) // 自动填充创建时间
    private LocalDateTime createTime; // 创建时间
    @TableField(value = "is_delete",fill = FieldFill.INSERT)
    private Integer isDelete; // 是否删除（0-未删除，1-已删除）
}