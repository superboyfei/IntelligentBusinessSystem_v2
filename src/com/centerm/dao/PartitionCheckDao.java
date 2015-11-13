package com.centerm.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("partitionCheckDao")
public class PartitionCheckDao extends BaseDao<Integer>{
	/**
	 * 每次启动服务器或定时任务时间到会执行此存储过程创建BusinessRecord的动态分区
	 * */
	public void checkBusinessRecordPartition(){
		String procedure = "CALL PROC_BUSINESSRECORD_MONTH_PARTITION()";
		Query query = getSession().createSQLQuery(procedure);
		query.executeUpdate();
	}
	
	/**
	 * 每次启动服务器或定时任务时间到会执行此存储过程创建BusinessStatistics的动态分区
	 * */
	public void checkBusinessStatisticsPartition(){
		String procedure = "CALL PROC_BUSINESSSTATISTICS_YEAR_PARTITION()";
		Query query = getSession().createSQLQuery(procedure);
		query.executeUpdate();
	}
}
