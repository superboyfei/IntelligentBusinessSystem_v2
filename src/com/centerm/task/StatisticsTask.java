package com.centerm.task;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.centerm.msghandler.basehandler.common.BusinessRecordCode;
import com.centerm.service.business.StatisticsRecordService;

@Component("statisticsTask")
public class StatisticsTask 
{
	private static final Logger logger = LogManager.getLogger(StatisticsTask.class);
	
	@Resource(name = "statisticsRecordService")
	private StatisticsRecordService statisticsRecordService;
	@Resource(name = "businessRecordCode")
	private BusinessRecordCode businessRecordCode;
	
	/**
	 * 统计业务数据
	 */
	//@Scheduled(cron="0 0 0 * * ? ")
	public void statisticsBusinessRecord()
	{
		try
		{
			statisticsRecordService.statisticsBusinessRecord();
		}
		catch(Exception e)
		{
			logger.error("统计业务数据异常", e);
		}
	}
}
