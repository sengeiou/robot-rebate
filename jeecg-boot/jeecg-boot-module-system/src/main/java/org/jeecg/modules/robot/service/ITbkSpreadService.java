package org.jeecg.modules.robot.service;

import org.jeecg.modules.robot.entity.TbkSpread;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 推广位
 * @Author: jeecg-boot
 * @Date:   2020-09-05
 * @Version: V1.0
 */
public interface ITbkSpreadService extends IService<TbkSpread> {
    /**
     * 自动批量添加推广位
     * @param topCount 将数据库中推广位同步到多少数量
     */
    public void autoBatchAdd(int topCount);

    /**
     * 通过PID查询对应的推广位信息
     * @param pid 推广位PID
     * @return 推广位
     */
    public TbkSpread getByPid(String pid);

    /**
     * 查询下一个空闲的推广位信息
     * @param pubId 淘宝客产品ID
     * @param siteId 淘宝客媒体ID
     * @return 推广位
     */
    public TbkSpread getNextFreeOne(Long pubId,Long siteId);
}
