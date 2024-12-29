package com.hao.tmusicmanagement.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtil {

    /**
     * 获取 IP 的详细地址信息。
     *
     * @param ip 要查询的 IP 地址。
     * @return IP 的详细地址信息。
     * @throws Exception 如果发生网络或解析错误。
     */
    public static String getIPDetails(String ip) throws Exception {
        String apiUrl = "https://whois.pconline.com.cn/ipJson.jsp?json=true&ip=" + ip;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();

        try {
            // 创建 HTTP 请求
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);


            // 读取返回数据
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            String country = parseAddressFromResponse(result.toString());
            // 解析返回的内容
            return country;
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * 从返回的 JSON 数据中解析详细地址。
     *
     * @param response API 返回的原始字符串。
     * @return 详细地址信息。
     */
    private static String parseAddressFromResponse(String response) {
        // 使用正则表达式提取 addr 字段的值
        String regex = "\"addr\":\"(.*?)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(response);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "无法解析详细地址信息";
    }


}
