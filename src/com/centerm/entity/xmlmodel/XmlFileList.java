package com.centerm.entity.xmlmodel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class XmlFileList
{
	@XmlElement(name = "File")
	private List<XmlFile> xmlFiles;

	public List<XmlFile> getXmlFiles()
	{
		return xmlFiles;
	}

	public void setXmlFiles(List<XmlFile> xmlFiles)
	{
		this.xmlFiles = xmlFiles;
	} 
}
