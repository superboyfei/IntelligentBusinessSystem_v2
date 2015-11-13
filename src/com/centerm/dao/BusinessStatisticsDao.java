package com.centerm.dao;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.centerm.entity.BusinessStatistics;
import com.centerm.entity.BusinessStatisticsId;
import com.centerm.entity.po.BusinessStatisticsPo;
import com.centerm.entity.po.QueryByDealPo;
import com.centerm.entity.po.QueryByDealSubPo;
import com.centerm.entity.po.QueryByOutletsPo;
import com.centerm.entity.po.QueryByOutletsSubPo;
import com.centerm.entity.po.QueryByTimePo;
import com.centerm.entity.po.QueryByTimeSubPo;
import com.centerm.util.DateUtil;
import com.centerm.util.FormatUtil;

@Repository("businessStatisticsDao")
public class BusinessStatisticsDao extends BusinessStatisticsBaseDao<BusinessStatistics,BusinessStatisticsId>
{
	/**
	 * 插入业务统计记录
	 * @param date	交易日期
	 * @param outlets	交易网点
	 * @param business	业务类型
	 * @param device	交易设备
	 * @param count	交易数量
	 */
	public void insertBusinessStatistics(String date, Integer outlets, Integer business, Integer device, Long count)
	{
		BusinessStatistics businessStatistics = new BusinessStatistics();
		businessStatistics.setDate(DateUtil.stringToDate(date));
		businessStatistics.setOutlets(outlets);
		businessStatistics.setBusiness(business);
		businessStatistics.setDevice(device);
		businessStatistics.setCount(count);
		
		save(businessStatistics);
	}
	
	/**
	 * 查询指定起始日期到结束日期的交易数据统计记录
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	交易数据统计记录列表
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public List<BusinessStatisticsPo> queryBusinessStatistics(String startDate, String endDate) throws ParseException
	{
		String hql = "select bs.date as date, sum(bs.count) as count" +
					" from BusinessStatistics bs" +
					" where bs.date between :startDate and :endDate" +
					" group by bs.date";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
		query.setParameter("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
		query.setResultTransformer(Transformers.aliasToBean(BusinessStatisticsPo.class));
		
		return query.list();
	}
	
	/**
	 * 通过筛选条件按日期查询交易统计记录
	 * @param business	业务类别集合
	 * @param outlets	网点集合
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	交易数据统计记录列表
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public List<QueryByTimePo> queryBusinessStatisticsByDay(String[] business, String[] outlets, String startDate, String endDate) throws ParseException
	{
		String hql = "select bs.date as date, sum(bs.count) as count" +
					" from BusinessStatistics bs" +
					" where bs.date between :startDate and :endDate and" +
					" bs.business in (:business) and" +
					" bs.outlets in (:outlets)" +
					" group by bs.date";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
		query.setParameter("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
		query.setParameterList("business", FormatUtil.StringArrayToIntegerArray(business));
		query.setParameterList("outlets", FormatUtil.StringArrayToIntegerArray(outlets));
		query.setResultTransformer(Transformers.aliasToBean(QueryByTimePo.class));
		
		return query.list();
	}
	
	/**
	 * 通过筛选条件按月查询交易统计记录
	 * @param business	业务类别集合
	 * @param outlets	网点集合
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	交易数据统计记录
	 * @throws ParseException
	 */
	public QueryByTimePo queryBusinessStatisticsByMonth(String[] business, String[] outlets, String startDate, String endDate) throws ParseException
	{
		String title = startDate.substring(0, 7);	//截取年月
		String hql = "select '" + title + "' as date, sum(bs.count) as count" +
					" from BusinessStatistics bs" +
					" where bs.date between :startDate and :endDate and" +
					" bs.business in (:business) and" +
					" bs.outlets in (:outlets)";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
		query.setParameter("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
		query.setParameterList("business", FormatUtil.StringArrayToIntegerArray(business));
		query.setParameterList("outlets", FormatUtil.StringArrayToIntegerArray(outlets));
		query.setResultTransformer(Transformers.aliasToBean(QueryByTimePo.class));
		
		return (QueryByTimePo)query.uniqueResult();
	}
	
	/**
	 * 通过筛选条件按年查询交易统计记录
	 * @param business	业务类别集合
	 * @param outlets	网点集合
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	交易数据统计记录
	 * @throws ParseException
	 */
	public QueryByTimePo queryBusinessStatisticsByYear(String[] business, String[] outlets, String startDate, String endDate) throws ParseException
	{
		String title = startDate.substring(0, 4);	//截取年
		String hql = "select '" + title + "' as date, sum(bs.count) as count" +
					" from BusinessStatistics bs" +
					" where bs.date between :startDate and :endDate and" +
					" bs.business in (:business) and" +
					" bs.outlets in (:outlets)";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
		query.setParameter("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
		query.setParameterList("business", FormatUtil.StringArrayToIntegerArray(business));
		query.setParameterList("outlets", FormatUtil.StringArrayToIntegerArray(outlets));
		query.setResultTransformer(Transformers.aliasToBean(QueryByTimePo.class));
		
		return (QueryByTimePo)query.uniqueResult();
	}
	
	/**
	 * 通过筛选条件按季度查询交易统计记录
	 * @param business	业务类别集合
	 * @param outlets	网点集合
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	交易数据统计记录
	 * @throws ParseException
	 */
	public QueryByTimePo queryBusinessStatisticsBySeason(String[] business, String[] outlets, String startDate, String endDate) throws ParseException
	{
		String title = startDate.substring(0, 4);	//截取年
		String month = startDate.substring(5, 7);	//截取月
		String season = "";
		switch ( month )
		{
			case "01":
			{
				season = "1";
				break;
			}
			case "04":
			{
				season = "2";
				break;
			}
			case "07":
			{
				season = "3";
				break;
			}
			case "10":
			{
				season = "4";
				break;
			}
		}
		String hql = "select '" + title + "年第" + season + "季度" + "' as date, sum(bs.count) as count" +
					" from BusinessStatistics bs" +
					" where bs.date between :startDate and :endDate and" +
					" bs.business in (:business) and" +
					" bs.outlets in (:outlets)";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
		query.setParameter("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
		query.setParameterList("business", FormatUtil.StringArrayToIntegerArray(business));
		query.setParameterList("outlets", FormatUtil.StringArrayToIntegerArray(outlets));
		query.setResultTransformer(Transformers.aliasToBean(QueryByTimePo.class));
		
		return (QueryByTimePo)query.uniqueResult();
	}
	
	/**
	 * 通过筛选条件按时间查询交易统计子记录
	 * @param business	业务类别集合
	 * @param outlets	网点集合
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @param start	起始记录
	 * @param limit	记录条数
	 * @return	交易数据统计子记录
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public List<QueryByTimeSubPo> querySubBusinessStaisticsByTime(String[] business, String[] outlets, String startDate, String endDate, Integer start, Integer limit) throws ParseException
	{
		String hql = "select bs.business as business, bs.outlets as outlets, sum(bs.count) as count" +
					" from BusinessStatistics bs" +
					" where bs.date between :startDate and :endDate and" +
					" bs.business in (:business) and" +
					" bs.outlets in (:outlets)" +
					" group by bs.business, bs.outlets";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
		query.setParameter("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
		query.setParameterList("business", FormatUtil.StringArrayToIntegerArray(business));
		query.setParameterList("outlets", FormatUtil.StringArrayToIntegerArray(outlets));
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.setResultTransformer(Transformers.aliasToBean(QueryByTimeSubPo.class));
		
		return query.list();
	}
	
	/**
	 * 通过筛选条件按时间查询交易统计子记录总数
	 * @param business	业务类别集合
	 * @param outlets	网点集合
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	交易数据统计子记录总数
	 * @throws ParseException
	 */
	public Integer querySubBusinessStaisticsCountByTime(String[] business, String[] outlets, String startDate, String endDate) throws ParseException
	{
		String sql = "select count(distinct businessstatistics_business, businessstatistics_outlets)" +
					" from ibs_businessstatistics" +
					" where businessstatistics_date between :startDate and :endDate and" +
					" businessstatistics_business in (:business) and" +
					" businessstatistics_outlets in (:outlets)";
		
		Query query = getSession().createSQLQuery(sql);
		query.setParameter("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
		query.setParameter("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
		query.setParameterList("business", FormatUtil.StringArrayToIntegerArray(business));
		query.setParameterList("outlets", FormatUtil.StringArrayToIntegerArray(outlets));
		
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	/**
	 * 通过筛选条件按业务类别查询交易统计记录
	 * @param business	业务类别
	 * @param outlets	网点集合
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	交易数据统计记录列表
	 * @throws ParseException 
	 */
	public QueryByDealPo queryBusinessStatisticsByDeal(String business, String[] outlets, String startDate, String endDate) throws ParseException
	{
		String hql = "select " + business + " as business, sum(bs.count) as count" +
					" from BusinessStatistics bs" +
					" where bs.date between :startDate and :endDate and" +
					" bs.business = :business and" +
					" bs.outlets in (:outlets)";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
		query.setParameter("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
		query.setParameter("business", Integer.parseInt(business));
		query.setParameterList("outlets", FormatUtil.StringArrayToIntegerArray(outlets));
		query.setResultTransformer(Transformers.aliasToBean(QueryByDealPo.class));
		
		return (QueryByDealPo)query.uniqueResult();
	}
	
	/**
	 * 通过筛选条件按业务类别查询交易统计子记录
	 * @param business	业务类别
	 * @param outlets	网点集合
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @param start	起始记录
	 * @param limit	记录条数
	 * @return	交易数据统计子记录
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public List<QueryByDealSubPo> querySubBusinessStatisticsByDeal(String business, String[] outlets, String startDate, String endDate, Integer start, Integer limit) throws ParseException
	{
		String hql = "select bs.date as date, bs.outlets as outlets, sum(bs.count) as count" +
					" from BusinessStatistics bs" +
					" where bs.date between :startDate and :endDate and" +
					" bs.business = :business and" +
					" bs.outlets in (:outlets)" +
					" group by bs.date, bs.outlets";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
		query.setParameter("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
		query.setParameter("business", Integer.parseInt(business));
		query.setParameterList("outlets", FormatUtil.StringArrayToIntegerArray(outlets));
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.setResultTransformer(Transformers.aliasToBean(QueryByDealSubPo.class));
		
		return query.list();
	}
	
	/**
	 * 通过筛选条件按业务类别查询交易统计子记录总数
	 * @param business	业务类别
	 * @param outlets	网点集合
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	交易数据统计子记录总数
	 * @throws ParseException 
	 */
	public Integer querySubBusinessStatisticsCountByDeal(String business, String[] outlets, String startDate, String endDate) throws ParseException
	{
		String sql = "select count(distinct businessstatistics_date, businessstatistics_outlets)" +
					" from ibs_businessstatistics" +
					" where businessstatistics_date between :startDate and :endDate and" +
					" businessstatistics_business = :business and" +
					" businessstatistics_outlets in (:outlets)";
		
		Query query = getSession().createSQLQuery(sql);
		query.setParameter("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
		query.setParameter("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
		query.setParameter("business", Integer.parseInt(business));
		query.setParameterList("outlets", FormatUtil.StringArrayToIntegerArray(outlets));
		
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	/**
	 * 通过筛选条件按网点查询交易统计记录
	 * @param business	业务类别集合
	 * @param outlets	网点
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	交易数据统计记录列表
	 * @throws ParseException 
	 */
	public QueryByOutletsPo queryBusinessStatisticsByOutlets(String[] business, String outlets, String startDate, String endDate) throws ParseException
	{
		String hql = "select " + outlets + " as outlets, sum(bs.count) as count" +
					" from BusinessStatistics bs" +
					" where bs.date between :startDate and :endDate and" +
					" bs.business in (:business) and" +
					" bs.outlets = :outlets";
			
		Query query = getSession().createQuery(hql);
		query.setParameter("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
		query.setParameter("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
		query.setParameterList("business", FormatUtil.StringArrayToIntegerArray(business));
		query.setParameter("outlets", Integer.parseInt(outlets));
		query.setResultTransformer(Transformers.aliasToBean(QueryByOutletsPo.class));
		
		return (QueryByOutletsPo)query.uniqueResult();
	}
	
	/**
	 * 通过筛选条件按业务类别查询交易统计子记录
	 * @param business	交易类别
	 * @param outlets	网点
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @param start	起始记录
	 * @param limit	记录条数
	 * @return	当前页的交易统计子记录表
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public List<QueryByOutletsSubPo> querySubBusinessStatisticsByOutlets(String[] business, String outlets, String startDate, String endDate, Integer start, Integer limit) throws ParseException
	{
		String hql = "select bs.date as date, bs.business as business, sum(bs.count) as count" +
					" from BusinessStatistics bs" +
					" where bs.date between :startDate and :endDate and" +
					" bs.business in (:business) and" +
					" bs.outlets = :outlets" +
					" group by bs.date, bs.business";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
		query.setParameter("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
		query.setParameterList("business", FormatUtil.StringArrayToIntegerArray(business));
		query.setParameter("outlets", Integer.parseInt(outlets));
		query.setFirstResult(start);
		query.setMaxResults(limit);
		query.setResultTransformer(Transformers.aliasToBean(QueryByOutletsSubPo.class));
		
		return query.list();
	}
	
	/**
	 * 通过筛选条件按网点查询交易统计子记录总数
	 * @param business	业务类别
	 * @param outlets	网点
	 * @param startDate	起始日期
	 * @param endDate	结束日期
	 * @return	交易数据统计子记录总数
	 * @throws ParseException 
	 */
	public Integer querySubBusinessStatisticsCountByOutlets(String[] business, String outlets, String startDate, String endDate) throws ParseException
	{	
		String sql = "select count(distinct businessstatistics_date, businessstatistics_business)" +
					" from ibs_businessstatistics" +
					" where businessstatistics_date between :startDate and :endDate and" +
					" businessstatistics_business in (:business) and" +
					" businessstatistics_outlets = :outlets";
		
		Query query = getSession().createSQLQuery(sql);
		query.setParameter("startDate", DateUtils.parseDate(startDate, "yyyy-MM-dd"));
		query.setParameter("endDate", DateUtils.parseDate(endDate, "yyyy-MM-dd"));
		query.setParameterList("business", FormatUtil.StringArrayToIntegerArray(business));
		query.setParameter("outlets", Integer.parseInt(outlets));
		
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	
	
	
	
	

	
	
	

	
	/**
	 * 通过筛选条件查询单条统计记录
	 * @param date	交易日期
	 * @param outlets	交易网点
	 * @param business	交易类别
	 * @param device	交易设备
	 * @return	统计记录
	 */
	public BusinessStatisticsPo querySingleBusinessStatistics(String date, Integer outlets, Integer business, Integer device)
	{
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("select bs.date as date,bs.count as count from BusinessStatistics bs ")
			.append(" where bs.date = :date and")
			.append(" bs.outlets = :outlets and")
			.append(" bs.business = :business and")
			.append(" bs.device = :device");
		
		Query query = getSession().createQuery(hqlBuffer.toString());
		query.setParameter("date", DateUtil.stringToDate(date));
		query.setParameter("outlets", outlets);
		query.setParameter("business", business);
		query.setParameter("device", device);
		query.setResultTransformer(Transformers.aliasToBean(BusinessStatisticsPo.class));
		
		return (BusinessStatisticsPo)query.uniqueResult();
	}

/**
	 * 通过筛选条件更新交易数量
	 * @param date	交易日期
	 * @param outlets	交易网点
	 * @param business	交易类型
	 * @param device	交易设备
	 * @param count	交易数量
	 */
	public void updateBusinessStatistics(String date, Integer outlets, Integer business, Integer device, Long count)
	{
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("from BusinessStatistics bs where bs.date = :date "
				+ "and bs.outlets = :outlets and bs.business = :business "
				+ "and bs.device = :device");
		
		Query query = getSession().createQuery(hqlBuffer.toString());
		query.setParameter("date", DateUtil.stringToDate(date));
		query.setParameter("outlets", outlets);
		query.setParameter("business", business);
		query.setParameter("device", device);
		
		BusinessStatistics businessStatistics = (BusinessStatistics) query.uniqueResult();
		businessStatistics.setCount(count);
	}
	
}
