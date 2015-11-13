package com.centerm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_devicetask")
public class DeviceTask
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "devicetask_id")
	private Integer id;
	
	@Column(name = "devicetask_devicetype")
	private Integer devicetype;
	
	@Column(name = "devicetask_outlets")
	private Integer outlets;
	
	@Column(name = "devicetask_task")
	private Integer task;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getDevicetype()
	{
		return devicetype;
	}

	public void setDevicetype(Integer devicetype)
	{
		this.devicetype = devicetype;
	}

	public Integer getOutlets()
	{
		return outlets;
	}

	public void setOutlets(Integer outlets)
	{
		this.outlets = outlets;
	}

	public Integer getTask()
	{
		return task;
	}

	public void setTask(Integer task)
	{
		this.task = task;
	}
}
