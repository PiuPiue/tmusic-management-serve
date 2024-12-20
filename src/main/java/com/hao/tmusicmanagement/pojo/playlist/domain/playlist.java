package com.hao.tmusicmanagement.pojo.playlist.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("playlist")
public class playlist {

    @TableId
    private Long id;

    private String title;

    private String coverUrl;

    private String tagId;

    private Long artistId;

    private String description;

    private Integer type;
    @TableField(fill = FieldFill.INSERT) // 自动填充创建时间
    private Timestamp createTime;

    @TableField(value = "is_delete",fill = FieldFill.INSERT)
    private Integer isDelete;


}
