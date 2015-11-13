package com.centerm.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.centerm.entity.DownloadFile;
import com.centerm.listener.FtpServerListener;
import com.centerm.service.manage.download.DownloadService;
import com.centerm.vo.DownloadFileVo;

@Controller("downloadCenterAction")
@Scope("prototype")
@Namespace("")
@ParentPackage("json-default")
public class DownloadCenterAction implements ServletResponseAware
{
	private static final Logger logger = LogManager.getLogger(DownloadCenterAction.class);
	
	private Integer id;	//下载文件id
	private HttpServletResponse response;
	
	private boolean result = false;
	private List<DownloadFileVo> downloadFileVoList = new ArrayList<DownloadFileVo>();
	private String errorMsg = "";
	
	@Resource(name = "downloadService")
	private DownloadService downloadService;
	
	@Action
	(
		value = "toDownload",
		results =
		{
			@Result(name = "success", location = "/WEB-INF/jsp/download.jsp")
		}
	)
	public String toDownload()
	{
		logger.debug("跳转到下载页面");
		return "success";
	}
	
	@Action
	(
		value = "loadDownLoadFileInfo",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, downloadFileVoList.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadDownLoadFileInfo()
	{
		try
		{
			List<DownloadFile> downloadFileList = downloadService.getAllDownloadFile();
			if ( downloadFileList == null )
			{
				result = false;
				errorMsg = "获取下载文件列表错误";
				return "error";
			}
			
			for ( DownloadFile downloadFile : downloadFileList )
			{
				DownloadFileVo downloadFileVo = new DownloadFileVo();
				downloadFileVo.setId(downloadFile.getId());
				logger.debug("id：" + downloadFileVo.getId());
				downloadFileVo.setName(downloadFile.getName());
				logger.debug("name：" + downloadFileVo.getName());
				downloadFileVo.setSize(downloadFile.getSize());
				logger.debug("size：" + downloadFileVo.getSize());
				downloadFileVo.setUploadtime(DateFormatUtils.format(downloadFile.getUploadtime(), "yyyy-MM-dd"));
				logger.debug("uploadtime：" + downloadFileVo.getUploadtime());
				downloadFileVo.setDescription(downloadFile.getDescription());
				logger.debug("description：" + downloadFileVo.getDescription());
				downloadFileVo.setFilepath(downloadFile.getFilepath());
				logger.debug("filepath：" + downloadFileVo.getFilepath());
				downloadFileVo.setMd5(downloadFile.getMd5());
				logger.debug("md5：" + downloadFileVo.getMd5());
				downloadFileVo.setIconpath(downloadFile.getIconpath());
				logger.debug("iconpath：" + downloadFileVo.getIconpath());
				
				downloadFileVoList.add(downloadFileVo);
			}
		}
		catch(Exception e)
		{
			logger.error("获取下载文件列表异常", e);
			result = false;
    		errorMsg = "获取下载文件列表异常";
	    	return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "downloadFile",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}		
	)
	public String downloadFile()
	{
		try
		{
			DownloadFile downloadFile = downloadService.getDownloadFile(id);
			if ( downloadFile == null )
			{
				result = false;
	    		errorMsg = "下载文件错误";
		    	return "error";
			}
			
			File file = new File(FtpServerListener.USER_DIR + downloadFile.getFilepath());
			if ( !file.exists() )
			{
				result = false;
				errorMsg = "下载文件不存在";
				return "error";
			}
			
			FileInputStream fis = new FileInputStream(file);
			byte[] fileData = new byte[downloadFile.getSize()];
			byte[] tmp = new byte[1024 * 1024 * 10];
			int readLength = 0;
			int lastRead = 0;
			int fileSize = downloadFile.getSize();
			while ( readLength < fileSize )
			{
				if(readLength + 1024*1024*10 > fileSize){
					lastRead = fileSize - readLength;
					fis.read(tmp);
					break;
				}
				fis.read(tmp);
				System.arraycopy(tmp, 0, fileData, readLength, 1024*1024*10);
				readLength += 1024*1024*10;
			}
			if(lastRead > 0){
				System.arraycopy(tmp, 0, fileData, readLength, lastRead);
			}
			fis.close();
			
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setContentType("text/html");
			response.setHeader("Content-disposition", "attachment;filename=" + new String(downloadFile.getName().getBytes("UTF8"), "ISO8859-1"));
			
			os.write(fileData);
			os.flush();
			os.close();
		}
		catch(Exception e)
		{
			logger.error("下载文件异常", e);
			result = false;
    		errorMsg = "下载文件异常";
	    	return "error";
		}
		
		result = true;
		return "success";
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public void setServletResponse(HttpServletResponse response)
	{
		this.response = response;
	}
	
	public boolean getResult()
	{
		return result;
	}
	
	public List<DownloadFileVo> getDownloadFileVoList()
	{
		return downloadFileVoList;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
}
