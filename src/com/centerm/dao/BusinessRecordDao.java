package com.centerm.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.centerm.entity.BusinessRecord;
import com.centerm.entity.BusinessRecordId;
import com.centerm.util.DateUtil;
import com.centerm.vo.BusinessRecordVo;
import com.centerm.vo.CounterVo;

@Repository("businessRecordDao")
public class BusinessRecordDao extends BusinessStatisticsBaseDao<BusinessRecord,BusinessRecordId>
{
	
	/**
	 * 根据交易时间查询业务记录
	 * @param startTime	起始时间
	 * @param endTime	结束时间
	 * @return	按交易时间顺序排列的业务记录列表
	 */
	@SuppressWarnings("unchecked")
	public List<BusinessRecord> queryBusinessRecordByTime(String startTime, String endTime)
	{
		String hql = "from BusinessRecord br where br.time between :startTime and :endTime order by br.time";
		Query query = getSession().createQuery(hql)
				.setParameter("startTime", DateUtil.stringToTime(startTime))
				.setParameter("endTime", DateUtil.stringToTime(endTime));
		return (List<BusinessRecord>)query.list();
	}
	
	/**
	 * 保存交易记录
	 * 
	 * @param record
	 */
	public void saveBusinessRecord(BusinessRecordVo record) {
		BusinessRecord businessRecord = new BusinessRecord();
		businessRecord.setTime(DateUtil.stringToTime(record.getTime()));
		businessRecord.setOutlets(record.getOutlets());
		businessRecord.setBusiness(record.getBusiness());
		businessRecord.setDevice(record.getDevice());
		businessRecord.setData(record.getData());
		businessRecord.setCode(record.getCode());
		save(businessRecord);
	}
	
	@SuppressWarnings("unchecked")
	public List<CounterVo> getCounterDataByCode(String tableDate,String outletsCode,String code){
		String hql = "select br.data as data from BusinessRecord br "
				+" ,Outlets outlets where outlets.id = br.outlets "
				+" and outlets.code = :outletsCode and br.code = :code"
				+" and br.time >= :tableDate ";
		Query query = getSession().createQuery(hql);
		query.setParameter("outletsCode", outletsCode);
		query.setParameter("code", code);
		query.setParameter("tableDate", DateUtil.stringToTime(tableDate + " 00:00:00"));
		query.setResultTransformer(Transformers.aliasToBean(CounterVo.class));
		return query.list();
	}
	
	/**
	 * 获取当天的交易记录
	 * @param date 20xx-xx-xx
	 * @return
	 */
	public List<?> getBusinessRecordByDate(String date){
		String hql = "from BusinessRecord br where br.time >= :date ";
		Query query = getSession().createQuery(hql);
		query.setParameter("date", DateUtil.stringToTime(date + " 00:00:00"));
		return query.list();
	}
	
}
