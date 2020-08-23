package org.jeecg.modules.robot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.modules.robot.entity.WechatReplyMatche;
import org.jeecg.modules.robot.entity.WxReceive;
import org.jeecg.modules.robot.mapper.WechatReplyMatcheMapper;
import org.jeecg.modules.robot.service.IWechatReplyMatcheService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;

import java.util.List;

/**
 * @Description: 回复匹配
 * @Author: jeecg-boot
 * @Date: 2020-08-22
 * @Version: V1.0
 */
@Service
public class WechatReplyMatcheServiceImpl extends ServiceImpl<WechatReplyMatcheMapper, WechatReplyMatche> implements IWechatReplyMatcheService {

    @Cacheable(cacheNames = CacheConstant.TEST_DEMO_CACHE, value = "WechatReplyMatcheServiceImpl.selectAll")
    public List<WechatReplyMatche> selectAll() {
        return this.list();
    }

    @Override
    public WechatReplyMatche getMatche(List<WechatReplyMatche> matcheList, WxReceive msg) {
        for (WechatReplyMatche matche : matcheList) {
            //验证
            String content = matche.getContent();
            Assert.notNull(content, "匹配内容不能为空,id=" + matche.getId());
            Integer type = matche.getType();
            Assert.notNull(type, "匹配事件不能为空,id=" + matche.getId());

            //匹配失败
            if (!matcher(type.toString(), msg.getMsg_type()) || !matcher(content, msg.getMsg())) {
                continue;
            }

            //匹配成功
            return matche;
        }
        return null;
    }

    public boolean matcher(String pattern, String str) {
        PathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, str);
    }
}
