package com.centerm.vo;

public class AppVo
{
	private String id;	//节点id
	private String name;	//app文件名
	private String version;	//app版本
	private String description;	//app描述
	private String iconPath;	//app图标路径
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getVersion()
	{
		return version;
	}
	
	public void setVersion(String version)
	{
		this.version = version;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getIconPath()
	{
		return iconPath;
	}
	
	public void setIconPath(String iconPath)
	{
		this.iconPath = iconPath;
	}
}
