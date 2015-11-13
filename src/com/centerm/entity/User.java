package com.centerm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.struts2.json.annotations.JSON;

@Entity
@Table(name = "ibs_user")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;
	
	@Column(name = "user_uid")
	private String uid;
	
	@Column(name = "user_name")
	private String name;
	
	@Column(name = "user_password")
	private String password;
	
	@Column(name = "user_isadmin")
	private Integer isadmin;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public String getUid()
	{
		return uid;
	}
	
	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@JSON(serialize=false)
	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Integer getIsadmin()
	{
		return isadmin;
	}

	public void setIsadmin(Integer isadmin)
	{
		this.isadmin = isadmin;
	}
}
