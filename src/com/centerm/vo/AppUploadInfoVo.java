package com.centerm.vo;

public class AppUploadInfoVo
{
	private String name;	//业务名
	private String code;	//业务号
	private String version;	//应用版本
	private String description;	//应用升级日志
	private String iconPath;	//应用图标路径
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
