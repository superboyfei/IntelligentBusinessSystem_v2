package com.centerm.service.business;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.BusinessStatisticsDao;
import com.centerm.entity.po.QueryByTimePo;
import com.centerm.entity.po.QueryByTimeSubPo;
import com.centerm.service.manage.business.BusinessService;
import com.centerm.service.manage.outlets.OutletsService;

@Service("statisticsQueryByTimeService")
public class StatisticsQueryByTimeService
{
	private static final Logger logger = LogManager.getLogger(StatisticsQueryByTimeService.class);
	
	@Resource(name = "businessStatisticsDao")
	private BusinessStatisticsDao businessStatisticsDao;
	
	/**
	 * 获得满足指定的筛选条件按时间排列的当前页交易统计记录
	 * @param queryType	查询类型<br>
	 * 0 - 按日期查询<br>
	 * 1 - 按月查询<br>
	 * 2 - 按年查询<br>
	 * 3 - 按季度查询
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
	public List<QueryByTimePo> getBusinessStatisticsListByPage(String queryType, String bussType, String outlet, String startDate, String endDate, Integer page, Integer rows) throws ParseException
	{
		if ( StringUtils.isBlank(queryType) || StringUtils.isBlank(bussType) || StringUtils.isBlank(outlet) )
		{
			logger.debug("queryType或者bussType或者outlet为空");
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
		
		switch ( queryType )
		{
			case "0":
			{
				Date startTime = DateUtils.parseDate(startDate, "yyyy-MM-dd");
				startTime = DateUtils.addDays(startTime, start);	//获得当前页起始日期
				Date endTime = DateUtils.parseDate(startDate, "yyyy-MM-dd");
				endTime = DateUtils.addDays(endTime, start + limit - 1);	//获得当前页结束日期
				Date lastTime = DateUtils.parseDate(endDate, "yyyy-MM-dd");
				if ( endTime.after(lastTime) )	//如果endTime超过结束日期，则以结束日期作为endTime
				{
					endTime = lastTime;
				}
				
				startDate = DateFormatUtils.format(startTime, "yyyy-MM-dd");
				endDate = DateFormatUtils.format(endTime, "yyyy-MM-dd");
				List<QueryByTimePo> queryByTimePoList = businessStatisticsDao.queryBusinessStatisticsByDay(business, outlets, startDate, endDate);
				
				Date tmpTime = startTime;
				String tmpDate = DateFormatUtils.format(tmpTime, "yyyy-MM-dd");
				while ( !tmpTime.after(endTime) )
				{
					QueryByTimePo tmpQueryByTimePo = new QueryByTimePo();
					tmpQueryByTimePo.setDate(tmpDate);
					tmpQueryByTimePo.setCount((long)0);
					
					boolean exist = false;	//是否存在相同记录
					for ( int i = 0; i < queryByTimePoList.size(); i++ )
					{
						QueryByTimePo queryByTimePo = queryByTimePoList.get(i);
						String date = queryByTimePo.getDate();
						Date time = DateUtils.parseDate(date, "yyyy-MM-dd");
						if ( time.before(tmpTime) )
						{
							continue;
						}
						else if ( time.after(tmpTime) )
						{
							queryByTimePoList.add(i, tmpQueryByTimePo);	//如果不存在当天的交易记录，则补充一条交易量为0的记录
							exist = true;
							break;
						}
						else
						{
							exist = true;
							break;
						}
					}
					if ( exist == false )
					{
						queryByTimePoList.add(tmpQueryByTimePo);	//遍历到末尾仍未找到当天的交易记录，则在末尾补充一条交易量为0的记录
					}
					
					tmpTime = DateUtils.addDays(tmpTime, 1);	//日期增加一天
					tmpDate = DateFormatUtils.format(tmpTime, "yyyy-MM-dd");
				}
				
				return queryByTimePoList;
			}
			case "1":
			{
				Date startTime = DateUtils.parseDate(startDate + "-01", "yyyy-MM-dd");
				startTime = DateUtils.addMonths(startTime, start);	//获得当前页起始月
				Date lastTime = DateUtils.parseDate(endDate + "-01", "yyyy-MM-dd");
				lastTime = DateUtils.addMonths(lastTime, 1);
				
				List<QueryByTimePo> queryByTimePoList = new ArrayList<QueryByTimePo>();
				for ( int i = 0; i < limit; i++ )
				{
					startDate = DateFormatUtils.format(startTime, "yyyy-MM-dd");
					Date tmpTime = DateUtils.addMonths(startTime, 1);
					if ( tmpTime.after(lastTime) )	//如果endTime超过结束月，则以结束月作为endTime
					{
						break;	//已经到结束月，不需要继续循环查询
					}
					Date endTime = DateUtils.addDays(tmpTime, -1);	//减去一天，获得当前页结束月的最后一天
					endDate = DateFormatUtils.format(endTime, "yyyy-MM-dd");
					
					QueryByTimePo queryByTimePo = businessStatisticsDao.queryBusinessStatisticsByMonth(business, outlets, startDate, endDate);
					queryByTimePoList.add(queryByTimePo);
					
					startTime = DateUtils.addMonths(startTime, 1);
				}
				
				return queryByTimePoList;
			}
			case "2":
			{
				Date startTime = DateUtils.parseDate(startDate + "-01-01", "yyyy-MM-dd");
				startTime = DateUtils.addYears(startTime, start);	//获得当前页起始年
				Date lastTime = DateUtils.parseDate(endDate + "-01-01", "yyyy-MM-dd");
				lastTime = DateUtils.addYears(lastTime, 1);
				
				List<QueryByTimePo> queryByTimePoList = new ArrayList<QueryByTimePo>();
				for ( int i = 0; i < limit; i++ )
				{
					startDate = DateFormatUtils.format(startTime, "yyyy-MM-dd");
					Date tmpTime = DateUtils.addYears(startTime, 1);
					if ( tmpTime.after(lastTime) )	//如果endTime超过结束年，则以结束年作为endTime
					{
						break;	//已经到结束年，不需要继续循环查询
					}
					Date endTime = DateUtils.addDays(tmpTime, -1);	//减去一天，获得当前页结束年的最后一天
					endDate = DateFormatUtils.format(endTime, "yyyy-MM-dd");
					
					QueryByTimePo queryByTimePo = businessStatisticsDao.queryBusinessStatisticsByYear(business, outlets, startDate, endDate);
					queryByTimePoList.add(queryByTimePo);
					
					startTime = DateUtils.addYears(startTime, 1);
				}
				
				return queryByTimePoList;
			}
			case "3":
			{
				String startSeason = startDate.substring(5, 6);	//截取季度
				Date startTime;
				switch ( startSeason )
				{
					case "1":
					{
						startTime = DateUtils.parseDate(startDate.substring(0, 4) + "-01-01", "yyyy-MM-dd");
						break;
					}
					case "2":
					{
						startTime = DateUtils.parseDate(startDate.substring(0, 4) + "-04-01", "yyyy-MM-dd");
						break;
					}
					case "3":
					{
						startTime = DateUtils.parseDate(startDate.substring(0, 4) + "-07-01", "yyyy-MM-dd");
						break;
					}
					case "4":
					{
						startTime = DateUtils.parseDate(startDate.substring(0, 4) + "-10-01", "yyyy-MM-dd");
						break;
					}
					default:
					{
						logger.debug("startSeasion：" + startSeason + "——不合法");
						return null;
					}
				}
				startTime = DateUtils.addMonths(startTime, start * 3);	//获得当前页起始季度
				
				String endSeason = endDate.substring(5, 6);	//截取季度
				Date lastTime;
				switch ( endSeason )
				{
					case "1":
					{
						lastTime = DateUtils.parseDate(endDate.substring(0, 4) + "-01-01", "yyyy-MM-dd");
						break;
					}
					case "2":
					{
						lastTime = DateUtils.parseDate(endDate.substring(0, 4) + "-04-01", "yyyy-MM-dd");
						break;
					}
					case "3":
					{
						lastTime = DateUtils.parseDate(endDate.substring(0, 4) + "-07-01", "yyyy-MM-dd");
						break;
					}
					case "4":
					{
						lastTime = DateUtils.parseDate(endDate.substring(0, 4) + "-10-01", "yyyy-MM-dd");
						break;
					}
					default:
					{
						logger.debug("endSeasion：" + endSeason + "——不合法");
						return null;
					}
				}
				lastTime = DateUtils.addMonths(lastTime, 1 * 3);
				
				List<QueryByTimePo> queryByTimePoList = new ArrayList<QueryByTimePo>();
				for ( int i = 0; i < limit; i++ )
				{
					startDate = DateFormatUtils.format(startTime, "yyyy-MM-dd");
					Date tmpTime = DateUtils.addMonths(startTime, 1 * 3);
					if ( tmpTime.after(lastTime) )	//如果endTime超过结束季度，则以结束季度作为endTime
					{
						break;	//已经到结束季度，不需要继续循环查询
					}
					Date endTime = DateUtils.addDays(tmpTime, -1);	//减去一天，获得当前页结束季度的最后一天
					endDate = DateFormatUtils.format(endTime, "yyyy-MM-dd");
					
					QueryByTimePo queryByTimePo = businessStatisticsDao.queryBusinessStatisticsBySeason(business, outlets, startDate, endDate);
					queryByTimePoList.add(queryByTimePo);
					
					startTime = DateUtils.addMonths(startTime, 1 * 3);
				}
				
				return queryByTimePoList;
			}
			default:
			{
				logger.debug("queryType：" + queryType + "——不合法");
				return null;
			}
		}
	}
	
	/**
	 * 获得满足指定的筛选条件按时间排列的交易统计记录总数
	 * @param queryType	查询类型<br>
	 * 0 - 按日期查询<br>
	 * 1 - 按月查询<br>
	 * 2 - 按年查询<br>
	 * 3 - 按季度查询
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	记录总数，如果失败则为-1
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public int getBusinessStatisticsCount(String queryType, String startDate, String endDate) throws ParseException
	{
		if ( StringUtils.isBlank(queryType) )
		{
			logger.debug("queryType或者bussType或者outlet为空");
			return -1;
		}
		if ( startDate == null || endDate == null )
		{
			logger.debug("startDate或者endDate为null");
			return -1;
		}
		
		
		switch ( queryType )
		{
			case "0":
			{
				Date startTime = DateUtils.parseDate(startDate, "yyyy-MM-dd");
				Date endTime = DateUtils.parseDate(endDate, "yyyy-MM-dd");
				
				startTime = DateUtils.addDays(startTime, -1);	//后退一天，将起始日期也计算在内
				long startMs = startTime.getTime();
				long endMs = endTime.getTime();
				
				return (int)((endMs - startMs) / (24 * 60 * 60 * 1000));
			}
			case "1":
			{
				Date startTime = DateUtils.parseDate(startDate + "-01", "yyyy-MM-dd");
				Date endTime = DateUtils.parseDate(endDate + "-01", "yyyy-MM-dd");
				
				int count = 1;
				while ( endTime.after(startTime) )
				{
					count++;
					startTime = DateUtils.addMonths(startTime, 1);
				}
				
				return count;
			}
			case "2":
			{
				Date startTime = DateUtils.parseDate(startDate + "-01-01", "yyyy-MM-dd");
				Date endTime = DateUtils.parseDate(endDate + "-01-01", "yyyy-MM-dd");
				
				int count = 1;
				while ( endTime.after(startTime) )
				{
					count++;
					startTime = DateUtils.addYears(startTime, 1);
				}
				
				return count;
			}
			case "3":
			{
				String startSeason = startDate.substring(5, 6);	//截取季度
				Date startTime;
				switch ( startSeason )
				{
					case "1":
					{
						startTime = DateUtils.parseDate(startDate.substring(0, 4) + "-01-01", "yyyy-MM-dd");
						break;
					}
					case "2":
					{
						startTime = DateUtils.parseDate(startDate.substring(0, 4) + "-04-01", "yyyy-MM-dd");
						break;
					}
					case "3":
					{
						startTime = DateUtils.parseDate(startDate.substring(0, 4) + "-07-01", "yyyy-MM-dd");
						break;
					}
					case "4":
					{
						startTime = DateUtils.parseDate(startDate.substring(0, 4) + "-10-01", "yyyy-MM-dd");
						break;
					}
					default:
					{
						logger.debug("startSeasion：" + startSeason + "——不合法");
						return -1;
					}
				}
				
				String endSeason = endDate.substring(5, 6);	//截取季度
				Date endTime;
				switch ( endSeason )
				{
					case "1":
					{
						endTime = DateUtils.parseDate(endDate.substring(0, 4) + "-01-01", "yyyy-MM-dd");
						break;
					}
					case "2":
					{
						endTime = DateUtils.parseDate(endDate.substring(0, 4) + "-04-01", "yyyy-MM-dd");
						break;
					}
					case "3":
					{
						endTime = DateUtils.parseDate(endDate.substring(0, 4) + "-07-01", "yyyy-MM-dd");
						break;
					}
					case "4":
					{
						endTime = DateUtils.parseDate(endDate.substring(0, 4) + "-10-01", "yyyy-MM-dd");
						break;
					}
					default:
					{
						logger.debug("endSeasion：" + endSeason + "——不合法");
						return -1;
					}
				}
				
				int count = 1;
				while ( endTime.after(startTime) )
				{
					count++;
					startTime = DateUtils.addMonths(startTime, 1 * 3);
				}
				
				return count;
			}
			default:
			{
				logger.debug("queryType：" + queryType + "——不合法");
				return -1;
			}
		}
	}
	
	/**
	 * 获得交易时间内满足指定的筛选条件的当前页交易统计记录
	 * @param bussType	交易类别
	 * @param outlet	网点
	 * @param page	当前页
	 * @param rows	每页记录数
	 * @param time	交易时间
	 * @return	当前页的交易统计记录列表，如果失败则为null
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<QueryByTimeSubPo> getSubBusinessStatisticsListByPage(String bussType, String outlet, Integer page, Integer rows, String time) throws ParseException
	{
		if ( StringUtils.isBlank(bussType) || StringUtils.isBlank(outlet) )
		{
			logger.debug("bussType或者outlet为空");
			return null;
		}
		if ( page == null || rows == null || time == null )
		{
			logger.debug("page或者rows或者time为null");
			return null;
		}
		
		String[] business = bussType.split(",");
		String[] outlets = outlet.split(",");
		int limit = rows;
		int start = page * limit - limit;
		
		String startDate;
		String endDate;
		switch ( time.length() )
		{
			case 10:	//按日期查询
			{
				startDate = time;
				endDate = time;
				break;
			}
			case 7:	//按月查询
			{
				startDate = time + "-01";
				Date startTime = DateUtils.parseDate(startDate, "yyyy-MM-dd");
				startTime = DateUtils.addMonths(startTime, 1);
				startTime = DateUtils.addDays(startTime, -1);	//获得月末日期
				endDate = DateFormatUtils.format(startTime, "yyyy-MM-dd");
				break;
			}
			case 4:	//按年查询
			{
				startDate = time + "-01-01";
				endDate = time + "-12-31";
				break;
			}
			case 9:	//按季度查询
			{
				String year = time.substring(0, 4);
				String season = time.substring(6, 7);
				switch ( season )
				{
					case "1":
					{
						startDate = year + "-01-01";
						endDate = year + "-03-31";
						break;
					}
					case "2":
					{
						startDate = year + "-04-01";
						endDate = year + "-06-30";
						break;
					}
					case "3":
					{
						startDate = year + "-07-01";
						endDate = year + "-09-30";
						break;
					}
					case "4":
					{
						startDate = year + "-10-01";
						endDate = year + "-12-31";
						break;
					}
					default:
					{
						logger.debug("time：" + time + "——不合法");
						return null;
					}
				}
				break;
			}
			default:
			{
				logger.debug("time：" + time + "——不合法");
				return null;
			}
		}
		
		List<QueryByTimeSubPo> queryByTimeSubPoList = businessStatisticsDao.querySubBusinessStaisticsByTime(business, outlets, startDate, endDate, start, limit);
		
		for( QueryByTimeSubPo queryByTimeSubPo : queryByTimeSubPoList )
		{
			queryByTimeSubPo.setBusinessName(BusinessService.businessMap.get(queryByTimeSubPo.getBusiness()));
			queryByTimeSubPo.setOutletName(OutletsService.outletsMap.get(queryByTimeSubPo.getOutlets()));
		}
		
		return queryByTimeSubPoList;
	}
	
	/**
	 * 获得交易时间内满足指定的筛选条件的交易统计记录总数
	 * @param bussType	业务类别
	 * @param outlet	网点
	 * @param time	交易时间
	 * @return	记录总数，如果失败则为-1
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public int getSubBusinessStatisticsCount(String bussType, String outlet, String time) throws ParseException
	{
		if ( StringUtils.isBlank(bussType) || StringUtils.isBlank(outlet) )
		{
			logger.debug("bussType或者outlet为空");
			return -1;
		}
		if ( time == null )
		{
			logger.debug("time为null");
			return -1;
		}
		
		String[] business = bussType.split(",");
		String[] outlets = outlet.split(",");
		
		String startDate;
		String endDate;
		switch ( time.length() )
		{
			case 10:	//按日期查询
			{
				startDate = time;
				endDate = time;
				break;
			}
			case 7:	//按月查询
			{
				startDate = time + "-01";
				Date startTime = DateUtils.parseDate(startDate, "yyyy-MM-dd");
				startTime = DateUtils.addMonths(startTime, 1);
				startTime = DateUtils.addDays(startTime, -1);	//获得月末日期
				endDate = DateFormatUtils.format(startTime, "yyyy-MM-dd");
				break;
			}
			case 4:	//按年查询
			{
				startDate = time + "-01-01";
				endDate = time + "-12-31";
				break;
			}
			case 9:	//按季度查询
			{
				String year = time.substring(0, 4);
				String season = time.substring(6, 7);
				switch ( season )
				{
					case "1":
					{
						startDate = year + "-01-01";
						endDate = year + "-03-31";
						break;
					}
					case "2":
					{
						startDate = year + "-04-01";
						endDate = year + "-06-30";
						break;
					}
					case "3":
					{
						startDate = year + "-07-01";
						endDate = year + "-09-30";
						break;
					}
					case "4":
					{
						startDate = year + "-10-01";
						endDate = year + "-12-31";
						break;
					}
					default:
					{
						logger.debug("time：" + time + "——不合法");
						return -1;
					}
				}
				break;
			}
			default:
			{
				logger.debug("time：" + time + "——不合法");
				return -1;
			}
		}
		
		return businessStatisticsDao.querySubBusinessStaisticsCountByTime(business, outlets, startDate, endDate).intValue();
	}
	
	/**
	 * 获得满足指定的筛选条件按时间排列的交易统计记录
	 * @param queryType	查询类型<br>
	 * 0 - 按日期查询<br>
	 * 1 - 按月查询<br>
	 * 2 - 按年查询<br>
	 * 3 - 按季度查询
	 * @param bussType	业务类别
	 * @param outlet	网点
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	交易统计记录列表，如果失败则为null
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<QueryByTimePo> getBusinessStatisticsList(String queryType, String bussType, String outlet, String startDate, String endDate) throws ParseException
	{
		if ( StringUtils.isBlank(queryType) || StringUtils.isBlank(bussType) || StringUtils.isBlank(outlet) )
		{
			logger.debug("queryType或者 bussType或者outlet为空");
			return null;
		}
		if ( startDate == null || endDate == null )
		{
			logger.debug("startDate或者endDate为null");
			return null;
		}
		
		String[] business = bussType.split(",");
		String[] outlets = outlet.split(",");
		
		List<QueryByTimePo> queryByTimePoList;
		switch ( queryType )
		{
			case "0":
			{
				Date startTime = DateUtils.parseDate(startDate, "yyyy-MM-dd");
				Date endTime = DateUtils.parseDate(endDate, "yyyy-MM-dd");
				queryByTimePoList = businessStatisticsDao.queryBusinessStatisticsByDay(business, outlets, startDate, endDate);
				
				Date tmpTime = startTime;
				String tmpDate = DateFormatUtils.format(tmpTime, "yyyy-MM-dd");
				while ( !tmpTime.after(endTime) )
				{
					QueryByTimePo tmpQueryByTimePo = new QueryByTimePo();
					tmpQueryByTimePo.setDate(tmpDate);
					tmpQueryByTimePo.setCount((long)0);
					
					boolean exist = false;	//是否存在相同记录
					for ( int i = 0; i < queryByTimePoList.size(); i++ )
					{
						QueryByTimePo queryByTimePo = queryByTimePoList.get(i);
						String date = queryByTimePo.getDate();
						Date time = DateUtils.parseDate(date, "yyyy-MM-dd");
						if ( time.before(tmpTime) )
						{
							continue;
						}
						else if ( time.after(tmpTime) )
						{
							queryByTimePoList.add(i, tmpQueryByTimePo);	//如果不存在当天的交易记录，则补充一条交易量为0的记录
							exist = true;
							break;
						}
						else
						{
							exist = true;
							break;
						}
					}
					if ( exist == false )
					{
						queryByTimePoList.add(tmpQueryByTimePo);	//遍历到末尾仍未找到当天的交易记录，则在末尾补充一条交易量为0的记录
					}
					
					tmpTime = DateUtils.addDays(tmpTime, 1);	//日期增加一天
					tmpDate = DateFormatUtils.format(tmpTime, "yyyy-MM-dd");
				}
				
				break;
			}
			case "1":
			{
				int count = 1;
				Date countStartTime = DateUtils.parseDate(startDate + "-01", "yyyy-MM-dd");
				Date countEndTime = DateUtils.parseDate(endDate + "-01", "yyyy-MM-dd");
				while ( countEndTime.after(countStartTime) )
				{
					count++;
					countStartTime = DateUtils.addMonths(countStartTime, 1);
				}
				
				Date startTime = DateUtils.parseDate(startDate + "-01", "yyyy-MM-dd");
				Date lastTime = DateUtils.parseDate(endDate + "-01", "yyyy-MM-dd");
				lastTime = DateUtils.addMonths(lastTime, 1);
				
				queryByTimePoList = new ArrayList<QueryByTimePo>();
				for ( int i = 0; i < count; i++ )
				{
					startDate = DateFormatUtils.format(startTime, "yyyy-MM-dd");
					Date tmpTime = DateUtils.addMonths(startTime, 1);
					if ( tmpTime.after(lastTime) )	//如果endTime超过结束月，则以结束月作为endTime
					{
						break;	//已经到结束月，不需要继续循环查询
					}
					Date endTime = DateUtils.addDays(tmpTime, -1);	//减去一天，获得当前页结束月的最后一天
					endDate = DateFormatUtils.format(endTime, "yyyy-MM-dd");
					
					QueryByTimePo queryByTimePo = businessStatisticsDao.queryBusinessStatisticsByMonth(business, outlets, startDate, endDate);
					queryByTimePoList.add(queryByTimePo);
					
					startTime = DateUtils.addMonths(startTime, 1);
				}
				
				break;
			}
			case "2":
			{
				int count = 1;
				Date countStartTime = DateUtils.parseDate(startDate + "-01-01", "yyyy-MM-dd");
				Date countEndTime = DateUtils.parseDate(endDate + "-01-01", "yyyy-MM-dd");
				while ( countEndTime.after(countStartTime) )
				{
					count++;
					countStartTime = DateUtils.addYears(countStartTime, 1);
				}
				
				Date startTime = DateUtils.parseDate(startDate + "-01-01", "yyyy-MM-dd");
				Date lastTime = DateUtils.parseDate(endDate + "-01-01", "yyyy-MM-dd");
				lastTime = DateUtils.addYears(lastTime, 1);
				
				queryByTimePoList = new ArrayList<QueryByTimePo>();
				for ( int i = 0; i < count; i++ )
				{
					startDate = DateFormatUtils.format(startTime, "yyyy-MM-dd");
					Date tmpTime = DateUtils.addYears(startTime, 1);
					if ( tmpTime.after(lastTime) )	//如果endTime超过结束年，则以结束年作为endTime
					{
						break;	//已经到结束年，不需要继续循环查询
					}
					Date endTime = DateUtils.addDays(tmpTime, -1);	//减去一天，获得当前页结束年的最后一天
					endDate = DateFormatUtils.format(endTime, "yyyy-MM-dd");
					
					QueryByTimePo queryByTimePo = businessStatisticsDao.queryBusinessStatisticsByYear(business, outlets, startDate, endDate);
					queryByTimePoList.add(queryByTimePo);
					
					startTime = DateUtils.addYears(startTime, 1);
				}
				
				break;
			}
			case "3":
			{
				String countStartSeason = startDate.substring(5, 6);	//截取季度
				Date countStartTime;
				switch ( countStartSeason )
				{
					case "1":
					{
						countStartTime = DateUtils.parseDate(startDate.substring(0, 4) + "-01-01", "yyyy-MM-dd");
						break;
					}
					case "2":
					{
						countStartTime = DateUtils.parseDate(startDate.substring(0, 4) + "-04-01", "yyyy-MM-dd");
						break;
					}
					case "3":
					{
						countStartTime = DateUtils.parseDate(startDate.substring(0, 4) + "-07-01", "yyyy-MM-dd");
						break;
					}
					case "4":
					{
						countStartTime = DateUtils.parseDate(startDate.substring(0, 4) + "-10-01", "yyyy-MM-dd");
						break;
					}
					default:
					{
						logger.debug("countStartSeasion：" + countStartSeason + "——不合法");
						return null;
					}
				}
				
				String countEndSeason = endDate.substring(5, 6);	//截取季度
				Date countEndTime;
				switch ( countEndSeason )
				{
					case "1":
					{
						countEndTime = DateUtils.parseDate(endDate.substring(0, 4) + "-01-01", "yyyy-MM-dd");
						break;
					}
					case "2":
					{
						countEndTime = DateUtils.parseDate(endDate.substring(0, 4) + "-04-01", "yyyy-MM-dd");
						break;
					}
					case "3":
					{
						countEndTime = DateUtils.parseDate(endDate.substring(0, 4) + "-07-01", "yyyy-MM-dd");
						break;
					}
					case "4":
					{
						countEndTime = DateUtils.parseDate(endDate.substring(0, 4) + "-10-01", "yyyy-MM-dd");
						break;
					}
					default:
					{
						logger.debug("countEndSeasion：" + countEndSeason + "——不合法");
						return null;
					}
				}
				
				int count = 1;
				while ( countEndTime.after(countStartTime) )
				{
					count++;
					countStartTime = DateUtils.addMonths(countStartTime, 1 * 3);
				}
				
				String startSeason = startDate.substring(5, 6);	//截取季度
				Date startTime;
				switch ( startSeason )
				{
					case "1":
					{
						startTime = DateUtils.parseDate(startDate.substring(0, 4) + "-01-01", "yyyy-MM-dd");
						break;
					}
					case "2":
					{
						startTime = DateUtils.parseDate(startDate.substring(0, 4) + "-04-01", "yyyy-MM-dd");
						break;
					}
					case "3":
					{
						startTime = DateUtils.parseDate(startDate.substring(0, 4) + "-07-01", "yyyy-MM-dd");
						break;
					}
					case "4":
					{
						startTime = DateUtils.parseDate(startDate.substring(0, 4) + "-10-01", "yyyy-MM-dd");
						break;
					}
					default:
					{
						logger.debug("startSeasion：" + startSeason + "——不合法");
						return null;
					}
				}
				
				String endSeason = endDate.substring(5, 6);	//截取季度
				Date lastTime;
				switch ( endSeason )
				{
					case "1":
					{
						lastTime = DateUtils.parseDate(endDate.substring(0, 4) + "-01-01", "yyyy-MM-dd");
						break;
					}
					case "2":
					{
						lastTime = DateUtils.parseDate(endDate.substring(0, 4) + "-04-01", "yyyy-MM-dd");
						break;
					}
					case "3":
					{
						lastTime = DateUtils.parseDate(endDate.substring(0, 4) + "-07-01", "yyyy-MM-dd");
						break;
					}
					case "4":
					{
						lastTime = DateUtils.parseDate(endDate.substring(0, 4) + "-10-01", "yyyy-MM-dd");
						break;
					}
					default:
					{
						logger.debug("endSeasion：" + endSeason + "——不合法");
						return null;
					}
				}
				lastTime = DateUtils.addMonths(lastTime, 1 * 3);
				
				queryByTimePoList = new ArrayList<QueryByTimePo>();
				for ( int i = 0; i < count; i++ )
				{
					startDate = DateFormatUtils.format(startTime, "yyyy-MM-dd");
					Date tmpTime = DateUtils.addMonths(startTime, 1 * 3);
					if ( tmpTime.after(lastTime) )	//如果endTime超过结束季度，则以结束季度作为endTime
					{
						break;	//已经到结束季度，不需要继续循环查询
					}
					Date endTime = DateUtils.addDays(tmpTime, -1);	//减去一天，获得当前页结束季度的最后一天
					endDate = DateFormatUtils.format(endTime, "yyyy-MM-dd");
					
					QueryByTimePo queryByTimePo = businessStatisticsDao.queryBusinessStatisticsBySeason(business, outlets, startDate, endDate);
					queryByTimePoList.add(queryByTimePo);
					
					startTime = DateUtils.addMonths(startTime, 1 * 3);
				}
				
				break;
			}
			default:
			{
				logger.debug("queryType：" + queryType + "——不合法");
				return null;
			}
		}
		
		return queryByTimePoList;
	}
}
