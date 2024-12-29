package com.hao.tmusicmanagement.pojo.song.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 歌词类
 */
@Data
@Document(collection = "lytest")
public class Lyrics {
    @Id
    private String id;
    private String songId;
    private String time;
    private String text;
}
