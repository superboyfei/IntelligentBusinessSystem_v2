package com.centerm.action.device.devicequery;

import java.util.ArrayList;
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

import com.centerm.entity.Device;
import com.centerm.entity.User;
import com.centerm.service.device.DeviceService;
import com.centerm.service.manage.outlets.OutletsService;
import com.centerm.util.GlobalVariables;
import com.centerm.vo.DeviceVo;
import com.centerm.vo.TableVo;

@Controller("deviceQueryMainAction")
@Scope("prototype")
@Namespace("/device/devicequery/main")
@ParentPackage("json-default")
public class DeviceQueryMainAction implements SessionAware
{
	private static final Logger logger = LogManager.getLogger(DeviceQueryMainAction.class);
	
	private String outlet;	//网点
	private String deviceType;	//设备类型
	private Integer page;	//当前页码
	private Integer rows;	//每页加载记录数
	private Map<String, Object> session;
	
	private boolean result = false;
	private TableVo tableVo = new TableVo();
	private String errorMsg = "";
	
	@Resource(name = "deviceService")
	private DeviceService deviceService;
	
	@Action
	(
		value = "toUI",
		results =
		{
			@Result(name = "success", location = "/WEB-INF/jsp/device/device_status.jsp")
		}
	)
	public String toUI()
	{
		User user = (User)session.get("user");
		if ( user == null )
		{
			logger.debug("用户进入设备查询界面");
			return "success";
		}
		
		logger.debug("用户" + user.getUid() + "进入设备查询界面");
		return "success";
	}
	
	@Action
	(
		value = "loadDevicesByPage",
		results =
		{
			@Result(name = "success", type = "json", params = {"includeProperties", "result, tableVo.*"}),
			@Result(name = "error", type = "json", params = {"includeProperties", "result, retMsg"})
		}
	)
	public String loadDevicesByPage()
	{
		try
		{
			List<?> deviceList = deviceService.getDeviceListByPage(outlet, deviceType, page, rows);
			if ( deviceList == null )
			{
				result = false;
				errorMsg = "设备查询错误";
				return "error";
			}
			
			List<DeviceVo> deviceVoList = new ArrayList<DeviceVo>();
			for ( int i = 0; i < deviceList.size(); i++ )
			{
				Device device = (Device)deviceList.get(i);
				DeviceVo deviceVo = new DeviceVo();
				deviceVo.setId(String.valueOf(device.getId()));
				logger.debug("id：" + deviceVo.getId());
				deviceVo.setSerial(device.getSerial());
				logger.debug("serial：" + deviceVo.getSerial());
				deviceVo.setType(GlobalVariables.deviceTypeMap.get(device.getType()));
				logger.debug("type：" + deviceVo.getType());
				deviceVo.setOutlets(OutletsService.outletsMap.get(device.getOutlets()));
				logger.debug("outlets：" + deviceVo.getOutlets());
				deviceVo.setStatus(GlobalVariables.deviceStatusMap.get(device.getStatus()));
				logger.debug("status：" + deviceVo.getStatus());
				deviceVoList.add(deviceVo);
			}
			
			int totalCount = deviceService.getDeviceCount(outlet, deviceType);
			if ( totalCount == -1 )
			{
				result = false;
				errorMsg = "设备查询错误";
				return "error";
			}
			int totalPage = (int)Math.ceil((double)totalCount / rows);
			
			tableVo.setTotalpages(totalPage);
			tableVo.setCurrpage(page);
			tableVo.setTotalrecords(totalCount);
			tableVo.setList(deviceVoList);
		}
		catch(Exception e)
		{
			logger.error("设备查询异常", e);
			result = false;
			errorMsg = "设备查询异常";
			return "error";
		}
		
		result = true;
		return "success";
	}
	
	public void setOutlet(String outlet)
	{
		this.outlet = outlet;
	}

	public void setDeviceType(String deviceType)
	{
		this.deviceType = deviceType;
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
	
	public boolean getResult()
	{
		return result;
	}
	
	public TableVo getTableVo()
	{
		return tableVo;
	}
	
	public String getErrorMsg()
	{
		return errorMsg;
	}
}
