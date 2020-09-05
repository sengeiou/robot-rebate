package org.jeecg.modules.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.robot.service.ITbkTradeService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 定时同步淘宝客订单
 */
@Slf4j
public class TbkTradeSyncJob implements Job {

	@Autowired
	private ITbkTradeService tbkTradeService;

	/**
	 * 参数
	 */
	private String parameter;

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		log.info(String.format("welcome %s! 定时同步淘宝客订单 TbkTradeSyncJob !   时间:" + DateUtils.now(), this.parameter));
		tbkTradeService.syncTradesFromTbk();
	}
}
