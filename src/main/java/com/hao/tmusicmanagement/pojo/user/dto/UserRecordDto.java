package com.hao.tmusicmanagement.pojo.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRecordDto implements Serializable {
    private static final long serialVersionUID = 1L;
    //新增类型 1，收藏（取消）歌单 2.点赞（取消）歌曲 3.关注（取消）歌手
    private Integer type;
    private String id;
    private Long user;
}
