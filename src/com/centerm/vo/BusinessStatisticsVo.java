package com.centerm.vo;

public class BusinessStatisticsVo
{
	private String date;	//日期
	private Long count;	//交易数量
	
	public String getDate()
	{
		return date;
	}
	
	public void setDate(String date)
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
