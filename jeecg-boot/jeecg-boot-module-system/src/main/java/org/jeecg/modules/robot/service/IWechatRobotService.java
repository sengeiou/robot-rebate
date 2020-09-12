package org.jeecg.modules.robot.service;

import org.jeecg.modules.robot.entity.WechatRobot;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 机器人
 * @Author: jeecg-boot
 * @Date:   2020-08-22
 * @Version: V1.0
 */
public interface IWechatRobotService extends IService<WechatRobot> {
    /**
     * 注册机器人微信信息
     * @param robotWxid 机器人微信ID
     * @return 机器人微信信息
     */
    WechatRobot register(String robotWxid);
}
