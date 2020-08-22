package org.jeecg.modules.robot.service.impl;

import org.jeecg.modules.robot.entity.WechatUser;
import org.jeecg.modules.robot.mapper.WechatUserMapper;
import org.jeecg.modules.robot.service.IWechatUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 微信用户
 * @Author: jeecg-boot
 * @Date:   2020-08-22
 * @Version: V1.0
 */
@Service
public class WechatUserServiceImpl extends ServiceImpl<WechatUserMapper, WechatUser> implements IWechatUserService {

}
