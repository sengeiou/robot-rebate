package org.jeecg.modules.robot.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.robot.handler.WecharHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Getter
@Setter
@ToString
public class WxReceive implements Serializable {

    private String type;	// 如：100
    private String msg_type;	// 如：1
    private String from_wxid;	// 如：linxw1219
    private String from_name;	// 如：Viel微
    private String final_from_wxid;	// 如：linxw1219
    private String final_from_name;	// 如：Viel微
    private String robot_wxid;	// 如：csp961096506
    private String file_url;	// 如：null
    private String msg;	// 如：3  private String time;	// 如：159629201

    public static WxReceive instance(HttpServletRequest request) {
        Map parameterMap = request.getParameterMap();
        JSONObject json = new JSONObject();
        Iterator paIter = parameterMap.keySet().iterator();
        while (paIter.hasNext()) {
            String key = paIter.next().toString();
            String[] values = (String[]) parameterMap.get(key);
            json.put(key, values[0]);
        }
        log.info("！！！传入微信消息:{}", json.toJSONString());
        WxReceive msg = JSONObject.toJavaObject(json, WxReceive.class);
        if (null != msg && null != msg.getMsg()) {
            msg.setMsg(WecharHandler.decodeMsg(msg.getMsg()));
        }
        log.info("！！！微信消息转为对象:{}", msg);
        return msg;
    }
}
