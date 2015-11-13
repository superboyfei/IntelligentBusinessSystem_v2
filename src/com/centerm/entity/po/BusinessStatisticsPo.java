package com.centerm.entity.po;

import java.util.Date;

public class BusinessStatisticsPo
{
	private Date date;	//日期
	private Long count;	//交易数量
	
	public Date getDate()
	{
		return date;
	}
	
	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public Long getCount()
	{
		return count;
	}
	
	public void setCount(Long count)
	{
		this.count = count;
	}
}
