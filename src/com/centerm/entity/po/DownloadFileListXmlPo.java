package com.centerm.entity.po;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DownloadFiles")
@XmlAccessorType(XmlAccessType.FIELD)
public class DownloadFileListXmlPo {
	@XmlElement(name = "DownloadFile")
	private List<DownloadFileXmlPo> downloadFile;

	public List<DownloadFileXmlPo> getDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(List<DownloadFileXmlPo> downloadFile) {
		this.downloadFile = downloadFile;
	}
	
}

