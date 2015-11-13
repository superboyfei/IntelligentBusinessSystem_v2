package com.centerm.entity.xmlmodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmlFile
{
	@XmlElement
	private String Name;
	@XmlElement
	private String Size;
	@XmlElement
	private String MD5;
	public String getSize()
	{
		return Size;
	}
	public void setSize(String size)
	{
		Size = size;
	}
	public String getMD5()
	{
		return MD5;
	}
	public void setMD5(String mD5)
	{
		MD5 = mD5;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String name)
	{
		Name = name;
	}
	
}
