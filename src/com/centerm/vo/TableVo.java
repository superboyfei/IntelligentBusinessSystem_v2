package com.centerm.vo;

import java.util.List;

public class TableVo
{
	private int totalpages;	//总页数
	private int currpage;	//当前页
	private int totalrecords;	//总记录数
	private List<?> list;	//数据
	
	public int getTotalpages()
	{
		return totalpages;
	}
	
	public void setTotalpages(int totalpages)
	{
		this.totalpages = totalpages;
	}
	
	public int getCurrpage()
	{
		return currpage;
	}
	
	public void setCurrpage(int currpage)
	{
		this.currpage = currpage;
	}
	
	public int getTotalrecords()
	{
		return totalrecords;
	}
	
	public void setTotalrecords(int totalrecords)
	{
		this.totalrecords = totalrecords;
	}
	
	public List<?> getList()
	{
		return list;
	}
	
	public void setList(List<?> list)
	{
		this.list = list;
	}
}
