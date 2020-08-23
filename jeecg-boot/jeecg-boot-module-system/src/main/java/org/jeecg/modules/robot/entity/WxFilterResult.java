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
public class WxFilterResult implements Serializable {
    private SendMsgAbstract sendMsg;
    private boolean breakOff = false; // 是否中断后续执行
}
