package com.centerm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_authority")
public class Authority
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authority_id")
	private Integer id;
	
	@Column(name = "authority_userid")
	private Integer userid;
	
	@Column(name = "authority_functionid")
	private Integer functionid;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getUserid()
	{
		return userid;
	}

	public void setUserid(Integer userid)
	{
		this.userid = userid;
	}

	public Integer getFunctionid()
	{
		return functionid;
	}

	public void setFunctionid(Integer functionid)
	{
		this.functionid = functionid;
	}
}
