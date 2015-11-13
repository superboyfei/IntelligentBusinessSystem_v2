package com.centerm.task;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.centerm.network.socket.server.MsgReceiver;
import com.centerm.service.business.BusinessRecordService;
import com.centerm.service.device.DeviceService;
import com.centerm.service.device.DeviceStatusService;
import com.centerm.service.device.DeviceTypeService;
import com.centerm.service.manage.business.BusinessService;
import com.centerm.service.manage.outlets.OutletsService;
import com.centerm.service.manage.system.ServerinfoService;

@Component("initializeTask")
public class InitializeTask
{
	private static final Logger logger = LogManager.getLogger(InitializeTask.class);
	
	@Resource(name = "serverinfoService")
	private ServerinfoService serverinfoService;
	@Resource(name = "businessService")
	private BusinessService businessService;
	@Resource(name = "outletsService")
	private OutletsService outletsService;
	@Resource(name = "msgReceiver")
	private MsgReceiver msgReceiver;
	@Resource(name = "deviceTypeService")
	private DeviceTypeService deviceTypeService;
	@Resource(name = "deviceStatusService")
	private DeviceStatusService deviceStatusService;
	@Resource(name = "businessRecordService")
	private BusinessRecordService businessRecordService;
	@Resource(name="partitionCheckTask")
	private PartitionCheckTask partitionCheckTask;
	@Resource(name = "deviceService")
	private DeviceService deviceService;
	
	/**
	 * 记录服务启动时间
	 */
	public void recordStartupTime()
	{
		try
		{
			serverinfoService.recordStartupTime();
		}
		catch(Exception e)
		{
			logger.debug("记录服务启动时间异常", e);
		}
	}
	
	/**
	 * 初始化排队号
	 */
	public void initBusinessRecordCode()
	{
		businessRecordService.initBusinessRecordCode();
	}
	
	/**
	 * 开始监听
	 */
	public void startReceive()
	{
		msgReceiver.startDeviceReceive();
		msgReceiver.startCounterReceive();
	}
	
	/**
	 * 初始化全局变量
	 */
	public void initGlobalVariables()
	{
		try
		{
			businessService.initBusinessMap();
			outletsService.initOutletsMap();
			deviceTypeService.initDeviceTypeMap();
			deviceStatusService.initDeviceStatusMap();
		}
		catch(Exception e)
		{
			logger.error("初始化全局变量异常", e);
		}
	}
	
	/**
	 * 动态创建表分区
	 * 
	 * */
	public void checkPartitions(){
		partitionCheckTask.checkBusinessRecordPartition();
		partitionCheckTask.checkBusinessStatisticsPartition();
	}
	
	/**
	 * 初始化所有设备的状态为下线
	 * 
	 */
	public void initDeviceStatus() {
		deviceService.initDeviceStatus();
	}
}
