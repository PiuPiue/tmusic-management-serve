package com.hao.tmusicmanagement.pojo.song.dto;

import com.hao.tmusicmanagement.pojo.song.domain.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongDto extends Song {
    private String lyrics1;
}
