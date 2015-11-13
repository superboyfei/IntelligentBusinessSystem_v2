package com.centerm.vo;

public class OutletsVo
{
	private String id;	//节点
	private String code;	//网点编号
	private String name;	//网点名
	private boolean isParent;	//是否父节点
	private String parentid;	//父节点id
	private String description;	//网点描述
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean getIsParent()
	{
		return isParent;
	}
	
	public void setIsParent(boolean isParent)
	{
		this.isParent = isParent;
	}
	
	public String getParentid()
	{
		return parentid;
	}
	
	public void setParentid(String parentid)
	{
		this.parentid = parentid;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
}
