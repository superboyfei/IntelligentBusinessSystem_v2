package com.centerm.vo;

public class DeviceVo 
{
	private String id;
	private String serial;	//设备编号
	private String type;	//设备类型
	private String outlets;	//设备网点
	private String status;	//设备状态
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getSerial()
	{
		return serial;
	}
	
	public void setSerial(String serial)
	{
		this.serial = serial;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getOutlets()
	{
		return outlets;
	}
	
	public void setOutlets(String outlets)
	{
		this.outlets = outlets;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
}
