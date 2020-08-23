package org.jeecg.modules.robot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.robot.entity.WechatReplyMatche;
import org.jeecg.modules.robot.entity.WxReceive;

import java.util.List;

/**
 * @Description: 回复匹配
 * @Author: jeecg-boot
 * @Date: 2020-08-22
 * @Version: V1.0
 */
public interface IWechatReplyMatcheService extends IService<WechatReplyMatche> {

    /**
     * 获取回复匹配列表(缓存)
     */
    List<WechatReplyMatche> selectAll();

    /**
     * 获取匹配信息
     */
    WechatReplyMatche getMatche(List<WechatReplyMatche> matcheList, WxReceive msg);
}
