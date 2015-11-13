package com.centerm.entity.xmlmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="FirmwareInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlFirmwareInfo
{
	@XmlElement
	private String Name;
	@XmlElement
	private String Device;
	@XmlElement
	private String FirmwareCode;
	@XmlElement
	private String Version;
	@XmlElement
	private String LastVersion;
	@XmlElement
	private String Description;
	@XmlElement(name = "FileList")
	private XmlFileList fileList;
	private boolean legal = false;
	private String legalTip;
	public String getName()
	{
		return Name;
	}
	public void setName(String name)
	{
		Name = name;
	}
	public String getDevice()
	{
		return Device;
	}
	public void setDevice(String device)
	{
		Device = device;
	}
	public String getFirmwareCode()
	{
		return FirmwareCode;
	}
	public void setFirmwareCode(String firmwareCode)
	{
		FirmwareCode = firmwareCode;
	}
	public String getVersion()
	{
		return Version;
	}
	public void setVersion(String version)
	{
		Version = version;
	}
	public String getLastVersion()
	{
		return LastVersion;
	}
	public void setLastVersion(String lastVersion)
	{
		LastVersion = lastVersion;
	}
	public String getDescription()
	{
		return Description;
	}
	public void setDescription(String description)
	{
		Description = description;
	}
	public XmlFileList getFileList()
	{
		return fileList;
	}
	public void setFileList(XmlFileList fileList)
	{
		this.fileList = fileList;
	}
	public boolean isLegal()
	{
		return legal;
	}
	public void setLegal(boolean legal)
	{
		this.legal = legal;
	}
	public String getLegalTip()
	{
		return legalTip;
	}
	public void setLegalTip(String legalTip)
	{
		this.legalTip = legalTip;
	}
	
}
