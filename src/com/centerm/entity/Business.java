package com.centerm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_business")
public class Business
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "business_id")
	private Integer id;
	
	@Column(name = "business_code")
	private String code;
	
	@Column(name = "business_name")
	private String name;
	
	@Column(name = "business_isparent")
	private Integer isparent;
	
	@Column(name = "business_parentid")
	private Integer parentid;
	
	@Column(name = "business_sortid")
	private Integer sortid;
	
	@Column(name = "business_feature")
	private String feature;
	
	@Column(name = "business_app")
	private int app;

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

	public String getFeature()
	{
		return feature;
	}

	public void setFeature(String feature)
	{
		this.feature = feature;
	}
	
	public Integer getApp()
	{
		return app;
	}

	public void setApp(Integer app)
	{
		this.app = app;
	}
}
