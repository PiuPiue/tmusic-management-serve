package com.hao.tmusicmanagement.pojo.playlist.dto;


import lombok.Data;

/**
 * 条件类可以根据类型进行查询
 * type
 *      1.根据歌单名进行查询（预备后续搜索功能时使用）
 *      2.根据标签查询（预备后续搜索功能时使用，前端可直接传送标签id）
 *      3.根据创建者进行查询（当点进这个人的个人主页，或自己点进自己的主页时，需要知道这个人创建的歌单）
 *      4.根据时间进行查询（暂时没用）
 */
@Data
public class PlayListDto {

    private String id;

    private String title;

    private Long tagId;

    private Long artistId;
    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 页码
     */
    private Integer pageNum;

}
