package com.centerm.vo;

public class DownloadFileVo
{
	private Integer id;	//文件id
	private String name;	//文件名
	private Integer size;	//文件大小
	private String uploadtime;	//上传时间
	private String description;	//文件描述
	private String filepath;	//文件路径
	private String md5;	//文件校验值
	private String iconpath;	//文件图标路径
	
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
	
	public String getUploadtime()
	{
		return uploadtime;
	}
	
	public void setUploadtime(String uploadtime)
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
