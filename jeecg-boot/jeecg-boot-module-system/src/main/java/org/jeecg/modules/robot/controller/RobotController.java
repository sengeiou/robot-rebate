package org.jeecg.modules.robot.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.robot.constants.WechatConstants;
import org.jeecg.modules.robot.entity.SendMsgAbstract;
import org.jeecg.modules.robot.entity.WechatRobot;
import org.jeecg.modules.robot.entity.WechatUser;
import org.jeecg.modules.robot.entity.WxReceive;
import org.jeecg.modules.robot.handler.WecharHandler;
import org.jeecg.modules.robot.service.IWechatRobotService;
import org.jeecg.modules.robot.service.IWechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/robot")
public class RobotController {

	@Autowired
	private WecharHandler wecharHandler;

	@Autowired
	private IWechatRobotService wxRobotService;

	@Autowired
	private IWechatUserService wxUserService;

	@ResponseBody
	@PostMapping("/channel")
	public void channel(HttpServletRequest req) throws Exception{
		// 接收微信信息转为java对象
		WxReceive msg = WxReceive.instance(req);

		// 保存、查询 对应的机器人、用户信息
		WechatRobot robot = wxRobotService.register(msg.getRobot_wxid());
		req.setAttribute(WechatConstants.ROBOT, robot);
		WechatUser user = wxUserService.register(msg.getRobot_wxid(), msg.getFrom_wxid());
		req.setAttribute(WechatConstants.USER, user);


		// 业务处理过程(机器人,用户信息,WechatReceive)
		// 业务返回 sendInfo
		SendMsgAbstract sendInfo = null;
		/*if("100".equals(msg.getType())){
			sendInfo = new SendTextMsg("我的测试!");
			sendInfo.setRobot_wxid(msg.getRobot_wxid());
			sendInfo.setTo_wxid(msg.getFrom_wxid());
		}*/

		// 返回
		wecharHandler.sendToWechar(sendInfo);
	}


	// 取好友列表
	@ResponseBody
	@GetMapping("/getInfo/{key}")
	public Object getInfo(@PathVariable("key") String key,HttpServletRequest req) throws Exception{
		Object result = null;
		switch (key) {
			case "get_friend_list": // 查询好友列表
				result = wecharHandler.getFriendList(null);
				break;
			case "get_logged_account_list": // 查询机器人列表
				result = wecharHandler.getRobotList();
				break;
			case "agree_friend_verify": // 同意好友请求
				wecharHandler.agreeFriendVerify("csp961096506","{\"share_wxid\":\"linxw1219\",\"share_nickname\":\"安之若素\",\"to_wxid\":\"csp961096506\",\"to_name\":\"Simple.Chen\",\"msgid\":1697584273,\"from_wxid\":\"wxid_g3xnklexyaj722\",\"from_nickname\":\"小妮子saly\",\"v1\":\"v3_020b3826fd03010000000000db8c0e44037ec5000000501ea9a3dba12f95f6b60a0536a1adb6688801961b54572e0c0519d115a543c9a94fce56a8024660d31e18733e7e6c79420dfbc08ed4f53250eaeb28408272a178370767f37e85ad7fe69471@stranger\",\"v2\":\"v4_000b708f0b040000010000000000c9d927b9ad750468aabcde19345f1000000050ded0b020927e3c97896a09d47e6e9ed6594a86e3fa83798a87dbef49bc6bd2e42621d614e16c8777938f14293f336cecd840f9e218ed2213807084a30e3fe89e4b38e2c7140aff353f7a52f5bf1ce55ad7bd135f14c44560ba1521128f981b23a8d21cec362e5685e9bc2283dc975daf2481f42dbf01fa@stranger\",\"sex\":2,\"from_content\":\"我是小妮子saly\",\"headimgurl\":\"http:\\/\\/wx.qlogo.cn\\/mmhead\\/ver_1\\/AJ8UaNdeH52XBbfYL9dxjca4niczGicrrWCeL8ibDia4tPS565akKyapqXnibJx8HIFpUOCMXx3VDhvs1qdnrCrlqIrmrwR6Llq2jw1l0fBaL1js\\/132\",\"type\":17}");
				break;
			case "modify_friend_note": // 修改好友备注
				wecharHandler.modifyFriendNote("csp961096506","wxid_g3xnklexyaj722","微小小");
				break;
		}
		return result;
	}




}
