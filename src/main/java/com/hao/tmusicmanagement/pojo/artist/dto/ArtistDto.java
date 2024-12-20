package com.hao.tmusicmanagement.pojo.artist.dto;

import lombok.Data;

@Data
public class ArtistDto {
    private String name;
    private Integer pageNum;
    private Integer pageSize;
}
