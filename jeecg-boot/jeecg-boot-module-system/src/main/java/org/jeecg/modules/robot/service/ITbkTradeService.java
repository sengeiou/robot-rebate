package org.jeecg.modules.robot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.robot.entity.TbkTrade;

/**
 * @Description: 淘宝客订单
 * @Author: jeecg-boot
 * @Date:   2020-09-05
 * @Version: V1.0
 */
public interface ITbkTradeService extends IService<TbkTrade> {
    /**
     * 同步淘宝客订单
     */
    void syncTradesFromTbk();
}
