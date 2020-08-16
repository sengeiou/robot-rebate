package org.jeecg.modules.robot.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.robot.entity.SendMsgAbstract;
import org.jeecg.modules.robot.entity.SendTextMsg;
import org.jeecg.modules.robot.entity.WechatReceive;
import org.jeecg.modules.robot.handler.WecharHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/robot")
public class RobotController {

	@Autowired
	private WecharHandler wecharHandler;

	@ResponseBody
	@PostMapping("/reply")
	public void reply(HttpServletRequest req) throws Exception{
		WechatReceive msg = toObject(req, WechatReceive.class);
		if (null != msg.getMsg()) {
			msg.setMsg(WecharHandler.decodeMsg(msg.getMsg()));
		}
		log.info("得到微信信息:{}", msg);

		// 保存、查询 对应的机器人、用户信息

		// 业务处理过程(机器人,用户信息,WechatReceive)
		// 业务返回 sendInfo
		SendMsgAbstract sendInfo = null;
		if("100".equals(msg.getType())){
			sendInfo = new SendTextMsg("我的测试!");
			sendInfo.setRobot_wxid(msg.getRobot_wxid());
			sendInfo.setTo_wxid(msg.getFrom_wxid());
		}

		// 返回
		wecharHandler.sendToWechar(sendInfo);
	}


	private <T> T toObject(HttpServletRequest request, Class<T> beanClass) {
		Map parameterMap = request.getParameterMap();
		log.info("!!!接收微信信息为:{}", parameterMap);
		JSONObject json = new JSONObject();
		Iterator paIter = parameterMap.keySet().iterator();
		while (paIter.hasNext()) {
			String key = paIter.next().toString();
			String[] values = (String[]) parameterMap.get(key);
			json.put(key, values[0]);
		}
		T obj = JSONObject.toJavaObject(json, beanClass);
		log.info("!!!转化微信信息为:{}", obj);
		return obj;
	}

}
