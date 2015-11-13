package com.centerm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_device")
public class Device
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "device_id")
	private Integer id;
	
	@Column(name = "device_serial")
	private String serial;
	
	@Column(name = "device_type")
	private Integer type;
	
	@Column(name = "device_outlets")
	private Integer outlets;
	
	@Column(name = "device_status")
	private Integer status;
	
	@Column(name = "device_ip")
	private String ip;
	
	@Column(name = "device_firmware")
	private String firmware;
	
	@Column(name = "device_app")
	private String app;
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer id)
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
	
	public Integer getType()
	{
		return type;
	}
	
	public void setType(Integer type)
	{
		this.type = type;
	}
	
	public Integer getOutlets()
	{
		return outlets;
	}
	
	public void setOutlets(Integer outlets)
	{
		this.outlets = outlets;
	}
	
	public Integer getStatus()
	{
		return status;
	}
	
	public void setStatus(Integer status)
	{
		this.status = status;
	}
	
	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}
	
	public String getFirmware()
	{
		return firmware;
	}
	
	public void setFirmware(String firmware)
	{
		this.firmware = firmware;
	}

	public String getApp()
	{
		return app;
	}
	
	public void setApp(String app)
	{
		this.app = app;
	}
}
