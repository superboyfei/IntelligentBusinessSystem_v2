package com.centerm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_firmware")
public class Firmware
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "firmware_id")
	private Integer id;
	
	@Column(name = "firmware_name")
	private String name;
	
	@Column(name = "firmware_version")
	private String version;
	
	@Column(name = "firmware_lastversion")
	private String lastversion;
	
	@Column(name = "firmware_type")
	private Integer type;
	
	@Column(name = "firmware_status")
	private Integer status;
	
	@Column(name = "firmware_uploadtime")
	private Date uploadtime;
	
	@Column(name = "firmware_deletetime")
	private Date deletetime;
	
	@Column(name = "firmware_filepath")
	private String filepath;
	
	@Column(name = "firmware_md5")
	private String md5;
	
	@Column(name = "firmware_ismust")
	private Integer ismust;

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

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getLastversion()
	{
		return lastversion;
	}

	public void setLastversion(String lastversion)
	{
		this.lastversion = lastversion;
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

	public Date getUploadtime()
	{
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime)
	{
		this.uploadtime = uploadtime;
	}

	public Date getDeletetime()
	{
		return deletetime;
	}

	public void setDeletetime(Date deletetime)
	{
		this.deletetime = deletetime;
	}

	public String getFilepath()
	{
		return filepath;
	}

	public void setFilepath(String filepath)
	{
		this.filepath = filepath;
	}
	
	public String getMd5()
	{
		return md5;
	}

	public void setMd5(String md5)
	{
		this.md5 = md5;
	}

	public Integer isIsmust() 
	{
		return ismust;
	}

	public void setIsmust(Integer ismust) 
	{
		this.ismust = ismust;
	}
	
	
}
