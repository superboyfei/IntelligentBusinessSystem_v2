package com.centerm.entity.po;

public class QueryByTimeSubPo
{
	private Integer business;	//业务id
	private String businessName;	//业务名
	private Integer outlets;	//网点id
	private String outletName;	//网点名
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
	
	public Integer getOutlets()
	{
		return outlets;
	}
	
	public void setOutlets(Integer outlets)
	{
		this.outlets = outlets;
	}
	
	public String getOutletName()
	{
		return outletName;
	}
	
	public void setOutletName(String outletName)
	{
		this.outletName = outletName;
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
