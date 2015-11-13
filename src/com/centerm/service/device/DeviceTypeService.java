package com.centerm.service.device;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.DeviceTypeDao;
import com.centerm.entity.DeviceType;
import com.centerm.util.GlobalVariables;

@Service("deviceTypeService")
public class DeviceTypeService
{
	private static final Logger logger = LogManager.getLogger(DeviceTypeService.class);
	
	@Resource
	private DeviceTypeDao deviceTypeDao;
	
	/**
	 * 获得所有设备类型列表
	 * @return	设备类型列表，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<DeviceType> getAllDeviceType()
	{
		List<DeviceType> deviceTypeList = deviceTypeDao.findAll();
		if ( deviceTypeList == null )
		{
			logger.debug("deviceTypeList为null");
		}
		
		return deviceTypeList;
	}
	
	/**
	 * 初始化设备类型id-设备类型code对应MAP
	 * @return
	 * true - 初始化成功<br>
	 * false - 初始化失败
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public void initDeviceTypeMap()
	{
		List<DeviceType> deviceTypeList = deviceTypeDao.findAll();
		if ( deviceTypeList != null && deviceTypeList.size() > 0 )
		{
			for ( DeviceType deviceType : deviceTypeList )
			{
				GlobalVariables.deviceTypeMap.put(deviceType.getId(), deviceType.getCode());
			}
		}
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public DeviceType getDeviceTypeByCode(String code)
	{
		return deviceTypeDao.getDeviceTypeByCode(code);
	}
}
