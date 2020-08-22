package org.jeecg.modules.robot.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.robot.entity.SendMsgAbstract;
import org.jeecg.modules.robot.entity.WxFriend;
import org.jeecg.modules.robot.entity.WxResult;
import org.jeecg.modules.robot.entity.WxRobot;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
            log.info("不发送给微信信息");
            return;
        }
        log.info("发送给微信信息:{}", sendInfo);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("data", JSON.toJSONString(sendInfo));

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(body, getHeader());
        ResponseEntity<WxResult> responseEntity = restTemplate.postForEntity(API_URL, httpEntity, WxResult.class);

        WxResult.throwInvalid(responseEntity);
        log.info("发送给微信信息成功：{}", responseEntity.getBody());
    }


    /**
     * 获取机器人账号列表
     */
    public List<WxRobot> getRobotList(){
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        Map<String,String> obj = new HashMap<String,String>();
        obj.put("type", "203");
        obj.put("key", WecharHandler.KEY);
        body.add("data", JSON.toJSONString(obj));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(body, getHeader());

        ResponseEntity<WxResult> responseEntity = restTemplate.postForEntity(API_URL, httpEntity, WxResult.class);
        WxResult.throwInvalid(responseEntity);

        WxResult result = responseEntity.getBody();
        List<WxRobot> robotList = result.getObjList(WxRobot.class);
        log.info("获取机器人账号列表结果：{}", robotList);
        return robotList;
    }


    /**
     * 获取好友列表
     */
    public List<WxFriend> getFriendList(String robotWxid){
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        Map<String,String> obj = new HashMap<String,String>();
        obj.put("type", "204");

        // 账户id（可选，如果填空字符串，即取所有登录账号的好友列表，反正取指定账号的列表）
        obj.put("robot_wxid", (robotWxid != null ? robotWxid : ""));
        // 是否刷新列表，0 从缓存获取 / 1 刷新并获取
        obj.put("is_refresh", "1");

        obj.put("key", WecharHandler.KEY);
        body.add("data", JSON.toJSONString(obj));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(body, getHeader());

        ResponseEntity<WxResult> responseEntity = restTemplate.postForEntity(API_URL, httpEntity, WxResult.class);
        WxResult.throwInvalid(responseEntity);

        WxResult msg = responseEntity.getBody();
        List<WxFriend> friendList = msg.getObjList(WxFriend.class);
        log.info("获取获取好友列表结果：{}", msg);
        return friendList;
    }


    /**
     * 同意好友请求
     */
    public void agreeFriendVerify(String robotWxid, String msg) {
        Objects.requireNonNull(robotWxid, "!!!微信机器人ID不能为空");
        Objects.requireNonNull(msg, "!!!微信用户ID不能为空");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        Map<String,String> obj = new HashMap<String,String>();
        obj.put("type", "303");
        obj.put("robot_wxid", robotWxid);
        obj.put("msg", msg);

        obj.put("key", WecharHandler.KEY);
        body.add("data", JSON.toJSONString(obj));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(body, getHeader());

        ResponseEntity<WxResult> responseEntity = restTemplate.postForEntity(API_URL, httpEntity, WxResult.class);
        log.info("同意好友请求,返回结果：{}",responseEntity.getBody());
        WxResult.throwInvalid(responseEntity);
    }


    /**
     * 修改好友备注
     */
    public void modifyFriendNote(String robotWxid, String friendWxid, String note) {
        Objects.requireNonNull(robotWxid);
        Objects.requireNonNull(friendWxid);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        Map<String,String> obj = new HashMap<String,String>();
        obj.put("type", "304");
        obj.put("robot_wxid", robotWxid);
        obj.put("friend_wxid", friendWxid);
        obj.put("note", (note != null ? note : "")); // 新备注（空字符串则是删除备注）

        obj.put("key", WecharHandler.KEY);
        body.add("data", JSON.toJSONString(obj));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(body, getHeader());

        ResponseEntity<WxResult> responseEntity = restTemplate.postForEntity(API_URL, httpEntity, WxResult.class);
        log.info("修改好友备注,返回结果：{}",responseEntity.getBody());
        WxResult.throwInvalid(responseEntity);
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
