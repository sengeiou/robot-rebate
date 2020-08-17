package org.jeecg.modules.robot.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class WxReceive implements Serializable {

    private String type;	// 如：100
    private String msg_type;	// 如：1
    private String from_wxid;	// 如：linxw1219
    private String from_name;	// 如：Viel微
    private String final_from_wxid;	// 如：linxw1219
    private String final_from_name;	// 如：Viel微
    private String robot_wxid;	// 如：csp961096506
    private String file_url;	// 如：null
    private String msg;	// 如：3
    private String time;	// 如：159629201
    
}
