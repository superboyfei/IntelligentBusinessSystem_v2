package com.centerm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(BusinessRecordId.class)
@Table(name = "ibs_businessrecord")
public class BusinessRecord 
{
	@Id
	private Integer id;
	@Id
	private Date time;	//业务办理时间
	@Column(name = "businessrecord_outlets")
	private Integer outlets;	//交易网点
	@Column(name = "businessrecord_business")
	private Integer business;	//业务类型
	@Column(name = "businessrecord_device")
	private Integer device;	//业务办理设备
	@Column(name = "businessrecord_data")
	private String data;	//业务数据
	@Column(name = "businessrecord_code")
	private String code;    //业务编码

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

	public Date getTime()
	{
		return time;
	}

	public void setTime(Date time)
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

	@Override
	public String toString() {
		return "BusinessRecord [id=" + id + ", time=" + time + ", outlets="
				+ outlets + ", business=" + business + ", device=" + device
				+ ", data=" + data + ", code=" + code + "]";
	}
	
}