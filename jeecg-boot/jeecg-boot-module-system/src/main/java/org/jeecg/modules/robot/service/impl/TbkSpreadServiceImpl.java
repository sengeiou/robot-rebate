package org.jeecg.modules.robot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.robot.constants.WechatConstants;
import org.jeecg.modules.robot.entity.TbAdzone;
import org.jeecg.modules.robot.entity.TbkSpread;
import org.jeecg.modules.robot.handler.TbkHandler;
import org.jeecg.modules.robot.mapper.TbkSpreadMapper;
import org.jeecg.modules.robot.service.ITbkSpreadService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description: 推广位
 * @Author: jeecg-boot
 * @Date: 2020-09-05
 * @Version: V1.0
 */
@Slf4j
@Service
public class TbkSpreadServiceImpl extends ServiceImpl<TbkSpreadMapper, TbkSpread> implements ITbkSpreadService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TbkHandler tbkHandler;

    @Autowired
    private TbkSpreadMapper spreadMapper;

    @Override
    @Transactional
    public void autoBatchAdd(int topCount) {
        // 判断 topCount <= 推广拉表的数量
        // 是==> 不执行
        int count = count();
        if (topCount <= count) {
            log.warn("推广位数量已经达到要求，不需要新增了！");
            return;
        }
        // 否==> 计算出需要添加的推广位数
        int add = topCount - count;

        // 不过新增 推广位 ，并设置到 推广位信息保存到数据表中
        List<TbkSpread> spreadList = new ArrayList<>();
        try {
            Date now = new Date();
            for (int i = 1; i <= add; i++) {
                TbAdzone adzone = null;
                try {
                    adzone = tbkHandler.addAdzone("销售_" + (count + i));
                } catch (Exception ex) {
                    throw new RuntimeException("批量添加失败" + ex.getMessage());
                }
                TbkSpread spread = new TbkSpread();
                BeanUtils.copyProperties(adzone, spread);
                spread.setCreateTime(now);
                spreadList.add(spread);
            }
            saveBatch(spreadList);
        } catch (Exception ex) {
            String adzoneIds = spreadList.stream().filter(s -> s.getAdzoneId() != null).map(s -> s.getAdzoneId().toString()).collect(Collectors.joining(","));
            if (StringUtils.isNotEmpty(adzoneIds)) {
                try {
                    tbkHandler.delAdzone(adzoneIds);
                } catch (Exception e) {
                    log.error("回滚添加的数据失败:{}", e);
                }
            }
        }
    }

    @Override
    public TbkSpread getByPid(String pid) {
        Objects.requireNonNull(pid, "！！！PID不能为空");

        // 一、判断redis中是否有推广位信息
        String key = String.format(WechatConstants.SPREAD_REDIS_FORMAT, pid);
        TbkSpread spread = (TbkSpread) redisUtil.get(key);

        // redis中有 ==>直接返回
        if (null != spread) {
            log.info("从Redis缓存中获取推广位【{}】信息:{}", pid, spread);
            return spread;
        }

        // 二、redis没有 ==>判断db中是否有推广位信息
        LambdaQueryWrapper<TbkSpread> query = new LambdaQueryWrapper<>();
        query.eq(TbkSpread::getPid, pid);
        spread = getOne(query);

        if (null == spread) {
            return null;
        }
        // db中有 ==>直接返回(并设置redis)
        log.info("从db中获取推广位【{}】信息:{}", pid, spread);
        redisUtil.set(key, spread, WechatConstants.SPREAD_REDIS_TIME);
        return spread;

    }

    @Override
    public TbkSpread getNextFreeOne(Long pubId, Long siteId) {
        Objects.requireNonNull(pubId, "！！！产品ID不能为空");
        Objects.requireNonNull(siteId, "！！！媒体ID不能为空");
        return spreadMapper.getNextFreeOne(pubId, siteId);
    }
}
