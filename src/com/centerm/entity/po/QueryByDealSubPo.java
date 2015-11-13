package com.centerm.entity.po;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class QueryByDealSubPo
{
	private Date date;	//交易日期
	private Integer outlets;	//网点id
	private String 	outletsName;	//网点名
	private Long count;	//交易数量
	
	@JSON(format = "yyyy-MM-dd")
	public Date getDate()
	{
		return date;
	}
	
	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public Integer getOutlets()
	{
		return outlets;
	}
	
	public void setOutlets(Integer outlets)
	{
		this.outlets = outlets;
	}
	
	public String getOutletsName()
	{
		return outletsName;
	}
	
	public void setOutletsName(String outletsName)
	{
		this.outletsName = outletsName;
	}
	
	public Long getCount()
	{
		return count;
	}
	
	public void setCount(Long count)
	{
		if ( count == null )
		{
			count = (long)0;
		}
		this.count = count;
	}
}
