package org.jeecg.modules.robot.entity;

import lombok.Getter;
import lombok.Setter;
import org.jeecg.modules.robot.handler.WecharHandler;

@Setter
@Getter
public abstract class SendMsgAbstract {
    // 忽略，在v2.3及以上启用了key验证才需要配这个;如:4612AA169B274a7e906740377F0DC423
    private String key = WecharHandler.KEY;

    // 发消息的机器账号id，默认是收到消息的这个号，如登录了两个号，也可以用其他的号发出;如:csp961096506
    private String robot_wxid;

    // 对方id（默认发送至来源的id，也可以发给其他人）;如:linxw1219
    private String to_wxid;

    // Api数值（可以参考 - api列表demo）;如:100
    public abstract int getType();

    // 发送内容;如:ok
    public abstract Object getMsg();

}
