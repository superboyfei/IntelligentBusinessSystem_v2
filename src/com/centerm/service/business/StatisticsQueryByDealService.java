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
import com.centerm.entity.po.QueryByDealPo;
import com.centerm.entity.po.QueryByDealSubPo;
import com.centerm.service.manage.business.BusinessService;
import com.centerm.service.manage.outlets.OutletsService;

@Service("statisticsQueryByDealService")
public class StatisticsQueryByDealService
{
	private static final Logger logger = LogManager.getLogger(StatisticsQueryByDealService.class);
	
	@Resource(name = "businessStatisticsDao")
	private BusinessStatisticsDao businessStatisticsDao;
	
	/**
	 * 获得满足指定的筛选条件按业务类型排列的当前页交易统计记录
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
	public List<QueryByDealPo> getBusinessStatisticsListByPage(String bussType, String outlet, String startDate, String endDate, Integer page, Integer rows) throws ParseException
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
		
		List<QueryByDealPo> queryByDealPoList = new ArrayList<QueryByDealPo>();
		for ( int i = start; i < start + limit; i++ )
		{
			if ( i >= business.length )	//已经超过网点总数，不需要再继续查询
			{
				break;
			}
			
			QueryByDealPo queryByDealPo = businessStatisticsDao.queryBusinessStatisticsByDeal(business[i], outlets, startDate, endDate);
			queryByDealPoList.add(queryByDealPo);
		}
		
		for ( QueryByDealPo queryByDealPo : queryByDealPoList )
		{
			queryByDealPo.setBusinessName(BusinessService.businessMap.get(queryByDealPo.getBusiness()));
		}
		
		return queryByDealPoList;
	}
	
	/**
	 * 获得满足指定的筛选条件按业务类型排列的交易统计记录总数
	 * @param bussType	业务类别
	 * @return	记录总数，如果失败则为-1
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public int getBusinessStatisticsCount(String bussType)
	{
		if ( StringUtils.isBlank(bussType) )
		{
			logger.debug("bussType为空");
			return -1;
		}
		
		String[] business = bussType.split(",");
		return business.length;
	}
	
	/**
	 * 通过筛选条件按业务类别分页查询交易统计子记录
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
	public List<QueryByDealSubPo> getSubBusinessListByPage(String bussType, String outlet, String startDate, String endDate, Integer page, Integer rows) throws ParseException
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
		
		String[] outlets = outlet.split(",");
		int limit = rows;
		int start = page * limit - limit;
		
		List<QueryByDealSubPo> queryByDealSubPoList = businessStatisticsDao.querySubBusinessStatisticsByDeal(bussType, outlets, startDate, endDate, start, limit);
		for(QueryByDealSubPo queryByDealSubPo : queryByDealSubPoList)
		{
			queryByDealSubPo.setOutletsName(OutletsService.outletsMap.get(queryByDealSubPo.getOutlets()));
		}
		
		return queryByDealSubPoList;
	}
	
	/**
	 * 通过筛选条件按业务类别查询交易统计子记录总数
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
		
		String[] outlets = outlet.split(",");
		
		return businessStatisticsDao.querySubBusinessStatisticsCountByDeal(bussType, outlets, startDate, endDate).intValue();
	}
	
	/**
	 * 获得满足指定的筛选条件按业务类型排列的交易统计记录
	 * @param bussType	业务类别
	 * @param outlet	网点
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	交易统计记录列表，如果失败则为null
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<QueryByDealPo> getBusinessStatisticsList(String bussType, String outlet, String startDate, String endDate) throws ParseException
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
		
		List<QueryByDealPo> queryByDealPoList = new ArrayList<QueryByDealPo>();
		for ( int i = 0; i < business.length; i++ )
		{
			QueryByDealPo queryByDealPo = businessStatisticsDao.queryBusinessStatisticsByDeal(business[i], outlets, startDate, endDate);
			queryByDealPoList.add(queryByDealPo);
		}
		
		for ( QueryByDealPo queryByDealPo : queryByDealPoList )
		{
			queryByDealPo.setBusinessName(BusinessService.businessMap.get(queryByDealPo.getBusiness()));
		}
		
		return queryByDealPoList;
	}
}
