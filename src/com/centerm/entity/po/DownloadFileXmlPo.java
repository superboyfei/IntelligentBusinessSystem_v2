package com.centerm.entity.po;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class DownloadFileXmlPo {
	@XmlAttribute(name="path")
	private String path;
	@XmlAttribute(name = "name")
	private String name;
	@XmlAttribute(name = "description")
	private String description;
	
	public DownloadFileXmlPo(){
		
	}
	
	public DownloadFileXmlPo(String path,String name,String description){
		this.path = path;
		this.name = name;
		this.description = description;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "DownloadFileXmlPo [path=" + path + ", name=" + name
				+ ", description=" + description + "]";
	}
}
