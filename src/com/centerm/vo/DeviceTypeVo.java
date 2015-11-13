package com.centerm.vo;

public class DeviceTypeVo
{
	private String id;	//节点
	private String parentid;	//父节点
	private String name;	//类型名
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getParentid()
	{
		return parentid;
	}
	
	public void setParentid(String parentid)
	{
		this.parentid = parentid;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}
