package org.jeecg.modules.robot.controller;

import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.robot.entity.WxFilterResult;
import org.jeecg.modules.robot.entity.WxReceive;
import org.jeecg.modules.robot.filter.IWxFilterHandler;
import org.jeecg.modules.robot.handler.TbkHandler;
import org.jeecg.modules.robot.handler.WecharHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/robot")
public class RobotController {

	@Autowired
	private List<IWxFilterHandler> filterHandlers;

	@Autowired
	private WecharHandler wecharHandler;

	@Autowired
	private TbkHandler tbkHandler;


	@ResponseBody
	@PostMapping("/channel")
	public void channel(HttpServletRequest req) throws Exception {
		//接收微信信息转为java对象
		WxReceive msg = WxReceive.instance(req);

		// 统一处理并过滤数据
		for (IWxFilterHandler fh : filterHandlers) {
			WxFilterResult result = fh.doFilter(req, msg);
			if (null == result) {
				continue;
			}
			// 如果有要提醒给用户信息，可以设置该对象
			if (null != result.getSendMsg()) {
				wecharHandler.sendToWechar(result.getSendMsg());
			}
			// 如果设置中断后续执行，则直接返回
			if (result.isBreakOff()) {
				return;
			}
		}

	}


	// 取好友列表 -- 测试
	@ResponseBody
	@GetMapping("/getInfo/{key}")
	public Object getInfo(@PathVariable("key") String key, HttpServletRequest req) throws Exception {
		String val = req.getParameter("val");
		Object result = null;
		switch (key) {
			case "get_friend_list": // 查询好友列表
				result = wecharHandler.getFriendList(null);
				break;
			case "get_logged_account_list": // 查询机器人列表
				result = wecharHandler.getRobotList();
				break;
			case "agree_friend_verify": // 同意好友请求
				//wecharHandler.agreeFriendVerify("csp961096506", "{\"share_wxid\":\"linxw1219\",\"share_nickname\":\"安之若素\",\"to_wxid\":\"csp961096506\",\"to_name\":\"Simple.Chen\",\"msgid\":1697584273,\"from_wxid\":\"wxid_g3xnklexyaj722\",\"from_nickname\":\"小妮子saly\",\"v1\":\"v3_020b3826fd03010000000000db8c0e44037ec5000000501ea9a3dba12f95f6b60a0536a1adb6688801961b54572e0c0519d115a543c9a94fce56a8024660d31e18733e7e6c79420dfbc08ed4f53250eaeb28408272a178370767f37e85ad7fe69471@stranger\",\"v2\":\"v4_000b708f0b040000010000000000c9d927b9ad750468aabcde19345f1000000050ded0b020927e3c97896a09d47e6e9ed6594a86e3fa83798a87dbef49bc6bd2e42621d614e16c8777938f14293f336cecd840f9e218ed2213807084a30e3fe89e4b38e2c7140aff353f7a52f5bf1ce55ad7bd135f14c44560ba1521128f981b23a8d21cec362e5685e9bc2283dc975daf2481f42dbf01fa@stranger\",\"sex\":2,\"from_content\":\"我是小妮子saly\",\"headimgurl\":\"http:\\/\\/wx.qlogo.cn\\/mmhead\\/ver_1\\/AJ8UaNdeH52XBbfYL9dxjca4niczGicrrWCeL8ibDia4tPS565akKyapqXnibJx8HIFpUOCMXx3VDhvs1qdnrCrlqIrmrwR6Llq2jw1l0fBaL1js\\/132\",\"type\":17}");
				wecharHandler.agreeFriendVerify("wxid_xu97ppnheq5j22", "{\"to_wxid\":\"wxid_xu97ppnheq5j22\",\"to_name\":\"缘与份\",\"msgid\":1122063375,\"from_wxid\":\"csp961096506\",\"from_nickname\":\"Simple.Chen\",\"v1\":\"v1_4646aad2026508fb0ee1aef6711e21173af260635c3a38477f1c009bc52b0db3@stranger\",\"v2\":\"v4_000b708f0b040000010000000000a7ac51f80cd69c7c7b162eeb445f1000000050ded0b020927e3c97896a09d47e6e9eff217812fc966fc92fc953014bcba57d9648bea15f23f6bdbcf4fc00aaaec81945f479c35d9c2c8413b6a6503d8ff1b15b0c45abb608088af517f0cfc2b077e8ce5bfa47e1d7d6e78f36bb528a5a18bb8e7f2fafc60562b9ba7e54186d2e443046d085e003fd61510c@stranger\",\"sex\":1,\"from_content\":\"陈升鹏\",\"headimgurl\":\"http:\\/\\/wx.qlogo.cn\\/mmhead\\/ver_1\\/anSKLV8tUJ7AOz43oLlIglpGCKVBpRWQPSdy3SwJVU7VI5jEiaS40Acpm0wFKugB2FWPun3JjQIdTtsdmHbS2zw\\/96\",\"type\":3}");
				break;
			case "modify_friend_note": // 修改好友备注
				wecharHandler.modifyFriendNote("csp961096506", "wxid_g3xnklexyaj722", "微小小");
				break;

			// 淘宝客
			case "getItemIdByHttpPwd":
				Long itemId = tbkHandler.getItemIdByHttpPwd(val);
				if(null == itemId){
					break;
				}
				TbkDgMaterialOptionalResponse.MapData obj = tbkHandler.getPushInfo(itemId, null);
				if(null == obj){
					break;
				}
				String url = (obj.getCouponShareUrl() != null ? obj.getCouponShareUrl() : obj.getUrl());
				String tip = tbkHandler.createPushPwd(url, obj.getPictUrl());
				log.info("tip:{}", tip);
				break;
		}
		return result;
	}


}
