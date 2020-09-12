package org.jeecg.modules.robot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.robot.entity.WechatUser;

/**
 * @Description: 微信用户
 * @Author: jeecg-boot
 * @Date:   2020-08-22
 * @Version: V1.0
 */
public interface WechatUserMapper extends BaseMapper<WechatUser> {

    void updatePid(@Param("user") WechatUser user);
}
