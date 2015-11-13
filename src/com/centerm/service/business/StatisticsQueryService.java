package com.centerm.service.business;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.BusinessStatisticsDao;
import com.centerm.entity.po.BusinessStatisticsPo;

@Service("statisticsQueryService")
public class StatisticsQueryService
{
	private static final Logger logger = LogManager.getLogger(StatisticsQueryService.class);
	
	@Resource(name = "businessStatisticsDao")
	private BusinessStatisticsDao businessStatisticsDao;
	
	/**
	 * 获得指定时间段内每天的业务量统计结果
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	统计结果列表，如果失败则为null
	 * @throws ParseException
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<BusinessStatisticsPo> getBusinessStatisticsEveryDay(String startDate, String endDate) throws ParseException
	{
		if ( startDate == null || endDate == null )
		{
			logger.debug("startDate或者endDate为null");
			return null;
		}
		
		Date startTime = DateUtils.parseDate(startDate, "yyyy-MM-dd");
		Date endTime = DateUtils.parseDate(endDate, "yyyy-MM-dd");
		if ( startTime.after(endTime) )
		{
			logger.debug("起始日期晚于结束日期");
			return null;
		}
		
		List<BusinessStatisticsPo> businessStatisticsPoList = businessStatisticsDao.queryBusinessStatistics(startDate, endDate);
		
		Date tmpTime = startTime;
		while ( !tmpTime.after(endTime) )
		{
			BusinessStatisticsPo tmpBusinessStatisticsPo = new BusinessStatisticsPo();
			tmpBusinessStatisticsPo.setDate(tmpTime);
			tmpBusinessStatisticsPo.setCount((long)0);
			
			boolean exist = false;	//是否存在相同记录
			for ( int i = 0; i < businessStatisticsPoList.size(); i++ )
			{
				BusinessStatisticsPo businessStatisticsPo = businessStatisticsPoList.get(i);
				Date time = businessStatisticsPo.getDate();
				if ( time.before(tmpTime) )
				{
					continue;
				}
				else if ( time.after(tmpTime) )
				{
					businessStatisticsPoList.add(i, tmpBusinessStatisticsPo);	//如果不存在当天的交易记录，则补充一条交易量为0的记录
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
				businessStatisticsPoList.add(tmpBusinessStatisticsPo);	//遍历到末尾仍未找到当天的交易记录，则在末尾补充一条交易量为0的记录
			}
			
			tmpTime = DateUtils.addDays(tmpTime, 1);	//日期增加一天
		}
		
		return businessStatisticsPoList;
	}
}
