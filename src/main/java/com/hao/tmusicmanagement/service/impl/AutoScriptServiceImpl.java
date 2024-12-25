package com.hao.tmusicmanagement.service.impl;



import cn.hutool.json.JSONObject;
import com.hao.tmusicmanagement.dao.PlayListSongDao;
import com.hao.tmusicmanagement.dao.SingerDao;
import com.hao.tmusicmanagement.dao.SongDao;
import com.hao.tmusicmanagement.dao.SongListDao;
import com.hao.tmusicmanagement.pojo.artist.pojo.Artist;
import com.hao.tmusicmanagement.pojo.playlist.domain.playlist;
import com.hao.tmusicmanagement.pojo.song.domain.PlayListSong;
import com.hao.tmusicmanagement.pojo.song.domain.Song;
import com.hao.tmusicmanagement.service.*;
import com.jayway.jsonpath.JsonPath;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.jsoup.nodes.Element;

@Service
public class AutoScriptServiceImpl implements AutoScriptService {

    @Autowired
    private SongDao songDao;
    @Autowired
    private SingerDao singerDao;
    @Autowired
    private SongListDao songListDao;
    @Autowired
    private PlayListSongDao playListSongDao;


    private static String cookies = "NMTID=00OFF0b6ynE49-Fh08KvBw2a14jYUgAAAGPTkcG-w; _iuqxldmzr_=32; _ntes_nnid=cb2c3ee147f89d3210bb3b729187e629,1715005230222; _ntes_nuid=cb2c3ee147f89d3210bb3b729187e629; WEVNSM=1.0.0; WNMCID=pghghj.1715005231631.01.0; WM_TID=4MFYcDupj4tEARUUAAfF7mW4cT4GAHlE; sDeviceId=YD-rT89kjiCBDNFVkEQAVLRvnG4NTsHDjPa; ntes_utid=tid._.GLbCkidLoRdBFwEQQRKR%252F3DoNC4GCnPC._.0; __snaker__id=kTqsnjBwsWYt7NqY; __remember_me=true; __csrf=50e9f6982e77c1cf088a4442c0059824; WM_NI=T9%2BWPSAZKEytZXcDO4ZxXdLmjzrs2UhLdkKqc32VJq4rwKgCxE4q7O1%2F1GhIHi7T0A2h5U3ru3%2F2vc5uUpoeRjLi7PyxkS0mytia5jitezJsFNwtgdOGWHF7h2ivdemdY00%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6eeb1f04197aebbb0dc6fa99a8aa2c45f838a8bb0d744a2aba5acf65b8eb2ae9bf72af0fea7c3b92a98e7e5b8d359838685a6fc809a88ada5ce409aa89885cf67aaa6be94b15fac94ac8cca7ba1edfeb7c97bf8aeb9ace649bc9bfc92b645f3f1ffa8eb438cb7f9b5b34bf2b8fbb6b27ab7ede1d3f569f7ab9b91ee6f92b6848ab65cbba8a5b4b367bbaca782b862abacab9bc9798692b995e6708d9999a9c53daceea797e76fafbc97d1b337e2a3; NTES_YD_SESS=GYQkhhYueqYQ.pmH.Idx.eA7OMJfWztzFtVxqbnlLL6d5EnP5.MfeYku.Wc6Ul0tJja.3H8AJgwgt2dFFYylfYfmcDAu.VCOpntcjsHF5HLsFSG3dsRRU4hF8YmpmeeMOGMPSpW8LeSwkPqWsIFU50fE3MM5GgNOGSBdhOTADgZtOztgj3ImWyPoJ4R6Z5djZyknGqOf9JGKROmhgglMVItfMdnz8YPW7JJCMuOVcsksO; S_INFO=1730812983|0|0&60##|13354210131; P_INFO=13354210131|1730812983|1|music|00&99|null&null&null#CN&null#10#0|&0|null|13354210131; MUSIC_U=00DEB1AEBF8907C74A207B2ADAA819EC908F01F03BF1366F9552785CB9132E8A8349E0676FFAED3B7CACBCAAEECA7CABC4BC576AE8E87D43E809361112C56580A13C9B88A138AEAE060E4B5BAA9DBABB3454FA71D5FFD55AB7C572DA8FD0EC86EC867B2346E6C50748BD7C2654DDC420DEE9FD71FDDF0FDD6B77D865973F685ADC9C3E3CA6B81663598A5D55C0E9C85BE265A4253329C318163E534D46A5FE322C7A0668864BF18B0F3F05C89E513FD8247760B0D04CC2B83C03A777F5540D224E78947D93D2FD7A4F89797F164F5AD9F6C5CAF5E7AB945C77BE9041384404300B9022530678DDF3E8BCDF06BE5E77FD5ED66AEC69814A34056FF6F110D26E1508C325F3C3BF34942ED19FF8730B7E714258F5F22E9C62C268E0F90FCDBF7ACC09A67F2409F0D601BBB47C139536036C86295698A4B4A5C501C39136680B906FF446A1DE26C6F36B66BE6A4B02467748C50C50E98DDCB10CB4229E6F835893A55B; __csrf=31abc2abdd8f2a754340131d4bed6694; ntes_kaola_ad=1; gdxidpyhxdE=h%2FjuJEH8EW3E4vz2ip50owKVHYu6itYt8vgkJQdQyNL3SeYkWK4IRYmAOpUwcnnLl7SqT4oMy0CHqBB8erOagfGW%2BGyWCPIjsmYbIINhApHfpKj2dksY%2FLWvoqRzMHhT7%2FGh04IVhO30cOHZXpH%2B8pQ9Pd9gXjHp%5CLncfaNod7fa2QlD%3A1730813891691; playerid=24627868; csrfToken=95GtC-xSPDGBYRynvpAUv6Co; JSESSIONID-WYYY=m%2FoW63rsBs1e%2BgjsjUtt%2FsP2ptrrO%2FKjudOUO78k%2FNXTz5euUsNVgF7J%5Cwj22HPj%5CQIdaKXmyGZn17WcdmAzWrzxV%2BJ6pTUkG8Wnw8fJQ7boGFutSl%2BWJkps5rURyf%5C5%5CmJZCzfaOktaPI5Wx38Si%2BbHWyb4VPvzb2D51gse7HTbIujN%3A1730817341239; os=pc";
    private static String listBaseUrl = "https://netease-cloud-music-api.fe-mm.com/song/detail";
    private static String songUrl = "https://music.163.com/song/media/outer/url?id=";

    //获取指定页数的所有id
    private List<String> getListId(String type,int page) throws IOException, InterruptedException {
        String URL = "https://music.163.com/discover/playlist/";
        int offset = (page - 1) * 35;
        // 构造查询参数
        String params = String.format("?order=hot&cat=%s&limit=35&offset=%d",type,offset);

        // 使用 HttpClient 发送 GET 请求
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + params))
                .header("Cookie", cookies) // 替换为实际 Cookie
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 解析 HTML
        Document doc = Jsoup.parse(response.body());
        Elements aTags = doc.select("a.tit.f-thide.s-fc0"); // 根据 CSS 类选择元素

        // 提取 href 中的 ID 值
        List<String> listIds = new ArrayList<>();
        for (Element aTag : aTags) {
            String href = aTag.attr("href");
            String id = href.substring(href.lastIndexOf('=') + 1); // 提取 '=' 后的部分
            listIds.add(id);
        }

        return listIds;
    }

    //用于获取指定的歌单的信息和包含的歌曲id
    private static List<Object> getSongList(String id) {
        String url = "https://music.163.com/playlist";

        // 参数和头部设置
        Map<String, String> params = new HashMap<>();
        params.put("id", id);

        // Cookie 和 Headers 示例，需根据实际情况更新
        // 创建一个 Map 来存储 cookies
        Map<String, String> cookie = new HashMap<>();

        // 分割 cookie 字符串
        String[] cookieArray = cookies.split(";");

        // 遍历 cookie 数组并将每个键值对放入 Map 中
        for (String cookie1 : cookieArray) {
            String[] keyValue = cookie1.split("=");
            if (keyValue.length == 2) {
                cookie.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }


        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

        List<Object> resList = new ArrayList<>();

        try {
            // 构造请求 URL
            Document doc = Jsoup.connect(url)
                    .data(params)
                    .cookies(cookie)
                    .headers(headers)
                    .get();

            // 获取歌单的元数据
            String title = doc.select("meta[property=og:title]").attr("content");
            String description = doc.select("meta[property=og:description]").attr("content");
            String image = doc.select("meta[property=og:image]").attr("content");
            String createTime = doc.select("span.time.s-fc4").text().split("\u00a0")[0]; // 获取创建时间

            // 将日期格式化
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            createTime = outputFormat.format(inputFormat.parse(createTime));

            // 歌单信息
            playlist playlist = new playlist();
            playlist.setId(Long.valueOf(id));
            playlist.setTitle(title);
            playlist.setCoverUrl(image);
            playlist.setDescription(description);
            playlist.setCreateTime(Timestamp.valueOf(createTime));
            playlist.setTagId("1");
            playlist.setArtistId(1L);
            playlist.setType(2);
            playlist.setIsDelete(0);

//            Map<String, Object> songListInfo = new HashMap<>();
//            songListInfo.put("id", id);
//            songListInfo.put("title", title);
//            songListInfo.put("cover_url", image);
//            songListInfo.put("description", description);
//            songListInfo.put("create_time", createTime);
//            songListInfo.put("tag_id", "1"); // 默认值
//            songListInfo.put("artist_id", 1); // 默认值
//            songListInfo.put("type", 2); // 默认值
//            songListInfo.put("is_delete", 0); // 默认值

            resList.add(playlist);

            // 获取歌单中的所有歌曲 ID
            Elements songElements = doc.select("ul.f-hide a");
            List<String> songIds = new ArrayList<>();
            for (Element song : songElements) {
                String href = song.attr("href");
                String songId = href.split("=")[1];
                songIds.add(songId);
            }

            resList.add(songIds);
        } catch (Exception e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }

        return resList;
    }

    //实现歌手信息和歌曲信息的爬取
    public static List getSongInfo(String songId) throws Exception {

        songId = songId.split("\\[")[songId.split("\\[").length - 1].split("\\]")[0];

        // 请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.6261.95 Safari/537.36");

        // 发送请求并获取响应
        String response = sendGetRequest(listBaseUrl,songId, headers);

        // 解析返回的 JSON
        JSONObject res = new JSONObject(response);

        // 使用 JsonPath 提取数据
        List<Object> ids = JsonPath.read(res.toString(), "$.privileges[*].id");
        List<String> names = JsonPath.read(res.toString(), "$.songs[*].name");
        List<String> coverUrls = JsonPath.read(res.toString(), "$.songs[*].al.picUrl");
        List<Object> artists = JsonPath.read(res.toString(), "$.songs[*].ar[0].id");
        List<String> artistNames = JsonPath.read(res.toString(), "$.songs[*].ar[0].name");
        List<Object> durations = JsonPath.read(res.toString(), "$.songs[*].dt");
        List<Object> createTimes = JsonPath.read(res.toString(), "$.songs[*].publishTime");

        // 组合数据
        List<Song> songs = new ArrayList<>();
        List<Artist> art = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < names.size(); i++) {
            try {
                // 取时长的前三位（确保类型为 Long）
                long durationLong = (durations.get(i) instanceof Integer)
                        ? ((Integer) durations.get(i)).longValue()
                        : (Long) durations.get(i);
                int dur = Integer.parseInt(String.valueOf(durationLong).substring(0, 3));

                // 创建 Song 对象
                Song song = new Song();
                long id = (ids.get(i) instanceof Integer)
                        ? ((Integer) ids.get(i)).longValue()
                        : (Long) ids.get(i);
                song.setId(id);
                song.setTitle(names.get(i));
                song.setCover(coverUrls.get(i));
                long artistId = (artists.get(i) instanceof Integer)
                        ? ((Integer) artists.get(i)).longValue()
                        : (Long) artists.get(i);
                song.setArtist(artistId);
                song.setUrl(songUrl + id + ".mp3");
                song.setTagId("1");
                song.setDuration(dur);
                song.setPlayCount(100);
                song.setDownloadCount(100);
                song.setCreateTime(LocalDateTime.now());
                song.setIsDelete(false);
                song.setLyrics(2L);
                songs.add(song);

                // 创建 Artist 对象
                Artist artist1 = new Artist();
                artist1.setId(artistId);
                artist1.setName(artistNames.get(i));
                artist1.setAvatar("https://c-ssl.duitang.com/uploads/item/202005/07/20200507184355_By5mf.jpeg");
                artist1.setDescription("还在获取中哦~~~");
                artist1.setCreateTime(LocalDateTime.now());
                artist1.setIsDelete(1); // 这里 1 表示已删除，是否需要改成 false？
                art.add(artist1);
            } catch (Exception e) {
                // 捕获异常并打印错误日志，避免程序崩溃
                System.err.println("Error processing index " + i + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        // 返回组合结果
        List resList = new ArrayList<>();
        resList.add(songs);
        resList.add(art);
        System.out.println("歌曲和歌手信息爬取完成");

        return resList;
    }

    // 发送 GET 请求并返回响应
    private static String sendGetRequest(String url, String params, Map<String, String> headers) throws Exception {
        StringBuilder urlWithParams = new StringBuilder(url);
        urlWithParams.append(params);
        URL requestUrl = new URL(urlWithParams.toString());
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        connection.setRequestMethod("GET");

        // 设置请求头
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        // 获取响应
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    /**
     * 此脚本用于根据页数实现爬取，爬取指定分类页的歌单，歌单中包含的歌曲以及歌曲对应的歌手
     */

    private void getSongListUtil(String type,Integer page) throws Exception {
        //首先定义
        List<playlist> playlists = new ArrayList<>();
        //1.根据页数获取这一页所有歌单的id
        List<String> listId = this.getListId(type,page);
        //2.定义歌曲，实现批量插入
        List<Song> songsInfo = new ArrayList<>();
        //3.定义歌曲对应表
        List<PlayListSong> playListSongsInfo = new ArrayList<>();
        //4.定义歌手，实现批量插入
        List<Artist> artistsInfo = new ArrayList<>();
        for(String id:listId){
            //2.获取这一页的歌单信息和里面所有歌曲的id
            List<Object> songList = getSongList(id);
            // 2.1将这一个歌单信息存起来
            playlists.add((playlist) songList.get(0));
            // 2.2获取当前歌单的所有歌曲id,并进行拼接参数
            List<String> list  = (List) songList.get(1);
            String param = "?ids=";
            for(String i:list){
                param += i+',';
            }
            param = param.substring(0,param.length()-1);
            //3.实现所有歌曲的获取
            List songInfo = getSongInfo(param);
            //3.1 将歌曲信息存起来
            songsInfo.addAll((List<Song>) songInfo.get(0));
            List<Song> songs = (List<Song>) songInfo.get(0);
            //3.2 将歌手信息存起来
            artistsInfo.addAll((List<Artist>) songInfo.get(1));
            //List<Artist> artists = (List<Artist>) songInfo.get(1);
            List<PlayListSong> collect = songs.stream().map(song -> {
                PlayListSong playListSong = new PlayListSong();
                playListSong.setPlaylistId(Long.valueOf(id));
                playListSong.setSongId(song.getId());
                playListSong.setIsDelete(0);
                return playListSong;
            }).collect(Collectors.toList());
            playListSongsInfo.addAll(collect);
            System.out.println("相关信息获取完成");
        }
        //1.插入歌单歌曲对应信息，歌曲信息
        this.songDao.saveOrUpDateBatch(songsInfo);
        System.out.println("歌曲插入成功");
        //2.插入对应记录
        this.playListSongDao.saveOrUpDateBatch(playListSongsInfo);
        System.out.println("歌单歌曲表插入成功");
        //3.插入歌手信息
        this.singerDao.saveOrUpDateBatch(artistsInfo);
        System.out.println("歌手插入成功");
        this.songListDao.saveOrUpDateBatch(playlists);

    }

    @Override
    public void getSongList(String type,Integer startPage,Integer endPage) throws Exception {
        for(int i = startPage;i<=endPage;i++){
            this.getSongListUtil(type,i);
        }
    }

}
