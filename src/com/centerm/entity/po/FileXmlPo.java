package com.centerm.entity.po;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FileXmlPo
{
	@XmlElement(name = "Name")
	private String name;	//文件名
	
	@XmlElement(name = "Size")
	private String size;	//文件大小
	
	@XmlElement(name = "MD5")
	private String md5;	//文件MD5校验值
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getSize()
	{
		return size;
	}
	
	public void setSize(String size)
	{
		this.size = size;
	}
	
	public String getMd5()
	{
		return md5;
	}
	
	public void setMd5(String md5)
	{
		this.md5 = md5;
	}
}
