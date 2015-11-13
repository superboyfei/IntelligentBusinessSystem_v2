package com.centerm.service.manage.system;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.util.XmlUtil;

@Service("logService")
public class LogService
{
	private static final Logger logger = LogManager.getLogger(LogService.class);
	
	/**
	 * 获取日志输出级别
	 * @param configFile	日志配置文件路径
	 * @return	输出级别<br>
	 * 0 - 不输出<br>
	 * 1 - 低<br>
	 * 2 - 高<br>
	 * -1 - 获取失败
	 * @throws MalformedURLException
	 * @throws DocumentException
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public int getLogConfig(String configFile) throws MalformedURLException, DocumentException
	{	
		int printLevel = -1;
		
		Document configDocument = XmlUtil.read(configFile);
		if ( configDocument == null )
		{
			logger.debug("获取log配置文件错误");
			return printLevel;
		}
		
		Element rootNode = configDocument.getRootElement();
		List<?> loggersNodes = rootNode.elements("Loggers");
		for ( int i = 0; i < loggersNodes.size(); i++ )
		{
			Element loggersNode = (Element)loggersNodes.get(i);
			List<?> loggerNodes = loggersNode.elements("Logger");
			for ( int j = 0; j < loggerNodes.size(); j++ )
			{
				Element loggerNode = (Element)loggerNodes.get(j);
				String name = loggerNode.attributeValue("name");
				if ( name.equals("com.centerm") )	//找到代码日志配置项
				{
					String level = loggerNode.attributeValue("level");
					if ( level.equals("off") )
					{
						printLevel = 0;
					}
					else if ( level.equals("error") )
					{
						printLevel = 1;
					}
					else if ( level.equals("debug") )
					{
						printLevel = 2;
					}
				}
				else if ( name.equals("druid.sql.Statement") )	//找到数据源日志配置项
				{
					//数据源日志配置暂不进行设置
				}
			}
		}
		
		return printLevel;
	}
	
	/**
	 * 设置日志输出级别
	 * @param configFile	日志配置文件路径
	 * @param printLevel	输出级别<br>
	 * 0 - 不输出<br>
	 * 1 - 低<br>
	 * 2 - 高
	 * @return
	 * true - 设置成功
	 * false - 设置失败
	 * @throws DocumentException
	 * @throws IOException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean setLogConfig(String configFile, int printLevel) throws DocumentException, IOException
	{
		Document configDocument = XmlUtil.read(configFile);
		if ( configDocument == null )
		{
			logger.debug("获取log配置文件错误");
			return false;
		}
		
		Element rootNode = configDocument.getRootElement();
		List<?> loggersNodes = rootNode.elements("Loggers");
		for ( int i = 0; i < loggersNodes.size(); i++ )
		{
			Element loggersNode = (Element)loggersNodes.get(i);
			List<?> loggerNodes = loggersNode.elements("Logger");
			for ( int j = 0; j < loggerNodes.size(); j++ )
			{
				Element loggerNode = (Element)loggerNodes.get(j);
				String name = loggerNode.attributeValue("name");
				if ( name.equals("com.centerm") )	//找到代码日志配置项
				{
					Attribute level = loggerNode.attribute("level");
					switch ( printLevel )
					{
						case 0:
						{
							level.setValue("off");
							break;
						}
						case 1:
						{
							level.setValue("error");
							break;
						}
						case 2:
						{
							level.setValue("debug");
							break;
						}
					}
				}
				else if ( name.equals("druid.sql.Statement") )	//找到数据源日志配置项
				{
					//数据源日志配置暂不进行设置
				}
			}
		}
		XmlUtil.write(configDocument, configFile, null);
		
		return true;
	}
	
	/**
	 * 将指定起止日期之间的log日志打包为zip文件
	 * @param fileDir	zip文件路径
	 * @param fileName	zip文件名
	 * @param startDate	起始日期
	 * @param endDate	终止日期
	 * @return
	 * true - 打包成功<br>
	 * false - 打包失败，没有需要打包的日志文件
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean getZipLogFile(String fileDir, String fileName, String startDate, String endDate) throws ParseException, IOException
	{
		List<File> logList = new ArrayList<File>();
		Date start = DateUtils.parseDate(startDate, "yyyy-MM-dd");
		Date end = DateUtils.parseDate(endDate, "yyyy-MM-dd");
		String curDate = "";
		
		Collection<File> fileList = FileUtils.listFiles(new File(fileDir), new String[]{"log"}, false);	//获取目录下扩展名为log的所有文件集合，不进行递归
		for ( File file : fileList )
		{
			String name = file.getName();
			if ( name.equals("debug.log") )	//最后一天有效日志
			{
				FileInputStream fis = new FileInputStream(file);
				byte[] bDate = new byte[10];
				int readLen = fis.read(bDate, 0, bDate.length);
				fis.close();
				if ( readLen == 10 )	//该文件有内容
				{
					curDate = new String(bDate);
					logList.add(file);
				}
			}
			if ( name.length() == 20 )	//文件名符合日志文件名长度
			{
				String fileDate = name.substring(6, 16);
				Date cur = DateUtils.parseDate(fileDate, "yyyy-MM-dd");
				if ( (start.before(cur) || start.equals(cur)) && (end.after(cur) || end.equals(cur)) )
				{
					logList.add(file);
				}
			}
		}
		if ( logList.size() == 0 )	//如果不存在要打包的日志文件，则直接返回
		{
			return false;
		}
		
		FileOutputStream fos = new FileOutputStream(fileDir + File.separator + fileName);
		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
		zos.setEncoding("UTF-8");
		zos.setComment("logfiles between " + startDate + " and " + endDate);
		
		for ( File file : logList )
		{
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			String name = file.getName();
			logger.debug("压缩日志文件：" + name);
			
			if ( name.equals("debug.log") )	//将当天日志文件重命名为含有日期的格式
			{
				name = "debug-" + curDate + ".log";
			}
			zos.putNextEntry(new ZipEntry(name));
			
			int readLen = 0;
			byte[] readByte = new byte[(int)file.length()];
			do
			{
				readLen = bis.read(readByte, readLen, readByte.length);
			}
			while ( readLen < file.length() );
			zos.write(readByte);
			
			bis.close();
			zos.flush();
		}
		zos.close();
		
		return true;
	}
}
