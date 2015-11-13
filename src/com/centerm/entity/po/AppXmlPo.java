package com.centerm.entity.po;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AppInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppXmlPo
{
	@XmlElement(name = "Name")
	private String name;	//业务名
	
	@XmlElement(name = "Device")
	private String device;	//对应设备类型
	
	@XmlElement(name = "Code")
	private String code;	//业务号
	
	@XmlElement(name = "Version")
	private String version;	//应用版本
	
	@XmlElement(name = "Description")
	private String description;	//应用升级日志
	
	@XmlElement(name = "FileList")
	private FileListXmlPo fileList;	//应用文件列表

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDevice()
	{
		return device;
	}

	public void setDevice(String device)
	{
		this.device = device;
	}

	public String getCode() {
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

	public FileListXmlPo getFileList()
	{
		return fileList;
	}

	public void setFileList(FileListXmlPo fileList)
	{
		this.fileList = fileList;
	}
}
