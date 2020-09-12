package org.jeecg.modules.robot.service;

import org.jeecg.modules.robot.entity.WechatUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 微信用户
 * @Author: jeecg-boot
 * @Date:   2020-08-22
 * @Version: V1.0
 */
public interface IWechatUserService extends IService<WechatUser> {
    /**
     * 注册微信用户信息
     * @param robotWxid 机器人微信ID
     * @param wxid 微信用户ID
     * @return 微信用户信息
     */
    WechatUser register(String robotWxid, String wxid);

    /**
     * 通过 机器人微信ID + 微信用户ID 查询对应的微信用户信息
     * @param robotWxid 机器人微信ID
     * @param wxid 微信用户ID
     * @return 微信用户信息
     */
    WechatUser getUserByWxid(String robotWxid, String wxid);

    /**
     * 设置微信用户的PID
     * @param user 微信用户信息
     */
    void setPid(WechatUser user);
}
