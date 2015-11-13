package com.centerm.vo;


public class BusinessRecordVo 
{
	private Integer id;
	private String time;	//业务办理时间
	private Integer outlets;	//交易网点
	private Integer business;	//业务类型
	private Integer device;	//业务办理设备
	private String data;	//业务数据
	private String code;

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public Integer getOutlets()
	{
		return outlets;
	}

	public void setOutlets(Integer outlets)
	{
		this.outlets = outlets;
	}

	public Integer getBusiness()
	{
		return business;
	}

	public void setBusiness(Integer business)
	{
		this.business = business;
	}

	public Integer getDevice()
	{
		return device;
	}

	public void setDevice(Integer device)
	{
		this.device = device;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}
}