package com.centerm.service.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.BusinessRecordDao;
import com.centerm.dao.BusinessStatisticsDao;
import com.centerm.dao.OutletsDao;
import com.centerm.entity.Outlets;
import com.centerm.entity.po.BusinessStatisticsPo;
import com.centerm.msghandler.basehandler.common.BusinessRecordCode;
import com.centerm.vo.BusinessRecordVo;
import com.centerm.vo.CounterVo;

@Service("businessRecordService")
public class BusinessRecordService 
{
	@Resource(name = "outletsDao")
	private OutletsDao outletsDao;
	@Resource(name = "businessRecordDao")
	private BusinessRecordDao businessRecordDao;
	@Resource(name = "businessStatisticsDao")
	private BusinessStatisticsDao businessStatisticsDao;
	@Resource(name = "businessRecordCode")
	private BusinessRecordCode businessRecordCode;
	
	/**
	 * 新增交易记录
	 * @param json 
	 * @return
	 * @throws ParseException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveBusinessRecord(JSONObject json) throws ParseException{
		
		BusinessRecordVo record = new BusinessRecordVo();
		record.setBusiness(json.getInt("business"));
		record.setDevice(json.getInt("device"));
		record.setOutlets(json.getInt("outlets"));
		record.setTime(json.getString("time"));
		record.setData(json.getString("data"));
		record.setCode(json.getString("code"));
		
		//统计业务数
		String date = record.getTime().substring(0, 10);
		int outlets = record.getOutlets();
		int business = record.getBusiness();
		int device = record.getDevice();;
		BusinessStatisticsPo businessStatisticsPo = businessStatisticsDao.querySingleBusinessStatistics(date, outlets, business, device);
		if ( businessStatisticsPo == null )	//尚未做过满足条件的交易
		{
			businessStatisticsDao.insertBusinessStatistics(date, outlets, business, device, (long) 1);
		}
		else
		{
			long count = businessStatisticsPo.getCount().intValue();
			count++;	//交易数量加一
			businessStatisticsDao.updateBusinessStatistics(date, outlets, business, device, count);
		}
		
		businessRecordDao.saveBusinessRecord(record);
		
		// 修改outlets中的排队号
		Outlets outlets_temp = outletsDao.queryOutletsById(json.getInt("outlets"));
		outlets_temp.setRecordcode(json.getString("code"));
		outletsDao.update(outlets_temp);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public CounterVo getCounterDataByCode(String tableDate,String outletsCode,String code){
		
		List<CounterVo> list = businessRecordDao.getCounterDataByCode(tableDate,outletsCode,code);
		
		if(list != null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void initBusinessRecordCode(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<?> list = businessRecordDao.getBusinessRecordByDate(sdf.format(new Date()));
		List<Outlets> outletsList = outletsDao.findAll();
		if(list != null && list.size() > 0){
			//已经有了今天的交易记录，使用outlets中的排队号
			for(Outlets outlets : outletsList){
				businessRecordCode.putCode(outlets.getCode(), outlets.getRecordcode());
			}
		}else{
			for(Outlets outlets : outletsList){
				outlets.setRecordcode("000000");
				outletsDao.update(outlets);
			}
		}
	}	
}
