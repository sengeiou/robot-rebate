package org.jeecg.modules.robot.entity;

import lombok.Data;

/**
 * 微信好友信息
 */
@Data
public class WxFriend {
    private String wxid; // 微信用户id,如：linxw1219
    private String nickname; // 微信昵称如：安之若素
    private String note; // 微信备注，如：Viel微
    private String robot_wxid; // 微信用户所对应的机器人，如：csp961096506
}
