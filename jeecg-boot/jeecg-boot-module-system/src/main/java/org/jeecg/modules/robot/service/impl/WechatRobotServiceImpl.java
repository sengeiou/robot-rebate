package org.jeecg.modules.robot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.robot.constants.WechatConstants;
import org.jeecg.modules.robot.entity.WechatRobot;
import org.jeecg.modules.robot.entity.WxRobot;
import org.jeecg.modules.robot.handler.WecharHandler;
import org.jeecg.modules.robot.mapper.WechatRobotMapper;
import org.jeecg.modules.robot.service.IWechatRobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 机器人
 * @Author: jeecg-boot
 * @Date: 2020-08-22
 * @Version: V1.0
 */
@Slf4j
@Service
public class WechatRobotServiceImpl extends ServiceImpl<WechatRobotMapper, WechatRobot> implements IWechatRobotService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private WecharHandler wecharHandler;

    @Override
    public WechatRobot register(String robotWxid) {
        Objects.requireNonNull(robotWxid, "！！！机器人微信ID不能为空");

        // 一、判断redis中是否有机器人信息
        String key = String.format(WechatConstants.ROBOT_REDIS_FORMAT, robotWxid);
        WechatRobot robot = (WechatRobot) redisUtil.get(key);

        // redis中有 ==>直接返回
        if (null != robot) {
            log.info("从Redis缓存中获取机器人【{}】信息:{}", robotWxid, robot);
            return robot;
        }

        // 二、redis没有 ==>判断db中是否有机器人信息
        LambdaQueryWrapper<WechatRobot> query = new LambdaQueryWrapper<>();
        query.eq(WechatRobot::getWxid, robotWxid);
        robot = getOne(query);

        // db中有 ==>直接返回(并设置redis),如果同步时间未超过一定时间范围内的话
        if (null != robot) {
            // 判断是否需要重新同步
            LocalDateTime syncTime = robot.getSyncTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            long hours = ChronoUnit.HOURS.between(syncTime, LocalDateTime.now());
            boolean noOverTime = (hours < WechatConstants.ROBOT_SYNC_TIME);
            if (noOverTime) {
                log.info("从db中获取机器人【{}】信息:{}", robotWxid, robot);
                redisUtil.set(key, robot, WechatConstants.ROBOT_REDIS_TIME);
                return robot;
            }
            log.info("机器人【{}】信息需要重新同步:{}", robotWxid, robot);
        }

        //三、db中没有 ==>判断微信api中是否有机器人信息
        List<WxRobot> wxRobotList = wecharHandler.getRobotList();
        WxRobot wxRobot = wxRobotList.stream().filter(ro -> robotWxid.equals(ro.getWxid())).findFirst().orElse(null);

        // 微信api中没有 ==> 报出异常
        if (null == wxRobot) {
            log.error("！！！机器人【{}】不存在信息", robotWxid);
            throw new RuntimeException("！！！机器人不存在");
        }

        // 微信api中有 ==> 直接返回(并设置redis，保存到db)
        WechatRobot newRobot = buildWechatRobot(robot, wxRobot);
        saveOrUpdate(newRobot);
        redisUtil.set(key, newRobot, WechatConstants.ROBOT_REDIS_TIME);
        log.info("从微信api中获取机器人【{}】信息:{}", robotWxid, robot);
        return newRobot;
    }

    private WechatRobot buildWechatRobot(WechatRobot robot, WxRobot wxRobot) {
        Date now = new Date();
        if (null == robot) {
            robot = new WechatRobot();
            robot.setCreateTime(now);
        } else {
            robot.setUpdateTime(now);
        }
        robot.setSyncTime(now); // 注意：设置同步时间

        robot.setWxid(wxRobot.getWxid());
        robot.setNickname(wxRobot.getNickname());
        robot.setHeadUrl(wxRobot.getHead_url());
        robot.setBackgroundUrl(wxRobot.getBackgroundimgurl());
        robot.setSignature(wxRobot.getSignature());
        return robot;
    }
}
