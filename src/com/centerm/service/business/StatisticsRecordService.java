package com.centerm.service.business;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.BusinessRecordDao;
import com.centerm.dao.BusinessStatisticsDao;
import com.centerm.dao.ServerinfoDao;
import com.centerm.entity.BusinessRecord;
import com.centerm.entity.Serverinfo;

@Service("statisticsRecordService")
public class StatisticsRecordService 
{
	private static final Logger logger = LogManager.getLogger(StatisticsRecordService.class);
	
	@Resource(name = "serverinfoDao")
	private ServerinfoDao serverinfoDao;
	@Resource(name = "businessRecordDao")
	private BusinessRecordDao businessRecordDao;
	@Resource(name = "businessStatisticsDao")
	private BusinessStatisticsDao businessStatisticsDao;
	
	/**
	 * 统计业务记录
	 */
	@Transactional(rollbackFor = Exception.class)
	public void statisticsBusinessRecord()
	{
		List<Serverinfo> serverinfoList = serverinfoDao.findAll();
		if ( serverinfoList == null || serverinfoList.isEmpty() )	//没找到服务信息记录
		{
			logger.debug("服务信息列表为空，无法进行统计");	//不新建记录，防止统计出现空缺
			return;
		}
		
		Serverinfo serverinfo = serverinfoList.get(0);	//默认取第一条记录
		Date lastStatisticsTime = serverinfo.getLaststatisticstime();	//获取上一次统计结束时间
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);	//回到前一天
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date nowStatisticsTime = calendar.getTime();	//得到本次统计结束时间
		
		String startTime = DateFormatUtils.format(lastStatisticsTime, "yyyy-MM-dd HH:mm:ss");
		logger.debug("统计开始时间：" + startTime);
		String endTime = DateFormatUtils.format(nowStatisticsTime, "yyyy-MM-dd HH:mm:ss");
		logger.debug("统计结束时间：" + endTime);
		
		if ( lastStatisticsTime.after(nowStatisticsTime) )	//最后统计时间晚于当前时间，则不进行统计
		{
			logger.debug("最后统计时间晚于当前时间，不进行统计");
			return;
		}
		
		List<BusinessRecord> businessRecordList = businessRecordDao.queryBusinessRecordByTime(startTime, endTime);
		
		Map<String, Map<String, Long>> statisticsDateMap = new HashMap<String, Map<String, Long>>();	//按日期区分统计数据
		for ( BusinessRecord businessRecord : businessRecordList )
		{
			Date businessTime = businessRecord.getTime();
			String businessDate = DateFormatUtils.format(businessTime, "yyyy-MM-dd");	//保留日期信息
			String businessInfo = businessRecord.getOutlets() + "#" + businessRecord.getBusiness() + "#" + businessRecord.getDevice();	//将业务信息组合
			
			Set<String> dateSet = statisticsDateMap.keySet();	//获得已保存记录的日期集合
			Map<String, Long> statisticsMap;
			if ( dateSet.contains(businessDate) )	//已包含该日期的记录
			{
				statisticsMap = statisticsDateMap.get(businessDate);	//取出该日期对应的记录Map
				Set<String> infoSet = statisticsMap.keySet();	//获得已保存记录的业务信息集合
				if ( infoSet.contains(businessInfo) )	//已包含该业务信息的记录
				{
					Long count = statisticsMap.get(businessInfo);
					count++;	//业务数量加一
					statisticsMap.put(businessInfo, count);
				}
				else	//未包含该业务信息的记录
				{
					statisticsMap.put(businessInfo, (long) 1);
				}
			}
			else	//未包含该日期的记录
			{
				statisticsMap = new HashMap<String, Long>();
				statisticsMap.put(businessInfo, (long) 1);
			}
			
			statisticsDateMap.put(businessDate, statisticsMap);
		}
		
		Set<String> dateSet = statisticsDateMap.keySet();
		Iterator<String> dateIt = dateSet.iterator();
		while ( dateIt.hasNext() )
		{
			String date = dateIt.next();
			Map<String, Long> statisticsMap = statisticsDateMap.get(date);
			Set<String> infoSet = statisticsMap.keySet();
			Iterator<String> infoIt = infoSet.iterator();
			while ( infoIt.hasNext() )
			{
				String info = infoIt.next();
				String[] infos = info.split("#");
				Integer outlets = Integer.parseInt(infos[0]);
				Integer business = Integer.parseInt(infos[1]);
				Integer device = Integer.parseInt(infos[2]);
				Long count = statisticsMap.get(info);
				businessStatisticsDao.insertBusinessStatistics(date, outlets, business, device, count);
			}
		}
		
		serverinfo.setLaststatisticstime(nowStatisticsTime);	//更新最后统计时间
		serverinfoDao.update(serverinfo);
	}
}
