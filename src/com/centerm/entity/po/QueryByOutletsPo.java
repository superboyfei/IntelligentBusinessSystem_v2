package com.centerm.entity.po;

public class QueryByOutletsPo
{
	private Integer outlets;	//网点id	
	private String outletsName;	//网点名称
	private Long count;	//交易数量
	
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
