package com.centerm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_task")
public class Task
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private Integer id;
	
	@Column(name = "task_name")
	private String name;
	
	@Column(name = "task_type")
	private Integer type;
	
	@Column(name = "task_status")
	private Integer status;
	
	@Column(name = "task_releasetime")
	private Date releasetime;
	
	@Column(name = "task_executetime")
	private Date executetime;
	
	@Column(name = "task_overtime")
	private Date overtime;
	
	@Column(name = "task_attachment")
	private Integer attachment;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getType()
	{
		return type;
	}

	public void setType(Integer type)
	{
		this.type = type;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Date getReleasetime()
	{
		return releasetime;
	}

	public void setReleasetime(Date releasetime)
	{
		this.releasetime = releasetime;
	}

	public Date getExecutetime()
	{
		return executetime;
	}

	public void setExecutetime(Date executetime)
	{
		this.executetime = executetime;
	}

	public Date getOvertime()
	{
		return overtime;
	}

	public void setOvertime(Date overtime)
	{
		this.overtime = overtime;
	}

	public Integer getAttachment()
	{
		return attachment;
	}

	public void setAttachment(Integer attachment)
	{
		this.attachment = attachment;
	}
}
