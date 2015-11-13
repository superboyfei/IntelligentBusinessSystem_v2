package com.centerm.entity.po;

public class QueryByDealPo
{
	private Integer business;	//业务id	
	private String businessName;	//业务名称
	private Long count;	//交易数量
	
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
