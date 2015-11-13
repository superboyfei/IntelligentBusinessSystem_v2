package com.centerm.action.system.download;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.centerm.entity.DownloadFile;
import com.centerm.entity.User;
import com.centerm.service.manage.download.DownloadService;
import com.centerm.vo.DownloadFileVo;

@Controller("downloadMainAction")
@Scope("prototype")
@Namespace("/system/download/main")
@ParentPackage("json-default")
public class DownloadMainAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(DownloadMainAction.class);
	
	private Integer id;	//下载文件id
	private Map<String, Object> session;
	
	private boolean result = false;
	private List<DownloadFileVo> downloadFileVoList = new ArrayList<DownloadFileVo>();
	private String errorMsg = "";
	
	@Resource(name = "downloadService")
	private DownloadService downloadService;
	
	@Action
	(
		value = "toDownloadManage",
		results =
		{
			@Result(name = "success", location = "/WEB-INF/jsp/download_manage.jsp")
		}
	)
	public String toDownloadManage()
	{
		User user = (User)session.get("user");
		if ( user == null )
		{
			logger.debug("用户进入下载中心管理界面");
			return "success";
		}
		
		logger.info("用户" + user.getUid() + "进入下载中心管理界面");
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
				errorMsg = "获取文件列表错误";
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
			logger.error("获取文件列表异常", e);
			result = false;
    		errorMsg = "获取文件列表异常";
	    	return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value="deleteDownLoadFileInfo",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String deleteDownLoadFileInfo()
	{
		try
		{
			boolean ret = downloadService.deleteDownloadFile(id);
			if ( ret == false )
			{
				result = false;
				errorMsg = "删除文件失败";
				return "error";
			}
		}
		catch(Exception e)
		{
			logger.error("删除文件异常", e);
			result = false;
    		errorMsg = "删除文件异常";
	    	return "error";
		}
		
		result = true;
		return "success";
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
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
