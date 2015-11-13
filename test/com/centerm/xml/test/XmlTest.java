package com.centerm.xml.test;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.centerm.entity.xmlmodel.XmlFirmwareInfo;

public class XmlTest
{
	public static void main(String[] args) throws JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(XmlFirmwareInfo.class);  
        Unmarshaller unmarshaller = context.createUnmarshaller();  
        File file = new File("test/firm.xml");  
        XmlFirmwareInfo xmlFirmwareInfo = (XmlFirmwareInfo)unmarshaller.unmarshal(file);  
        System.out.println(xmlFirmwareInfo.getName());  
        System.out.println(xmlFirmwareInfo.getFileList().getXmlFiles().get(0).getMD5());  
	}
}
