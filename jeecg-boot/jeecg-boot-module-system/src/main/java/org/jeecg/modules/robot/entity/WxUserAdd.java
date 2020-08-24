package org.jeecg.modules.robot.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Getter
@Setter
@ToString
public class WxUserAdd implements Serializable {
    public static final int SEARCH_QQ_ADD = 1; //搜索QQ号添加
    public static final int SEARCH_WECHAT_NUM_ADD = 3; //搜索微信号添加
    public static final int SEARCH_WECHAT_GROUP_ADD = 14; //通过微信群添加
    public static final int SEARCH_MOBILE_ADD = 15; //搜索手机号添加
    public static final int CARD_SHARING_ADD = 17; //通过名片分享添加
    public static final int SCAN_ADD = 30; //扫二维添加

    private String to_wxid;	// 如：csp961096506
    private String to_name;	// 如：Simple.Chen
    private String from_wxid;	// 如：wxid_g3xnklexyaj722
    private String from_nickname;	// 如：小妮子saly
    private Integer sex;	// 如：2
    private String headimgurl;	// 如：http:\/\/wx.qlogo.cn\/mmhead\/ver_1\/AJ8UaNdeH52XBbfYL9dxjca4niczGicrrWCeL8ibDia4tPS565akKyapqXnibJx8HIFpUOCMXx3VDhvs1qdnrCrlqIrmrwR6Llq2jw1l0fBaL1js\/132
    private Integer type;	// 如：1
    private String share_wxid; // 分享者微信id,如:linxw1219
    private String share_nickname; // 如:安之若素
}
