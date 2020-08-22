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

    WechatUser register(String robotWxid, String wxid, String nickName);
}
