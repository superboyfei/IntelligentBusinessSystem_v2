package com.centerm.entity.po;

public class QueryByTimePo
{
	private String date;	//交易日期
	private Long count;	//交易数量
	
	public String getDate()
	{
		return date;
	}

	public void setDate(Object date)
	{
		String strDate = date.toString();
		if ( strDate.length() > 10 )
		{
			this.date = strDate.substring(0, 10);
		}
		else
		{
			this.date = strDate;
		}
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
