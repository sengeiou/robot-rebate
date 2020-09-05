package org.jeecg.modules.robot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.robot.entity.TbkTrade;

import java.util.List;

/**
 * @Description: 淘宝客订单
 * @Author: jeecg-boot
 * @Date:   2020-09-05
 * @Version: V1.0
 */
public interface TbkTradeMapper extends BaseMapper<TbkTrade> {

    void inserReplaceBatch(@Param("tradeList") List<TbkTrade> tradeList);
}
