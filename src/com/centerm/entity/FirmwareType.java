package com.centerm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_firmwaretype")
public class FirmwareType
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "firmwaretype_id")
	private Integer id;
	
	@Column(name = "firmwaretype_code")
	private String code;
	
	@Column(name = "firmwaretype_name")
	private String name;
	
	@Column(name = "firmwaretype_version")
	private String version;
	
	@Column(name = "firmwaretype_updatetime")
	private Date updatetime;
	
	@Column(name = "firmwaretype_description")
	private String description;

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

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}
	
	public Date getUpdatetime()
	{
		return updatetime;
	}
	
	public void setUpdatetime(Date updatetime)
	{
		this.updatetime = updatetime;
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
