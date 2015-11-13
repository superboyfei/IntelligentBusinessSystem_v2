package com.centerm.service.device;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.AppDao;
import com.centerm.dao.DeviceDao;

@Service("appUpdateService")
public class AppUpdateService
{
	@Resource
	private DeviceDao deviceDao;
	@Resource
	private AppDao appDao;
	
	/**
	 * 获取网点设备，安装的app信息
	 * @param outlet 网点序列
	 * @return
	 * @author  liuyu
	 * @since  2015-5-27
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<String> getDeviceAppByOutlets(String outlet)
	{
		return deviceDao.getDeviceAppByOutlets(outlet);
	}

	/**
	 * 获取数据库中所有的应用信息
	 * @return
	 * @author  liuyu
	 * @since  2015-5-27
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> getAllAppInfo()
	{
		return appDao.findAllAppForLoad();
	}

	/**
	 * 为指定网点下的设备发布选择的应用
	 * @param outlet 指定的网点
	 * @param selectValue 选中的，需要对设备发布的应用的id序列
	 * @author  liuyu
	 * @since  2015-5-27
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void publishApp(String outlet, String selectValue)
	{
		deviceDao.publishApp(outlet, selectValue);
	}

}
