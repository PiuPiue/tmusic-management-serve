package com.hao.tmusicmanagement.util.redis;


import lombok.Data;

/**
 * redis令牌管理
 */
@Data
public class RedisKeys {

    /**
     * token前缀
     */
    public final static String DEFINE_TOKEN = "Authorization:login:token:";

    /**
     * 歌单是否被收藏
     */
    public static String DEFINE_PLAYLIST_IS_COLLECTION;

    /**
     * 歌曲是否被收藏
     */
    public static String DEFINE_SONG_IS_COLLECTION;

    /**
     * 评论是否被点赞
     */
    public static String DEFINE_COMMENT_IS_LIKE;

    /**
     * 歌单收藏量
     */
    public static String DEFINE_PLAYLIST_COLLECTION_COUNT;

    /**
     * 歌曲点赞量
     */
    public static String DEFINE_SONG_LIKE_COUNT;

    /**
     * 歌曲信息
     */
    public static String DEFINE_SONG_INFO;

    public  RedisKeys(){}

    /**
     * 生成歌单是否被登录人收藏
     * @param userId
     * @param playListId
     * @return
     */
    public static RedisKeys generatePlayListIsCollectedKey(Long userId,Long playListId){
        RedisKeys redisKeys = new RedisKeys();
        redisKeys.DEFINE_PLAYLIST_IS_COLLECTION = "PlayListCollected:"+userId+":"+playListId;

        return redisKeys;
    }

    /**
     * 生成歌曲是否被登录人点赞
     * @param userId
     * @param songId
     * @return
     */
    public static RedisKeys generateSongIsCollectedKey(Long userId,Long songId){
        RedisKeys redisKeys = new RedisKeys();
        redisKeys.DEFINE_SONG_IS_COLLECTION = "SongCollected:"+userId+":"+songId;
        return redisKeys;
    }

    /**
     * 生成歌单收藏量
     * @param playListId
     * @return
     */
    public static RedisKeys playListCollectedCount(Long playListId){
        RedisKeys redisKeys = new RedisKeys();
        redisKeys.DEFINE_PLAYLIST_COLLECTION_COUNT = "PlayListCollectedCount:"+playListId;
        return redisKeys;
    }

    /**
     * 生成歌曲点赞量
     * @param songId
     * @return
     */
    public static RedisKeys songLikeCount(Long songId){
        RedisKeys redisKeys = new RedisKeys();
        redisKeys.DEFINE_SONG_LIKE_COUNT = "SongLikeCount:"+songId;
        return redisKeys;
    }

    /**
     * 生成歌曲信息
     */
    public static RedisKeys songInfo(Long songId){
        RedisKeys redisKeys = new RedisKeys();
        redisKeys.DEFINE_SONG_INFO = "SongInfo:"+songId;
        return redisKeys;
    }





}