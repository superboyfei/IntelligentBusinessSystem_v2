package com.centerm.task;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.centerm.entity.Outlets;
import com.centerm.msghandler.basehandler.common.BusinessRecordCode;
import com.centerm.service.manage.outlets.OutletsService;

@Component("businessRecordCodeTask")
public class BusinessRecordCodeTask 
{
	@Resource(name = "outletsService")
	private OutletsService outletsService;
	@Resource(name = "businessRecordCode")
	private BusinessRecordCode businessRecordCode;
	
	/**
	 * 每天清空排队号
	 */
	@SuppressWarnings("unchecked")
	@Scheduled(cron="0 0 0 * * ? ")
	public void clearBusinessRecordCode()
	{
		businessRecordCode.clearRecordCodeMap();
		List<Outlets> list = (List<Outlets>) outletsService.getAllOutlets();
		for(Outlets outlets : list){
			outlets.setRecordcode("000000");
			outletsService.updateOutlets(outlets);
		}
	}
}
