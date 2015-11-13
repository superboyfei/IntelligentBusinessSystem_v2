package com.centerm.service.business;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.BusinessStatisticsDao;
import com.centerm.entity.po.QueryByOutletsPo;
import com.centerm.entity.po.QueryByOutletsSubPo;
import com.centerm.service.manage.business.BusinessService;
import com.centerm.service.manage.outlets.OutletsService;

@Service("statisticsQueryByOutletsService")
public class StatisticsQueryByOutletsService
{
	private static final Logger logger = LogManager.getLogger(StatisticsQueryByOutletsService.class);
	
	@Resource(name = "businessStatisticsDao")
	private BusinessStatisticsDao businessStatisticsDao;
	
	/**
	 * 获得满足指定的筛选条件按网点排列的当前页交易统计记录
	 * @param bussType	业务类别
	 * @param outlet	网点
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @param page	当前页
	 * @param rows	每页记录数
	 * @return	当前页的交易统计记录列表，如果失败则为null
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<QueryByOutletsPo> getBusinessStatisticsListByPage(String bussType, String outlet, String startDate, String endDate, Integer page, Integer rows) throws ParseException
	{
		if ( StringUtils.isBlank(bussType) || StringUtils.isBlank(outlet) )
		{
			logger.debug("bussType或者outlet为空");
			return null;
		}
		if ( startDate == null || endDate == null )
		{
			logger.debug("startDate或者endDate为null");
			return null;
		}
		if ( page == null || rows == null )
		{
			logger.debug("page或者rows为null");
			return null;
		}
		
		String[] business = bussType.split(",");
		String[] outlets = outlet.split(",");
		int limit = rows;
		int start = page * limit - limit;
		
		List<QueryByOutletsPo> queryByOutletsPoList = new ArrayList<QueryByOutletsPo>();
		for ( int i = start; i < start + limit; i++ )
		{
			if ( i >= outlets.length )	//已经超过业务总数，不需要再继续查询
			{
				break;
			}
			
			QueryByOutletsPo queryByOutletsPo = businessStatisticsDao.queryBusinessStatisticsByOutlets(business, outlets[i], startDate, endDate);
			queryByOutletsPoList.add(queryByOutletsPo);
		}
		
		for ( QueryByOutletsPo queryByOutletsPo : queryByOutletsPoList )
		{
			queryByOutletsPo.setOutletsName(OutletsService.outletsMap.get(queryByOutletsPo.getOutlets()));
		}
		
		return queryByOutletsPoList;
	}
	
	/**
	 * 获得满足指定的筛选条件按网点排列的交易统计记录总数
	 * @param outlet	网点
	 * @return	记录总数，如果失败则为-1
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public int getBusinessStatisticsCount(String outlet)
	{
		if ( StringUtils.isBlank(outlet) )
		{
			logger.debug("outlet为空");
			return -1;
		}
		
		String[] outlets = outlet.split(",");
		return outlets.length;
	}
	
	/**
	 * 通过筛选条件按网点分页查询交易统计子记录
	 * @param bussType	业务类别
	 * @param outlet	网点
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @param page	当前页
	 * @param rows	每页记录数
	 * @return	当前页的交易统计子记录表，如果失败则为null
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<QueryByOutletsSubPo> getSubBusinessListByPage(String bussType, String outlet, String startDate, String endDate, Integer page, Integer rows) throws ParseException
	{
		if ( StringUtils.isBlank(bussType) || StringUtils.isBlank(outlet) )
		{
			logger.debug("bussType或者outlet为空");
			return null;
		}
		if ( startDate == null || endDate == null )
		{
			logger.debug("startDate或者endDate为null");
			return null;
		}
		if ( page == null || rows == null )
		{
			logger.debug("page或者rows或者time为null");
			return null;
		}
		
		String[] bussTypes = bussType.split(",");
		int limit = rows;
		int start = page * limit - limit;
		
		List<QueryByOutletsSubPo> queryByOutletsSubPoList = businessStatisticsDao.querySubBusinessStatisticsByOutlets(bussTypes, outlet, startDate, endDate, start, limit);
		for(QueryByOutletsSubPo queryByOutletsSubPo : queryByOutletsSubPoList)
		{
			queryByOutletsSubPo.setBusinessName(BusinessService.businessMap.get(queryByOutletsSubPo.getBusiness()));
		}
		
		return queryByOutletsSubPoList;
	}
	
	/**
	 * 通过筛选条件按网点查询交易统计子记录总数
	 * @param bussType	业务类别
	 * @param outlet	网点
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	记录总数，如果失败则为-1
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public int getSubBusinessStatisticsCount(String bussType, String outlet, String startDate, String endDate) throws ParseException
	{
		if ( StringUtils.isBlank(bussType) || StringUtils.isBlank(outlet) )
		{
			logger.debug("bussType或者outlet为空");
			return -1;
		}
		if ( startDate == null || endDate == null )
		{
			logger.debug("startDate或者endDate为null");
			return -1;
		}
		
		String[] bussTypes = bussType.split(",");
		
		return businessStatisticsDao.querySubBusinessStatisticsCountByOutlets(bussTypes, outlet, startDate, endDate).intValue();
	}
	
	/**
	 * 获得满足指定的筛选条件按网点排列的交易统计记录
	 * @param bussType	业务类别
	 * @param outlet	网点
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	交易统计记录列表，如果失败则为null
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<QueryByOutletsPo> getBusinessStatisticsList(String bussType, String outlet, String startDate, String endDate) throws ParseException
	{
		if ( StringUtils.isBlank(bussType) || StringUtils.isBlank(outlet) )
		{
			logger.debug("bussType或者outlet为空");
			return null;
		}
		if ( startDate == null || endDate == null )
		{
			logger.debug("startDate或者endDate为null");
			return null;
		}
		
		String[] business = bussType.split(",");
		String[] outlets = outlet.split(",");
		
		List<QueryByOutletsPo> queryByOutletsPoList = new ArrayList<QueryByOutletsPo>();
		for ( int i = 0; i < outlets.length; i++ )
		{
			QueryByOutletsPo queryByOutletsPo = businessStatisticsDao.queryBusinessStatisticsByOutlets(business, outlets[i], startDate, endDate);
			queryByOutletsPoList.add(queryByOutletsPo);
		}
		
		for ( QueryByOutletsPo queryByOutletsPo : queryByOutletsPoList )
		{
			queryByOutletsPo.setOutletsName(OutletsService.outletsMap.get(queryByOutletsPo.getOutlets()));
		}
		
		return queryByOutletsPoList;
	}
}
