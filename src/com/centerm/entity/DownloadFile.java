package com.centerm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_downloadfile")
public class DownloadFile
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "downloadfile_id")
	private Integer id;
	
	@Column(name = "downloadfile_name")
	private String name;
	
	@Column(name = "downloadfile_size")
	private Integer size;
	
	@Column(name = "downloadfile_uploadtime")
	private Date uploadtime;
	
	@Column(name = "downloadfile_description")
	private String description;
	
	@Column(name = "downloadfile_filepath")
	private String filepath;
	
	@Column(name = "downloadfile_md5")
	private String md5;
	
	@Column(name = "downloadfile_iconpath")
	private String iconpath;
	
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
	
	public Integer getSize()
	{
		return size;
	}

	public void setSize(Integer size)
	{
		this.size = size;
	}
	
	public Date getUploadtime()
	{
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime)
	{
		this.uploadtime = uploadtime;
	}
	
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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
}
