package org.jeecg.modules.robot;

import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;
import java.net.URLEncoder;
@Slf4j
public class URLUtils {
    public static String UTF_8 = "UTF-8";

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
}
