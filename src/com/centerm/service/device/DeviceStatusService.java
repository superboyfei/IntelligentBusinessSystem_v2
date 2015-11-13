package com.centerm.service.device;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.DeviceStatusDao;
import com.centerm.entity.DeviceStatus;
import com.centerm.util.GlobalVariables;

@Service("deviceStatusService")
public class DeviceStatusService
{
	//private static final Logger logger = LogManager.getLogger(DeviceStatusService.class);
	
	@Resource(name = "deviceStatusDao")
	private DeviceStatusDao deviceStatusDao;
	
	/**
	 * 初始化设备状态id-设备状态description对应MAP
	 * @return
	 * true - 初始化成功<br>
	 * false - 初始化失败
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public void initDeviceStatusMap()
	{
		List<DeviceStatus> deviceStatusList = deviceStatusDao.findAll();
		if ( deviceStatusList != null && deviceStatusList.size() > 0 )
		{
			for ( DeviceStatus deviceStatus : deviceStatusList )
			{
				GlobalVariables.deviceStatusMap.put(deviceStatus.getId(), deviceStatus.getDescription());
			}
		}
	}
}
