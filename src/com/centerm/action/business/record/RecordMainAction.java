package com.centerm.action.business.record;

import java.util.Map;

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

@Controller("recordMainAction")
@Scope("prototype")
@Namespace("/business/record/main")
@ParentPackage("json-default")
public class RecordMainAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(RecordMainAction.class);
	
	private Map<String, Object> session;
	
	@Action
	(
		value = "toUI",
		results =
		{
			@Result(name = "success", location = "/WEB-INF/jsp/bquery/detail-query.jsp")
		}
	)
	public String toUI()
	{
		User user = (User)session.get("user");
		if ( user == null )
		{
			logger.info("进入实时业务查询界面");
			logger.debug("用户session为null");
			return "success";
		}
		
		logger.info("用户" + user.getUid() + "进入实时业务查询界面");
		return "success";
	}
	
	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}
}
