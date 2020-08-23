package org.jeecg.modules.robot.constants;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum WechatUserSexEnum {
    OHTER(0,"未知"),
    BOY(1,"男性"),
    GIRL(2,"女性");


    int code;
    String text;

    WechatUserSexEnum(int _code, String _text){
        this.code = _code;
        this.text = _text;
    }

    public static WechatUserSexEnum of(Integer sex) {
        return Arrays.stream(values()).filter(x -> sex != null && sex == x.getCode()).findFirst().orElse(WechatUserSexEnum.OHTER);
    }
}
