package com.centerm.service.business.hubei;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.hubei.OutletsReflectDao;
import com.centerm.entity.hubei.OutletsReflect;

@Service("outletsReflectService")
public class OutletsReflectService {
	
	private static final Logger logger = LogManager.getLogger(OutletsReflectService.class);
			
	@Resource
	private OutletsReflectDao outletsReflectDao;
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public OutletsReflect findByIp(String ip) {
		String ipnum = ip.substring(ip.trim().lastIndexOf(".")+1);
		int num = Integer.parseInt(ipnum.trim());
		return outletsReflectDao.findByIp(ip, num);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public OutletsReflect findByCode(String code) {
		return outletsReflectDao.findByCode(code);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> findAll() {
		return outletsReflectDao.findAll();
	}

	/**
	 * 分页查询
	 * @param currentPage
	 * @param recordNumInPage
	 * @return
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> getReflectByPage(Integer currentPage, Integer recordNumInPage) {
		if (currentPage == null || recordNumInPage == null ) {
			logger.debug("currentPage或者recordNumInPage为null");
			return null;
		}
		Integer start = currentPage * recordNumInPage - recordNumInPage;
		Integer limit = recordNumInPage;
		
		List<?> reflectList = outletsReflectDao.findByPage(start, limit);
		if ( reflectList == null )
		{
			logger.debug("userList为null");
		}
		
		return reflectList;
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public int getReflectCount() {
		Long count = outletsReflectDao.getRecordCount();
	
		if ( count == null )
		{
			logger.debug("count为null");
			return -1;
		}
	
		return count.intValue();
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Integer addReflect(String city, String outlets, String number,
			String startIP, String endIP, String submask) {
		if ( city == null || outlets == null || number == null || startIP == null
				|| endIP == null || submask == null)
		{
			logger.debug("city或者outlets或者number或者startIP或者endIP或者submask为null");
			return null;
		}
		
		String start = startIP.substring(startIP.trim().lastIndexOf(".")+1);
		int startnum = Integer.parseInt(start.trim());
		String end = endIP.substring(endIP.trim().lastIndexOf(".")+1);
		int endnum = Integer.parseInt(end.trim());
		
		OutletsReflect reflect = new OutletsReflect();
		reflect.setCity(city);
		reflect.setCode(number);
		reflect.setEnd(endIP);
		reflect.setStart(startIP);
		reflect.setSubmask(submask);
		reflect.setOutlets(outlets);
		reflect.setStartnum(startnum);
		reflect.setEndnum(endnum);
		
		Integer id = (Integer) outletsReflectDao.save(reflect);
		if ( id == null )
		{
			logger.debug("id为null");
			return null;
		}
		
		return id;
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean updateReflectById(String id, String city, String outlets,
			String number, String startIP, String endIP, String submask) {
		if ( id == null || city == null || outlets == null || number == null || startIP == null
				|| endIP == null || submask == null)
		{
			logger.debug("id或者city或者outlets或者number或者startIP或者endIP或者submask为null");
			return false;
		}
		
		OutletsReflect reflect = outletsReflectDao.findById(Integer.parseInt(id));
		if(reflect == null){
			logger.debug("网点IP映射记录不存在");
			return false;
		}
		
		String start = startIP.substring(startIP.trim().lastIndexOf(".")+1);
		int startnum = Integer.parseInt(start.trim());
		String end = endIP.substring(endIP.trim().lastIndexOf(".")+1);
		int endnum = Integer.parseInt(end.trim());
		
		reflect.setCity(city);
		reflect.setOutlets(outlets);
		reflect.setCode(number);
		reflect.setStart(startIP);
		reflect.setEnd(endIP);
		reflect.setSubmask(submask);
		reflect.setStartnum(startnum);
		reflect.setEndnum(endnum);
		
		outletsReflectDao.update(reflect);
		
		return true;
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean deleteReflect(Integer id) {
		if ( id == null )
		{
			logger.debug("id为null");
			return false;
		}
		
		OutletsReflect reflect = outletsReflectDao.findById(id);
		if ( reflect == null )
		{
			logger.debug("reflect为null");
			return false;
		}
		
		outletsReflectDao.delete(reflect);
		return true;
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean isOutletsExist(String city, String outlets)
	{
		if(city == null || outlets == null)
		{
			logger.debug("city或者outlets为null");
			return true;
		}
		List<?> reflectList = outletsReflectDao.findByOutlets(city, outlets);
		if(reflectList.isEmpty())
		{
			return false;
		} 
		return true;	
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean isCodeExist(String code)
	{
		if(code == null)
		{
			logger.debug("code为null");
			return true;
		}
		
		OutletsReflect reflect = outletsReflectDao.findByCode(code);
		if(reflect == null)
		{
			return false;
		} 
		return true;	
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean isIpUsable(String code, String start, String end)
	{
		if(start == null || end == null)
		{
			logger.debug("startIp或者endIp为null");
			return false;
		}
		
		String startIP = start.substring(start.trim().lastIndexOf(".")+1);
		int startnum = Integer.parseInt(startIP.trim());
		String endIP = end.substring(end.trim().lastIndexOf(".")+1);
		int endnum = Integer.parseInt(endIP.trim());
		
		OutletsReflect reflectStart = outletsReflectDao.findByIp(start, startnum);
		OutletsReflect reflectEnd = outletsReflectDao.findByIp(end, endnum);
		//非原来的网点
		if(code == null || code.isEmpty())
		{
			if(reflectStart != null || reflectEnd != null)
			{
				return false;
			}
		}
		else
		{
			if(reflectStart != null  && !reflectStart.getCode().equals(code))
			{
				return false;
			}
			if(reflectEnd != null && !reflectEnd.getCode().equals(code))
			{
				return false;
			}
		}
		
		List<?> reflectList = outletsReflectDao.findBetweenIps(start, end, startnum, endnum);
		if(!reflectList.isEmpty())
		{
			if(code == null || code.isEmpty())
			{
				return false;
			}
			else
			{
				if(reflectList.size() > 1)
				{
					return false;
				}
				else
				{
					OutletsReflect reflect = (OutletsReflect) reflectList.get(0);
					if(!reflect.getCode().equals(code))
					{
						return false;
					}
				}
			}
		}
		
		return true;
	}

}
