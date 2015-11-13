package com.centerm.service.business;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.centerm.dao.PartitionCheckDao;

@Service("partitionCheckService")
public class PartitionCheckService {
	@Resource(name="partitionCheckDao")
	private PartitionCheckDao partitionCheckDao;
	
	/**
	 * 每次启动服务器或定时任务时间到会执行此存储过程创建BusinessRecord的动态分区
	 * */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void checkBusinessRecordPartition(){
		partitionCheckDao.checkBusinessRecordPartition();
	}
	
	/**
	 * 每次启动服务器或定时任务时间到会执行此存储过程创建BusinessStatistics的动态分区
	 * */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void checkBusinessStatisticsPartition(){
		partitionCheckDao.checkBusinessStatisticsPartition();
	}
}
