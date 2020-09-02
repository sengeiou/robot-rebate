package org.jeecg.modules.robot.entity;

import lombok.ToString;
import org.jeecg.modules.robot.utils.URLUtil;

/**
 * 发送文字消息(好友或者群)
 */
@ToString
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
        return URLUtil.encode(msg);
    }
}
