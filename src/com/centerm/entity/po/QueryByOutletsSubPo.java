package com.centerm.entity.po;

import java.util.Date;
import org.apache.struts2.json.annotations.JSON;

public class QueryByOutletsSubPo
{
	private Date date;	//交易日期
	private Integer business;	//网点id
	private String 	businessName;	//网点名称
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
	
	public Integer getBusiness()
	{
		return business;
	}

	public void setBusiness(Integer business)
	{
		this.business = business;
	}

	public String getBusinessName()
	{
		return businessName;
	}
	
	public void setBusinessName(String businessName)
	{
		this.businessName = businessName;
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
