package org.jeecg.modules.robot.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 淘宝客登录信息
 */
@Getter
@Setter
@ToString
public class TbSession {
    private String token;
    private String cookie;
}
