package org.jeecg.modules.robot.handler;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.robot.entity.WechatReplyMatche;
import org.jeecg.modules.robot.entity.WxFilterResult;
import org.jeecg.modules.robot.entity.WxReceive;
import org.jeecg.modules.robot.service.IWechatReplyMatcheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 3、自动匹配处理业务数据
 */
@Order(3)
@Slf4j
@Component
public class WxReplyMatcheFilterHandler implements IWxFilterHandler {

    @Autowired
    private IWechatReplyMatcheService wechatReplyMatcheService;

    @Override
    public WxFilterResult doFilter(HttpServletRequest req, WxReceive receive) {
        //获取回复匹配列表(缓存)
        List<WechatReplyMatche> matcheList = wechatReplyMatcheService.selectAll();
        Assert.notEmpty(matcheList, "暂无回复匹配列表数据");

        //获取匹配信息
        WechatReplyMatche matche = wechatReplyMatcheService.getMatche(matcheList, receive);
        Assert.notNull(matche, "未获取匹配信息");
        return null;
    }

}
