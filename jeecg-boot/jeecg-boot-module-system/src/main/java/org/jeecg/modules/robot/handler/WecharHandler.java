package org.jeecg.modules.robot.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.robot.entity.SendMsgAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Slf4j
@Component
public class WecharHandler {
    private URI API_URL = URI.create("http://127.0.0.1:8073/send");
    public static String KEY = "25C498727D264fbc85C8B4C393A6A92D";
    public static String UTF_8 = "UTF-8";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 发送信息给微信
     */
    public void sendToWechar(SendMsgAbstract sendInfo){
        if(null == sendInfo){
            log.info("！！！不发送给微信信息");
            return;
        }
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("data", JSON.toJSONString(sendInfo));

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(body, getHeader());
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(API_URL, httpEntity, JSONObject.class);
        log.info("！！！发送给微信信息：{}", responseEntity);
    }

    public static String encodeMsg(String msg) {
        if (null == msg) {
            return null;
        }
        String encodeMsg = null;
        try {
            encodeMsg = URLEncoder.encode(msg, UTF_8);
        } catch (Exception ex) {
            log.error("！！！加密消息出错:{}", ex);
            throw new RuntimeException(ex.getMessage());
        }
        return encodeMsg;
    }

    public static String decodeMsg(String msg) {
        if (null == msg) {
            return null;
        }
        String decodeMsg = null;
        try {
            decodeMsg = URLDecoder.decode(msg, UTF_8);
        } catch (Exception ex) {
            log.error("！！！解密消息出错:{}", ex);
            throw new RuntimeException(ex.getMessage());
        }
        return decodeMsg;
    }

    private MultiValueMap<String, String> getHeader(){
        MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
        header.add(HttpHeaders.CACHE_CONTROL,"no-cache");
        header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        header.add(HttpHeaders.ACCEPT, MediaType.ALL_VALUE);
        return header;
    }
}
