package org.jeecg.modules.robot.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.robot.URLUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信结果统一对象
 */
@Data
public class WxResult {
    private int code;   // 返回码
    private String data;   // 微信返回信息

    public <T> T getObj(Class<T> clazz){
        if (clazz == null) {
            throw new IllegalArgumentException("！！！WechatResult.getObj 对应的类不能为空");
        }
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        String content = URLUtils.decode(data.trim());
        return JSON.parseObject(content, clazz);
    }

    public <T> List<T> getObjList(Class<T> clazz){
        if (clazz == null) {
            throw new IllegalArgumentException("！！！WechatResult.getObjList 对应的类不能为空");
        }
        if (StringUtils.isEmpty(data)) {
            return new ArrayList<T>(); // 注意，返回空list
        }
        String content = URLUtils.decode(data.trim());
        return JSON.parseArray(content, clazz);
    }

    /**
     * 检查是否有效，失效则抛出异常
     */
    public static void throwInvalid(ResponseEntity<WxResult> responseEntity){
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            throw new RuntimeException("！！！调用微信API异常!");
        }
        WxResult msg = responseEntity.getBody();
        if (0 != msg.getCode()) {
            throw new RuntimeException("！！！调用微信API失败!");
        }
    }

    /**
     * 检查是否有效，失效则抛出异常
     */
    /*public static boolean checkInvalid(ResponseEntity<WxResult> responseEntity){
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return false;
        }
        WxResult msg = responseEntity.getBody();
        if (0 != msg.getCode()) {
            return false;
        }
        return true;
    }*/
}
