package com.hao.tmusicmanagement.pojo.artist.vo;

import com.music.common.pojo.song.vo.SongVo;
import lombok.Data;

import java.util.List;

@Data
public class ArtistVo {
    private Long id;
    private String name;
    private String avatar;
    private String description;
    private Integer fans;
    private Integer songCount;
    //可直接根据作者获取歌单，但歌单中同样具有信息需要获取
    public List<SongVo> songList;

   public boolean isLikeArtist;
}
