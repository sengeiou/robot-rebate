package org.jeecg.modules.robot.constants;

public class WechatConstants {
    // 机器人存放request的key
    public static final String ROBOT = "WECHAT_ROBOT";
    // 机器人存放redis的key前缀
    public static final String ROBOT_REDIS_FORMAT = "wechat.robot.info.%s";
    // 机器人存放redis的过期间隔时间（单位：秒）
    public static final int ROBOT_REDIS_TIME = 60 * 60 * 24;
    // 机器人存放同步的间隔时间（单位：小时）
    public static final long ROBOT_SYNC_TIME = 24 * 2;

    // 微信用户存放request的key
    public static final String USER = "WECHAT_USER";
    // 微信用户存放redis的key前缀
    public static final String USER_REDIS_FORMAT = "wechat.user.info.%s#%s";
    // 微信用户存放redis的过期间隔时间（单位：秒）
    public static final int USER_REDIS_TIME = 60 * 60 * 6;
    // 微信用户存放同步的间隔时间（单位：小时）
    public static final long USER_SYNC_TIME = 24 * 1;

    // 推广位存放request的key
    public static final String SPREAD = "TBK_SPREAD";
    // 推广位存放redis的key前缀
    public static final String SPREAD_REDIS_FORMAT = "tbk.spread.info.%s";
    // 推广位存放redis的过期间隔时间（单位：秒）
    public static final int SPREAD_REDIS_TIME = 60 * 60 * 1;

}
