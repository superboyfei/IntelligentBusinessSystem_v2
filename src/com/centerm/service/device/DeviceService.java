package com.centerm.service.device;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.DeviceDao;
import com.centerm.dao.DeviceStatusDao;
import com.centerm.entity.Device;

@Service("deviceService")
public class DeviceService
{
	private static final Logger logger = LogManager.getLogger(DeviceService.class);
	
	@Resource(name = "deviceDao")
	private DeviceDao deviceDao;
	@Resource(name = "deviceStatusDao")
	private DeviceStatusDao deviceStatusDao;
	
	/**
	 * 获得所有设备个数
	 * @return	设备个数，如果失败则为-1
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public int getDeviceCount()
	{
		Long count = deviceDao.getRecordCount();
		if ( count == null )
		{
			logger.debug("count为null");
			return -1;
		}
		
		return count.intValue();
	}
	
	/**
	 * 同过筛选条件分页查询设备记录
	 * @param outlet 网点
	 * @param deviceType 设备类型
	 * @param page	当前页
	 * @param rows	每页记录数
	 * @return	当前页的设备记录列表，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> getDeviceListByPage(String outlet, String deviceType, Integer page, Integer rows)
	{
		if ( StringUtils.isBlank(outlet) || StringUtils.isBlank(deviceType) )
		{
			logger.debug("outlet或者deviceType为空");
			return null;
		}
		if ( page == null || rows == null )
		{
			logger.debug("page或者rows为null");
			return null;
		}
		
		String[] outlets = outlet.split(",");
		String[] deviceTypes = deviceType.split(",");
		int limit = rows;
		int start = page * limit - limit;
		
		return deviceDao.queryDeviceByPage(outlets, deviceTypes, start, limit);
	}
	
	/**
	 * 通过筛选条件查询设备记录总数
	 * @param outlet	网点
	 * @param deviceType	设备类型
	 * @return	记录总数，如果失败则为-1
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public int getDeviceCount(String outlet, String deviceType)
	{
		if ( StringUtils.isBlank(outlet) || StringUtils.isBlank(deviceType) )
		{
			logger.debug("outlet或者deviceType为空");
			return -1;
		}
		
		String[] outlets = outlet.split(",");
		String[] deviceTypes = deviceType.split(",");
		
		return deviceDao.queryDeviceCount(outlets, deviceTypes);
	}
	
	
	
	/**
	 * 设备注册
	 * @param jsonObject	注册信息JSON
	 * @return
	 * true - 注册成功<br>
	 * false - 注册失败
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean DeviceRegister(JSONObject jsonObject)
	{
		Device device = (Device) JSONObject.toBean(jsonObject, Device.class);
		
		Integer id = (Integer)deviceDao.save(device);
		if( id == null )
		{
			logger.debug("id为null");
			return false;
		}
		
		return true;
	}
	
	@Transactional(readOnly = true,rollbackFor = Exception.class)
	public Device getDeviceBySerial(String serial)
	{
		Device device = deviceDao.getDeviceBySerial(serial);
		
		return device;
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public void updateDeviceFirmware(String firmwareIds)
	{
		List<Device> devices = deviceDao.findAll();
		for(Device device : devices)
		{
			StringBuffer buf = new StringBuffer();
			buf.append(firmwareIds).append(device.getFirmware());
			device.setFirmware(buf.toString());
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void updateDevice(Device device){
		deviceDao.update(device);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int shutDownDevice(List<String> serialList){
		if(serialList != null && serialList.size() > 0){
			//设置设备状态为0（下线）
			return deviceDao.shutDownDevice(serialList, getDeviceStatusIdByCode(0));
		}else{
			return 0;
		}
	}
	
	@Transactional(readOnly = true,rollbackFor = Exception.class)
	public int getDeviceStatusIdByCode(Integer code){
		return deviceStatusDao.getDeviceStatusByCode(code).getId();
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public void initDeviceStatus() {
		int onlineId = deviceStatusDao.getDeviceStatusByCode(1).getId();
		int downId = deviceStatusDao.getDeviceStatusByCode(0).getId();
		List<Device> allDevice = deviceDao.findAllOnlineDevice(onlineId);
		List<String> serialList = new ArrayList<String>();
		for(Device device : allDevice) {
			serialList.add(device.getSerial());
		}
		if(!serialList.isEmpty()) {
			deviceDao.shutDownDevice(serialList, downId);
		}
	}
}
