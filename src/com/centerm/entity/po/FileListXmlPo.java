package com.centerm.entity.po;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FileListXmlPo
{
	@XmlElement(name = "File")
	private List<FileXmlPo> file;	//文件列表

	public List<FileXmlPo> getFile()
	{
		return file;
	}

	public void setFile(List<FileXmlPo> file)
	{
		this.file = file;
	} 
}
