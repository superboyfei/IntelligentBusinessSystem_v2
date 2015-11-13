package com.centerm.action.system.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.centerm.entity.User;
import com.centerm.service.manage.user.UserService;
import com.centerm.vo.TableVo;

@Controller("userMainAction")
@Scope("prototype")
@Namespace("/system/user/main")
@ParentPackage("json-default")
public class UserMainAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(UserMainAction.class);
	
	private String id;	//用户id
	private String matchUid;	//用户账号匹配字段
	private Integer page;	//当前页码
	private Integer rows;	//每页加载记录数
	private Map<String, Object> session;
	
	private boolean result = false;
	private TableVo tableVo = new TableVo();
	private String errorMsg = "";
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Action
	(
		value = "mainUI",
		results =
		{
			@Result(name = "success_mainUI", location = "/WEB-INF/jsp/user/user_manage.jsp"),
			@Result(name = "success_otherUI", location = "/WEB-INF/jsp/user/user_manage_other.jsp")
		}
	)
	public String mainUI()
	{
		User user = (User)session.get("user");
		if ( user == null )
		{
			logger.debug("用户进入用户管理界面");
			return "success_otherUI";
		}
		
		logger.debug("用户" + user.getUid() + "进入用户管理界面");
		if ( user.getIsadmin().toString().equals("1") )
		{
			return "success_mainUI";
		}
		else
		{
			return "success_otherUI";
		}
	}
	
	@Action
	(
		value = "loadUsersByPage",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, tableVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String loadUsersByPage()
	{
		try
		{
			List<?> userList = userService.getUserListByUidMatchAndPage(matchUid, page, rows);
			if ( userList == null )
			{
				result = false;
				errorMsg = "获取用户列表错误";
				return "error";
			}
			
			int totalCount = userService.getUserCountByUidMatch(matchUid);
			if ( totalCount == -1 )
			{
				result = false;
				errorMsg = "获取用户列表错误";
				return "error";
			}
			int totalPage = (int)Math.ceil((double)totalCount / rows);
			
			tableVo.setTotalpages(totalPage);
			logger.debug("totalPage：" + tableVo.getTotalpages());
			tableVo.setCurrpage(page);
			logger.debug("currentPage：" + tableVo.getCurrpage());
			tableVo.setTotalrecords(totalCount);
			logger.debug("totalRecord：" + tableVo.getTotalrecords());
			tableVo.setList(userList);
		}
		catch(Exception e)
		{
			logger.error("获取用户列表异常", e);
			result = false;
			errorMsg = "获取用户列表异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	@Action
	(
		value = "delUser",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, errorMsg"})
		}
	)
	public String delUser()
	{
		try
		{
			boolean ret = userService.deleteUser(Integer.parseInt(id));
			if ( ret == false )
			{
				result = false;
				errorMsg = "删除用户失败";
				return "error";
			}
		}
		catch(Exception e)
		{
			logger.error("删除用户异常", e);
			result = false;
			errorMsg = "删除用户异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public void setMatchUid(String matchUid)
	{
		this.matchUid = matchUid;
	}
	
	public void setPage(Integer page)
	{
		this.page = page;
	}
	
	public void setRows(Integer rows)
	{
		this.rows = rows;
	}
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}
	
	public TableVo getTableVo()
	{
		return tableVo;
	}
	
	public boolean getResult()
	{
		return result;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
}
