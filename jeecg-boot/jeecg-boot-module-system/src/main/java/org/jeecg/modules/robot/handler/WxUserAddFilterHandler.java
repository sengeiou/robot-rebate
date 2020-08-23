package org.jeecg.modules.robot.handler;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.robot.constants.WechatUserSexEnum;
import org.jeecg.modules.robot.constants.WechatUserSourceEnum;
import org.jeecg.modules.robot.constants.WechatUserStateEnum;
import org.jeecg.modules.robot.entity.WechatUser;
import org.jeecg.modules.robot.entity.WxFilterResult;
import org.jeecg.modules.robot.entity.WxReceive;
import org.jeecg.modules.robot.entity.WxUserAdd;
import org.jeecg.modules.robot.service.IWechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 1、处理添加为好友的请求
 */
@Slf4j
@Component
@Order(1)
public class WxUserAddFilterHandler implements IWxFilterHandler {

    @Autowired
    private WecharHandler wecharHandler;

    @Autowired
    private IWechatUserService wxUserService;

    @Override
    public WxFilterResult doFilter(HttpServletRequest req, WxReceive receive) {
        // 一、判断是否为申请添加好友的请求
        // 申请好友，否==>不处理
        if (!"500".equals(receive.getType())) {
            return null;
        }
        WxFilterResult result = new WxFilterResult();
        result.setBreakOff(true); // 注：type=500的消息，都中断后续执行

        // 二、申请好友，是==>判断对应的微信用户是否存在
        WechatUser user = wxUserService.getUserByWxid(receive.getRobot_wxid(), receive.getFrom_wxid());

        // 1、微信用户，存在==>判断是否拉黑用户
        // 拉黑用户，是==>直接不处理
        if (WechatUserStateEnum.BLACK.getCode() == user.getState()) {
            log.warn("!!!微信用户【{},{}】是拉黑用户，系统不自动添加", receive.getRobot_wxid(), receive.getFrom_wxid());
            return result;
        }

        // 拉黑用户，不是==>更新用户信息，并同意好友申请
        // 2、微信用户，不存在==>直接添加用户，并同意好友申请
        WechatUser newUser = buildWechatUser(user, receive);
        wxUserService.saveOrUpdate(newUser);
        wecharHandler.agreeFriendVerify(receive.getRobot_wxid(), receive.getMsg());
        return result;
    }

    private WechatUser buildWechatUser(WechatUser user, WxReceive receive) {
        Date now = new Date();
        WxUserAdd wxApplyAdd = receive.getMsgObj(WxUserAdd.class);
        if(null == user){
            user = new WechatUser();
            user.setRobotWxid(receive.getRobot_wxid());
            user.setWxid(receive.getFrom_wxid());
            user.setCreateTime(now);
            user.setState(WechatUserStateEnum.NORMAL.getCode());
        }else{
            user.setUpdateTime(now);
        }
        user.setNickname(receive.getFrom_name());
        user.setHeadUrl(wxApplyAdd.getHeadimgurl());
        user.setSource(getWxUserSource(wxApplyAdd.getType()));
        user.setSex(WechatUserSexEnum.of(wxApplyAdd.getSex()).getCode());
        user.setSyncTime(now);
        // 如果是卡片分享，则设置他的上级
        if (wxApplyAdd.getType() != null && wxApplyAdd.getType() == WxUserAdd.CARD_SHARING_ADD) {
            user.setInviteWxid(wxApplyAdd.getTo_wxid());
        }

        return user;
    }

    private Integer getWxUserSource(Integer wxType){
        if (null != wxType) {
            switch (wxType.intValue()) {
                case WxUserAdd.SEARCH_QQ_ADD: // 搜索QQ号添加
                    wxType = WechatUserSourceEnum.SEARCH_QQ_ADD.getCode();
                    break;
                case WxUserAdd.SEARCH_WECHAT_NUM_ADD: // 搜索微信号添加
                    wxType = WechatUserSourceEnum.SEARCH_WECHAT_NUM_ADD.getCode();
                    break;
                case WxUserAdd.SEARCH_WECHAT_GROUP_ADD: // 通过微信群添加
                    wxType = WechatUserSourceEnum.SEARCH_WECHAT_GROUP_ADD.getCode();
                    break;
                case WxUserAdd.SEARCH_MOBILE_ADD: // 搜索手机号添加
                    wxType = WechatUserSourceEnum.SEARCH_MOBILE_ADD.getCode();
                    break;
                case WxUserAdd.CARD_SHARING_ADD: // 通过名片分享添加
                    wxType = WechatUserSourceEnum.CARD_SHARING_ADD.getCode();
                    break;
                case WxUserAdd.SCAN_ADD: // 扫二维添加
                    wxType = WechatUserSourceEnum.SCAN_ADD.getCode();
                    break;
            }
        }
        return (null != wxType) ? wxType : WechatUserSourceEnum.OTHER_ADD.getCode();
    }
}
