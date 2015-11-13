package com.centerm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_serverinfo")
public class Serverinfo
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serverinfo_id")
	private Integer id;
	
	@Column(name = "serverinfo_firststartuptime")
	private Date firststartuptime;
	
	@Column(name = "serverinfo_laststartuptime")
	private Date laststartuptime;
	
	@Column(name = "serverinfo_laststatisticstime")
	private Date laststatisticstime;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Date getFirststartuptime()
	{
		return firststartuptime;
	}

	public void setFirststartuptime(Date firststartuptime)
	{
		this.firststartuptime = firststartuptime;
	}

	public Date getLaststartuptime()
	{
		return laststartuptime;
	}

	public void setLaststartuptime(Date laststartuptime)
	{
		this.laststartuptime = laststartuptime;
	}

	public Date getLaststatisticstime()
	{
		return laststatisticstime;
	}

	public void setLaststatisticstime(Date laststatisticstime)
	{
		this.laststatisticstime = laststatisticstime;
	}
}
