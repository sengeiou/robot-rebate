package org.jeecg.modules.robot.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.robot.entity.TbOrder;
import org.jeecg.modules.robot.entity.TbOrderPage;
import org.jeecg.modules.robot.entity.TbOrderPageSearch;
import org.jeecg.modules.robot.entity.TbkTrade;
import org.jeecg.modules.robot.handler.TbkHandler;
import org.jeecg.modules.robot.mapper.TbkTradeMapper;
import org.jeecg.modules.robot.service.ITbkTradeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 淘宝客订单
 * @Author: jeecg-boot
 * @Date: 2020-09-05
 * @Version: V1.0
 */
@Slf4j
@Service
public class TbkTradeServiceImpl extends ServiceImpl<TbkTradeMapper, TbkTrade> implements ITbkTradeService {
    @Autowired
    private TbkTradeMapper tbkTradeMapper;

    @Autowired
    private TbkHandler tbkHandler;

    @Override
    public void syncTradesFromTbk() {
        TbOrderPageSearch search = new TbOrderPageSearch();
        int pageNo = 1;
        search.setPageNo(pageNo);
        log.info("搜索条件：{}", search);
        try {
            Date now = new Date();
            TbOrderPage orders = tbkHandler.getTbOrderPage(search);
            log.info("orders:{}",orders);
            List<TbOrder> result = orders.getResult();
            if (!CollectionUtils.isEmpty(result)) {
                List<TbkTrade> tradeList = new ArrayList<>();
                result.stream().forEach(r -> {
                    TbkTrade trade = new TbkTrade();
                    BeanUtils.copyProperties(r, trade);
                    trade.setCreateTime(now);
                    trade.setUpdateTime(now);
                    tradeList.add(trade);
                });
                tbkTradeMapper.inserReplaceBatch(tradeList);

            }

            boolean hasNext = orders.isHasNext();
            if (hasNext == false) {
                return;
            }
            pageNo ++;
            search.setPageNo(pageNo);
            Thread.sleep(1000);
        } catch (Exception ex) {
            log.error("查询淘宝客订单列表报错：{}", ex);

        }
        /*while (true) {
            try {
                TbOrderPage orders = tbkHandler.getTbOrderPage(search);
                List<TbOrder> result = orders.getResult();
                if (!CollectionUtils.isEmpty(result)) {
                    List<TbkTrade> tradeList = new ArrayList<>();
                    result.stream().forEach(r -> {
                        TbkTrade trade = new TbkTrade();
                        BeanUtils.copyProperties(r, trade);
                        tradeList.add(trade);
                    });
                    saveOrUpdateBatch(tradeList);
                }

                boolean hasNext = orders.isHasNext();
                if (hasNext == false) {
                    break;
                }
                pageNo ++;
                search.setPageNo(pageNo);
                Thread.sleep(1000);
            } catch (Exception ex) {
                log.error("查询淘宝客订单列表报错：{}", ex);
                break;
            }
        }*/
        log.info("--------------------------------------------->");
    }

}
