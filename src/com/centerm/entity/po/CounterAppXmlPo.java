package com.centerm.entity.po;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TelnetInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class CounterAppXmlPo {
	@XmlElement(name = "Name")
	private String name;	//柜员应用名
	
	@XmlElement(name = "Version")
	private String version;	//柜员应用版本
	
	@XmlElement(name = "Code")
	private String code;	//柜员应用代码
	
	@XmlElement(name = "LastVersion")
	private String lastVersion;	//柜员应用适用版本
	
	@XmlElement(name = "Description")
	private String description;	//柜员应用升级日志
	
	@XmlElement(name = "FileList")
	private FileListXmlPo fileList;	//柜员应用文件列表
	
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
	
	public String getVersion()
	{
		return version;
	}
	
	public void setVersion(String version)
	{
		this.version = version;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	@Override
	public String toString() {
		return "CounterAppXmlPo [name=" + name + ", version=" + version
				+ ", lastVersion=" + lastVersion + ", description="
				+ description + ", fileList=" + fileList + ", result=" + result
				+ ", errorMsg=" + errorMsg + "]";
	}
	
}
