package org.jeecg.modules.robot.handler;

import org.jeecg.modules.robot.entity.WxFilterResult;
import org.jeecg.modules.robot.entity.WxReceive;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信过滤器接口
 * 1、 WxUserAddFilterHandler 添加好友申请过滤；
 * 2、 RegisterFilterHandler 注册/保存 机器人、好友信息；拉黑、禁用用户不理会消息
 *
 */
public interface IWxFilterHandler {
    public abstract WxFilterResult doFilter(HttpServletRequest req, WxReceive receive);
}
