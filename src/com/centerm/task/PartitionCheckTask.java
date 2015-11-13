package com.centerm.task;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.centerm.service.business.PartitionCheckService;

@Component("partitionCheckTask")
public class PartitionCheckTask {
	@Resource(name = "partitionCheckService")
	private PartitionCheckService partitionCheckService;
	
	/**
	 * 每年1月1日0点0分0秒创建统计表
	 */
	@Scheduled(cron = "0 0 0 1 * ?")
	public void checkBusinessRecordPartition(){
		partitionCheckService.checkBusinessRecordPartition();
	}
	
	/**
	 * 每年每月1日0点0分0秒创建统计表
	 */
	@Scheduled(cron = "0 0 0 1 1 ?")
	public void checkBusinessStatisticsPartition(){
		partitionCheckService.checkBusinessStatisticsPartition();
	}
}
