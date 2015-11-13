package com.centerm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_outlets")
public class Outlets
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "outlets_id")
	private Integer id;
	
	@Column(name = "outlets_code")
	private String code;
	
	@Column(name = "outlets_name")
	private String name;
	
	@Column(name = "outlets_isparent")
	private Integer isparent;
	
	@Column(name = "outlets_parentid")
	private Integer parentid;
	
	@Column(name = "outlets_sortid")
	private Integer sortid;
	
	@Column(name = "outlets_description")
	private String description;
	
	@Column(name = "outlets_app")
	private String app;
	
	@Column(name = "outlets_recordcode")
	private String recordcode;
	
	public String getRecordcode()
	{
		return recordcode;
	}
	
	public void setRecordcode(String recordcode)
	{
		this.recordcode = recordcode;
	}
	
	public String getApp()
	{
		return app;
	}
	
	public void setApp(String app)
	{
		this.app = app;
	}
	
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getIsparent()
	{
		return isparent;
	}

	public void setIsparent(Integer isparent)
	{
		this.isparent = isparent;
	}

	public Integer getParentid()
	{
		return parentid;
	}

	public void setParentid(Integer parentid)
	{
		this.parentid = parentid;
	}
	
	public Integer getSortid()
	{
		return sortid;
	}

	public void setSortid(Integer sortid)
	{
		this.sortid = sortid;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
