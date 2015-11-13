package com.centerm.action.homepage;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.centerm.service.manage.system.VersionService;
import com.centerm.vo.SystemVersionVo;

@Controller("systemVersionAction")
@Scope("prototype")
@Namespace("/homepage/version")
@ParentPackage("json-default")
public class SystemVersionAction
{
	private SystemVersionVo systemVersionVo;
	private boolean result = false;
	
	@Action
	(
		value = "versionInfo",
		results =
		{
			@Result(name = "success", type = "json", params={"includeProperties", "result, systemVersionVo.*"}),
			@Result(name = "error", type = "json", params={"includeProperties", "result"})
		}
	)
	public String versionInfo()
	{
		systemVersionVo = new SystemVersionVo();
		systemVersionVo.setVersionNum(VersionService.SYSTEM_VERSION);
		systemVersionVo.setVersionLog("更新日志");
		result = true;
		return "success";
	}
	

	public boolean isResult()
	{
		return result;
	}

	public SystemVersionVo getSystemVersionVo()
	{
		return systemVersionVo;
	}
}
