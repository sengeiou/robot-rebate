package org.jeecg.modules.robot.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 淘宝客结果统一对象
 */
@Slf4j
@Data
public class TbResult {
    private int resultCode;   // 返回码
    private boolean success;
    private JSONObject data;   // 微信返回信息

    /**
     * 检查是否有效，失效则抛出异常
     */
    public static TbResult throwInvalid(ResponseEntity<String> responseEntity){
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            throw new RuntimeException("！！！调用淘宝客API异常!");
        }
        log.info("返回结果:{}", responseEntity.getBody());
        TbResult msg = JSON.parseObject(responseEntity.getBody(), TbResult.class);
        if (200 != msg.getResultCode()) {
            throw new RuntimeException("！！！调用淘宝客API失败");
        }
        return msg;
    }

}
