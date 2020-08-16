package org.jeecg.modules.robot.entity;

import org.jeecg.modules.robot.handler.WecharHandler;

/**
 * 发送文字消息(好友或者群)
 */
public class SendTextMsg extends SendMsgAbstract {
    private String msg;
    public SendTextMsg(String msg){
        this.msg = msg;
    }

    @Override
    public int getType() {
        return 100;
    }

    @Override
    public String getMsg() {
        return WecharHandler.encodeMsg(msg);
    }
}
