package com.centerm.service.manage.system;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.ServerinfoDao;
import com.centerm.entity.Serverinfo;

@Service("serverinfoService")
public class ServerinfoService
{
	private static final Logger logger = LogManager.getLogger(ServerinfoService.class);
	
	@Resource(name = "serverinfoDao")
	private ServerinfoDao serverinfoDao;
	
	/**
	 * 记录服务器启动时间
	 */
	@Transactional(rollbackFor = Exception.class)
	public void recordStartupTime()
	{
		List<Serverinfo> serverinfoList = serverinfoDao.findAll();
		if ( serverinfoList == null || serverinfoList.isEmpty() )	//没找到服务信息记录
		{
			logger.debug("服务信息列表为空，无法记录启动时间");	//不新建记录，防止统计出现空缺
			return;
		}
		
		Serverinfo serverinfo = serverinfoList.get(0);	//默认使用第一条记录
		Calendar currentCalendar = Calendar.getInstance();	//获得当前时间
		Date currentDate = currentCalendar.getTime();
		
		Date firstDate = serverinfo.getFirststartuptime();
		logger.debug("服务器第一次启动时间：" + firstDate.toString());
		if ( currentDate.before(firstDate) )	//如果启动时间早于服务器记录的第一次启动时间
		{
			serverinfo.setFirststartuptime(currentDate);	//保存服务器第一次启动时间
		}
		serverinfo.setLaststartuptime(currentDate);	//保存服务器最后一次启动时间
		serverinfoDao.update(serverinfo);
	}
}
