package com.centerm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * 主键类，通过@Embeddable来标识
 * 1.必须实现Serializable
 * 2.必须覆盖equals和hashCode方法
 * 3.必须有无参构造方法
 * 4.主键ID的生成方式必须在主键类中标识GenerationType.IDENTITY
 * */
@Embeddable
public class BusinessStatisticsId implements Serializable
{
	private static final long serialVersionUID = -1875863517212408875L;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "businessstatistics_id")
	private Integer id;
	
	@Column(name = "businessstatistics_date")
	private Date date;
	
	public BusinessStatisticsId()
	{
		
	}
	
	public BusinessStatisticsId(Integer id, Date date)
	{
		this.id = id;
		this.date = date;
	}
	
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public boolean equals(Object obj)
	{
		if ( this == obj )
		{
			return true;
		}
		if ( obj == null )
		{
			return false;
		}
		if ( getClass() != obj.getClass() )
		{
			return false;
		}
		
		final BusinessStatisticsId other = (BusinessStatisticsId)obj;
		if ( id == other.id && date == other.date )
		{
			return true;
		}
		
		return false;
	}

	public int hashCode()
	{
		int result = id.hashCode() + date.hashCode();
		return result;
	}

}
