package com.centerm.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.centerm.service.device.DeviceService;

@Component("checkDeviceStatusTask")
public class CheckDeviceStatusTask {
	private static final Logger logger = LogManager.getLogger(CheckDeviceStatusTask.class);
	
	private static Map<String, Long> deviceOnlineTimeMap = new HashMap<String, Long>();
	
	@Resource(name = "deviceService")
	private DeviceService deviceService;
	
	/**
	 * 获取超时的设备的序列号
	 * @return
	 */
	private List<String> check(){
		long nowTime = System.currentTimeMillis();
		List<String> outOfTimeList = new ArrayList<String>();
		Set<String> keys = deviceOnlineTimeMap.keySet();
		for(String key : keys){
			long lastTime = deviceOnlineTimeMap.get(key);
			long difference = nowTime - lastTime;
			// 超过30分钟
			if(difference > 1800 * 1000){
				outOfTimeList.add(key);
			}
		}
		
		return outOfTimeList;
	}
	
	/**
	 * 5分钟检查一次
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void checkDeviceOnlineTime() {
		List<String> shutDownList = check();
		deviceService.shutDownDevice(shutDownList);
		for(String serial : shutDownList){
			deviceOnlineTimeMap.remove(serial);
		}
		if(!shutDownList.isEmpty()) {
			StringBuilder name = new StringBuilder();
			for(String serial : shutDownList) {
				name.append(serial + ",");
			}
			logger.debug(name.toString() + "共" + shutDownList.size() + "台设备下线");
		}
	}
	
	public synchronized void put(String serial,long time){
		deviceOnlineTimeMap.put(serial, time);
	}
	
	public synchronized void remove(String serial){
		deviceOnlineTimeMap.remove(serial);
	}
	
	public Map<String, Long> get(){
		return deviceOnlineTimeMap;
	}
	

}
