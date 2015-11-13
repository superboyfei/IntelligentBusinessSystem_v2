package com.centerm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_function")
public class Function
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "function_id")
	private Integer id;
	
	@Column(name = "function_name")
	private String name;
	
	@Column(name = "function_isparent")
	private Integer isparent;
	
	@Column(name = "function_parentid")
	private Integer parentid;
	
	@Column(name = "function_sortid")
	private Integer sortid;
	
	@Column(name = "function_url")
	private String url;
	
	@Column(name = "function_icon")
	private String icon;
	
	@Column(name = "function_target")
	private String target;
	
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
	
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getTarget()
	{
		return target;
	}

	public void setTarget(String target)
	{
		this.target = target;
	}

	public Integer getSortid()
	{
		return sortid;
	}

	public void setSortid(Integer sortid)
	{
		this.sortid = sortid;
	}
}
