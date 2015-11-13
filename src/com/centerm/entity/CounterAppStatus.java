package com.centerm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_counterappstatus")
public class CounterAppStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "counterappstatus_id")
	private Integer id;
	
	@Column(name = "counterappstatus_code")
	private Integer code;
	
	@Column(name = "counterappstatus_description")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "CounterAppStatus [id=" + id + ", code=" + code
				+ ", description=" + description + "]";
	}
	
}
