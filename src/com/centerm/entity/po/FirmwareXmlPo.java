package com.centerm.entity.po;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FirmwareInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class FirmwareXmlPo
{
	@XmlElement(name = "Name")
	private String name;	//固件名
	
	@XmlElement(name = "Device")
	private String device;	//对应设备类型
	
	@XmlElement(name = "FirmwareCode")
	private String firmwareCode;	//固件代码
	
	@XmlElement(name = "Ismust")
	private Integer ismust;	//是否必须升级
	
	@XmlElement(name = "Version")
	private String version;	//固件版本
	
	@XmlElement(name = "LastVersion")
	private String lastVersion;	//固件适用版本
	
	@XmlElement(name = "Description")
	private String description;	//固件升级日志
	
	@XmlElement(name = "FileList")
	private FileListXmlPo fileList;	//固件文件列表
	
	private boolean result = false;	//解析结果
	private String errorMsg = "";	//解析错误原因
	
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
	
	public String getFirmwareCode()
	{
		return firmwareCode;
	}
	
	public void setFirmwareCode(String firmwareCode)
	{
		this.firmwareCode = firmwareCode;
	}
	
	public Integer getIsmust() 
	{
		return ismust;
	}

	public void setIsmust(Integer ismust) 
	{
		this.ismust = ismust;
	}

	public String getVersion()
	{
		return version;
	}
	
	public void setVersion(String version)
	{
		this.version = version;
	}
	
	public String getLastVersion()
	{
		return lastVersion;
	}
	
	public void setLastVersion(String lastVersion)
	{
		this.lastVersion = lastVersion;
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
	
	public boolean getResult()
	{
		return result;
	}
	
	public void setResult(boolean result)
	{
		this.result = result;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}
}
