package org.jeecg.modules.robot.entity;

import lombok.Data;

/**
 * 微信机器人信息
 */
@Data
public class WxRobot {
    private String wxid;    // 微信ID
    private String nickname;    // 微信昵称
    private String head_url;    // 微信头像路径
    private String signature;   // 个性签名
    private String backgroundimgurl;   // 微信背景图片路径
    // private String robot_wxid;
    // private Integer status;
    // private String wx_num;
    // private Date login_time;
    // private String wx_hand;
    // private String wx_wind_handle;
    // private String pid;
    // private String update_desc;
    // private String headimgurl;
}
