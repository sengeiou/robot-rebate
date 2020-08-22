package org.jeecg.modules.robot.constants;

import lombok.Getter;

@Getter
public enum WechatUserStateEnum {
    NORMAL(0,"正常"),
    DISABLED(1,"禁用"),
    BLACK(2,"拉黑");

    int code;
    String text;

    WechatUserStateEnum(int _code, String _text){
        this.code = _code;
        this.text = _text;
    }

}
