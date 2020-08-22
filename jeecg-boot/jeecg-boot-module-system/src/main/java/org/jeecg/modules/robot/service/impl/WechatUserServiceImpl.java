package org.jeecg.modules.robot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.robot.constants.WechatConstants;
import org.jeecg.modules.robot.constants.WechatUserSourceEnum;
import org.jeecg.modules.robot.constants.WechatUserStateEnum;
import org.jeecg.modules.robot.entity.WechatUser;
import org.jeecg.modules.robot.entity.WxFriend;
import org.jeecg.modules.robot.handler.WecharHandler;
import org.jeecg.modules.robot.mapper.WechatUserMapper;
import org.jeecg.modules.robot.service.IWechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 微信用户
 * @Author: jeecg-boot
 * @Date: 2020-08-22
 * @Version: V1.0
 */
@Slf4j
@Service
public class WechatUserServiceImpl extends ServiceImpl<WechatUserMapper, WechatUser> implements IWechatUserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private WecharHandler wecharHandler;

    @Override
    public WechatUser register(String robotWxid, String wxid) {
        Objects.requireNonNull(robotWxid, "！！！机器人微信ID不能为空");
        Objects.requireNonNull(wxid, "！！！微信用户ID不能为空");

        // 一、判断redis中是否有微信用户信息
        String key = String.format(WechatConstants.USER_REDIS_FORMAT, robotWxid, wxid);
        WechatUser user = (WechatUser) redisUtil.get(key);

        // redis中有 ==>直接返回
        if (null != user) {
            log.info("从Redis缓存中获取微信用户【{},{}】信息:{}", robotWxid, wxid, user);
            return user;
        }

        // 二、redis没有 ==>判断db中是否有微信用户信息
        LambdaQueryWrapper<WechatUser> query = new LambdaQueryWrapper<>();
        query.eq(WechatUser::getRobotWxid, robotWxid);
        query.eq(WechatUser::getWxid, wxid);
        user = getOne(query);

        // db中有 ==>直接返回(并设置redis),如果同步时间未超过一定时间范围内的话
        if (null != user) {
            // 判断是否需要重新同步
            LocalDateTime syncTime = user.getSyncTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            long hours = ChronoUnit.HOURS.between(syncTime, LocalDateTime.now());
            boolean noOverTime = (hours < WechatConstants.USER_SYNC_TIME);
            if (noOverTime) {
                log.info("从db中获取微信用户【{},{}】信息:{}", robotWxid, wxid, user);
                redisUtil.set(key, user, WechatConstants.USER_REDIS_TIME);
                return user;
            }
            log.info("微信用户【{},{}】信息需要重新同步:{}", robotWxid, wxid, user);
        }

        //三、db中没有 ==>判断微信api中是否有微信用户信息
        List<WxFriend> wxFriendList = wecharHandler.getFriendList(robotWxid);
        WxFriend wxFriend = wxFriendList.stream().filter(ro -> wxid.equals(ro.getWxid())).findFirst().orElse(null);

        // 微信api中没有 ==> 报出异常
        if (null == wxFriend) {
            log.error("！！！微信用户【{},{}】不存在信息", robotWxid, wxid);
            throw new RuntimeException("！！！微信用户不存在");
        }

        // 微信api中有 ==> 直接返回(并设置redis，保存到db)
        WechatUser newUser = buildWechatUser(user, wxFriend);
        saveOrUpdate(newUser);
        redisUtil.set(key, newUser, WechatConstants.USER_REDIS_TIME);
        log.info("从微信api中获取微信用户【{},{}】信息:{}", robotWxid, wxid, user);
        return newUser;
    }

    private WechatUser buildWechatUser(WechatUser user, WxFriend wxFriend) {
        Date now = new Date();
        if (null == user) {
            user = new WechatUser();
            user.setCreateTime(now);
            user.setRobotWxid(wxFriend.getRobot_wxid());
            user.setSource(WechatUserSourceEnum.AUTO_ADD.getCode());
            user.setState(WechatUserStateEnum.NORMAL.getCode());
        } else {
            user.setUpdateTime(now);
        }
        user.setSyncTime(now); // 注意：设置同步时间
        user.setWxid(wxFriend.getWxid());
        user.setNickname(wxFriend.getNickname());
        user.setNote(wxFriend.getNote());
        return user;
    }
}
