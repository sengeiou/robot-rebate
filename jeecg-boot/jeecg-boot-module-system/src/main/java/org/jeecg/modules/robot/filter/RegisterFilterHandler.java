package org.jeecg.modules.robot.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.robot.constants.WechatConstants;
import org.jeecg.modules.robot.constants.WechatUserStateEnum;
import org.jeecg.modules.robot.entity.*;
import org.jeecg.modules.robot.service.ITbkSpreadService;
import org.jeecg.modules.robot.service.IWechatRobotService;
import org.jeecg.modules.robot.service.IWechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 2、注册/保存 机器人、好友信息；拉黑、禁用用户不理会消息
 */
@Slf4j
@Component
@Order(2)
public class RegisterFilterHandler implements IWxFilterHandler {

    @Autowired
    private IWechatRobotService wxRobotService;

    @Autowired
    private IWechatUserService wxUserService;

    @Autowired
    private ITbkSpreadService spreadService;

    @Override
    public WxFilterResult doFilter(HttpServletRequest req, WxReceive receive) {
        if (!"100".equals(receive.getType())) {
            return null;
        }
        WxFilterResult result = new WxFilterResult();

        // 保存、查询 对应的机器人、用户信息
        WechatRobot robot = wxRobotService.register(receive.getRobot_wxid());
        req.setAttribute(WechatConstants.ROBOT, robot);

        if (receive.getRobot_wxid().equals(receive.getFrom_wxid())) {
            return result;
        }
        WechatUser user = wxUserService.register(receive.getRobot_wxid(), receive.getFrom_wxid());
        if(null == user){
            result.setBreakOff(false); // 注：不中断后续执行
            return result;
        }

        req.setAttribute(WechatConstants.USER, user);

        if (StringUtils.isNotEmpty(user.getPid())) {
            TbkSpread spread = spreadService.getByPid(user.getPid()); // 查询推广位
            req.setAttribute(WechatConstants.SPREAD, spread);
        }

        // 拉黑、禁用的用户,直接不理会
        if (WechatUserStateEnum.BLACK.getCode() == user.getState() ||
                WechatUserStateEnum.DISABLED.getCode() == user.getState()) {
            log.warn("!!!拉黑、禁用用户【{},{}】不理会消息：{}", receive.getRobot_wxid(), receive.getFrom_wxid(), receive);
            result.setBreakOff(true); // 注：中断后续执行
        } else {
            result.setBreakOff(false); // 注：不中断后续执行
        }
        return result;
    }

}
