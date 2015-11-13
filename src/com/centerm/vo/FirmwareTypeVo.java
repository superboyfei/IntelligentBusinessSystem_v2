package com.centerm.vo;


public class FirmwareTypeVo
{
	private String id;
	private String code;	//固件类型编号
	private String name;	//固件名称
	private String version;	//固件版本
	private String updatetime;	//固件更新时间
	private String description;	//固件更新日志

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

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}
	
	public String getUpdatetime()
	{
		return updatetime;
	}

	public void setUpdatetime(String updatetime)
	{
		this.updatetime = updatetime;
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
