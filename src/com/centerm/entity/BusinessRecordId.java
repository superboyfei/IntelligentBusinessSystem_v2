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
public class BusinessRecordId implements Serializable{
	
	private static final long serialVersionUID = 4227164633131064330L;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "businessrecord_id")
	private Integer id;
	@Column(name = "businessrecord_time")
	private Date time;	//业务办理时间
	
	public BusinessRecordId(){
	}
	
	public BusinessRecordId(Integer id, Date time) {
		this.id = id;
		this.time = time;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BusinessRecordId other = (BusinessRecordId) obj;
		if (id == other.id && time == other.time)
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		int result = id.hashCode() + time.hashCode();
		return result;
	}
}
