package com.centerm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtil
{
	/**
	 * 通过路径读取xml文件
	 * @param filePath	xml文件路径
	 * @return Document	xml文件对应的Document类
	 * @throws MalformedURLException
	 * @throws DocumentException
	 */
	public static Document read( String filePath ) throws MalformedURLException, DocumentException
	{
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new File(filePath));
		return document;
	}
	
	/**
	 * 通过输入流读取xml文件
	 * @param is	xml输入流
	 * @return Document	xml文件对应的Document类
	 * @throws MalformedURLException
	 * @throws DocumentException
	 */
	public static Document read( InputStream is ) throws MalformedURLException, DocumentException
	{
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(is);
		return document;
	}
	
	/**
	 * 通过路径写入xml文件
	 * @param document	xml文件对应的Document类
	 * @param filePath	xml文件路径
	 * @param characterSet	字符编码，默认采用utf-8
	 * @throws IOException
	 */
	public static void write( Document document, String filePath, String characterSet ) throws IOException
	{
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		if ( characterSet == null )
		{
			outputFormat.setEncoding("UTF-8");
		}
		XMLWriter writer = new XMLWriter(new FileOutputStream(filePath), outputFormat);
		writer.write(document);
		writer.close();
	}

	/**
	 * 通过文件类写入xml文件
	 * @param document	xml文件对应的Document类
	 * @param xmlFile	xml文件对应的File类
	 * @param characterSet	字符编码，默认采用utf-8
	 * @throws IOException
	 */
	public static void write( Document document, File xmlFile, String characterSet ) throws IOException
	{
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		if ( characterSet == null )
		{
			outputFormat.setEncoding("UTF-8");
		}
		XMLWriter writer = new XMLWriter(new FileOutputStream(xmlFile), outputFormat);
		writer.write(document);
		writer.close();
	}
}
