package com.centerm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * 通过@IdClass引入复合主键类
 * */
@Entity
@IdClass(BusinessStatisticsId.class)
@Table(name = "ibs_businessstatistics")
public class BusinessStatistics 
{
	@Id
	private Integer id;
	@Id
	private Date date;
	@Column(name = "businessstatistics_outlets")
	private Integer outlets;
	@Column(name = "businessstatistics_business")
	private Integer business;
	@Column(name = "businessstatistics_device")
	private Integer device;
	@Column(name = "businessstatistics_count")
	private Long count;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
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

	public Long getCount()
	{
		return count;
	}

	public void setCount(Long count)
	{
		this.count = count;
	}

	@Override
	public String toString() {
		return "BusinessStatistics [id=" + id + ", date=" + date + ", outlets="
				+ outlets + ", business=" + business + ", device=" + device
				+ ", count=" + count + "]";
	}
	
}