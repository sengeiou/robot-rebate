package org.jeecg.modules.robot.constants;

import lombok.Getter;

@Getter
public enum WechatUserSourceEnum {
    OTHER_ADD(0,"其他方式添加(默认)"),
    AUTO_ADD(1,"系统自动补加"),
    SEARCH_QQ_ADD(2,"搜索QQ号添加"),
    SEARCH_WECHAT_NUM_ADD(3,"搜索微信号添加"),
    SEARCH_WECHAT_GROUP_ADD(4,"通过微信群添加"),
    SEARCH_MOBILE_ADD(5,"搜索手机号添加"),
    CARD_SHARING_ADD(6,"通过名片分享添加"),
    SCAN_ADD(7,"扫二维添加");

    int code;
    String text;

    WechatUserSourceEnum(int _code, String _text){
        this.code = _code;
        this.text = _text;
    }

}
