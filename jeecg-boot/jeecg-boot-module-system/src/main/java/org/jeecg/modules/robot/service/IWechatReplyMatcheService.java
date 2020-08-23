package org.jeecg.modules.robot.service;

import org.jeecg.common.constant.CacheConstant;
import org.jeecg.modules.robot.entity.WechatReplyMatche;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.robot.entity.WxReceive;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @Description: 回复匹配
 * @Author: jeecg-boot
 * @Date:   2020-08-22
 * @Version: V1.0
 */
public interface IWechatReplyMatcheService extends IService<WechatReplyMatche> {

    List<WechatReplyMatche> selectAll();

    WechatReplyMatche getMatche(List<WechatReplyMatche> matcheList, WxReceive msg);
}
