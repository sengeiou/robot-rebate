package org.jeecg.modules.robot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.robot.entity.TbkSpread;

/**
 * @Description: 推广位
 * @Author: jeecg-boot
 * @Date:   2020-09-05
 * @Version: V1.0
 */
public interface TbkSpreadMapper extends BaseMapper<TbkSpread> {

    TbkSpread getNextFreeOne(Long pubId, Long siteId);
}
