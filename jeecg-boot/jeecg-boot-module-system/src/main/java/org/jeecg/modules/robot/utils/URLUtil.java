package org.jeecg.modules.robot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URLDecoder;
import java.net.URLEncoder;
@Slf4j
public class URLUtil {
    public static final String UTF_8 = "UTF-8";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36";

    public static String encode(String msg) {
        if (null == msg) {
            return null;
        }
        String encode = null;
        try {
            encode = URLEncoder.encode(msg, UTF_8);
        } catch (Exception ex) {
            log.error("！！！加密消息出错:{}", ex);
            throw new RuntimeException(ex.getMessage());
        }
        return encode;
    }


    public static String decode(String msg) {
        if (null == msg) {
            return null;
        }
        String decode = null;
        try {
            decode = URLDecoder.decode(msg, UTF_8);
        } catch (Exception ex) {
            log.error("！！！解密消息出错:{}", ex);
            throw new RuntimeException(ex.getMessage());
        }
        return decode;
    }

    public static MultiValueMap<String, String> getHeader(){
        MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
        //添加请求头
        header.add(HttpHeaders.PRAGMA,"no-cache");
        header.add(HttpHeaders.CACHE_CONTROL,"no-cache");
        //设置浏览器类型
        header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        header.add(HttpHeaders.ACCEPT, MediaType.ALL_VALUE);
        header.add(HttpHeaders.USER_AGENT,USER_AGENT);
        header.add(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,en,*");
        header.add("x-requested-with", "XMLHttpRequest");
        header.add("authority", "pub.alimama.com");
        return header;
    }
}
