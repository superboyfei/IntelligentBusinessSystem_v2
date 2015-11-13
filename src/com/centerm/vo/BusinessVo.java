package com.centerm.vo;

public class BusinessVo
{
	private String id;	//节点
	private String code;	//业务号
	private String name;	//业务名
	private String parentid;	//父节点
	private boolean isParent;	//是否为父节点
	
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
	
	public String getParentid()
	{
		return parentid;
	}
	
	public void setParentid(String parentid)
	{
		this.parentid = parentid;
	}

	public boolean getIsParent()
	{
		return isParent;
	}

	public void setIsParent(boolean isParent)
	{
		this.isParent = isParent;
	}
}
