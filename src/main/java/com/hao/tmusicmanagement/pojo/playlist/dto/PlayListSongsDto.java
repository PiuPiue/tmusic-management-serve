package com.hao.tmusicmanagement.pojo.playlist.dto;

import com.hao.tmusicmanagement.pojo.song.domain.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayListSongsDto {
    Integer playListId;
    List<Integer> songsId;
}
