package com.centerm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_app")
public class App
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "app_id")
	private Integer id;
	
	@Column(name = "app_name")
	private String name;
	
	@Column(name = "app_packagename")
	private String packagename;
	
	@Column(name = "app_versioncode")
	private Integer versioncode;
	
	@Column(name = "app_version")
	private String version;
	
	@Column(name = "app_status")
	private Integer status;
	
	@Column(name = "app_uploadtime")
	private Date uploadtime;
	
	@Column(name = "app_deletetime")
	private Date deletetime;
	
	@Column(name = "app_filepath")
	private String filepath;
	
	@Column(name = "app_md5")
	private String md5;
	
	@Column(name = "app_iconpath")
	private String iconpath;
	
	@Column(name = "app_description")
	private String description;

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
	
	public String getPackagename()
	{
		return packagename;
	}

	public void setPackagename(String packagename)
	{
		this.packagename = packagename;
	}
	
	public Integer getVersioncode()
	{
		return versioncode;
	}

	public void setVersioncode(Integer versioncode)
	{
		this.versioncode = versioncode;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
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

	public String getIconpath()
	{
		return iconpath;
	}

	public void setIconpath(String iconpath)
	{
		this.iconpath = iconpath;
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
