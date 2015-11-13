package com.centerm.vo;


public class UploadAppInfoVo
{
	private String id;
	private String name;
	private String version;
	private String description;
	private String apkIconPath;
	private String businessName;
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
	public String getApkIconPath()
	{
		return apkIconPath;
	}
	public void setApkIconPath(String apkIconPath)
	{
		this.apkIconPath = apkIconPath;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getBusinessName()
	{
		return businessName;
	}
	public void setBusinessName(String businessName)
	{
		this.businessName = businessName;
	}
	
}