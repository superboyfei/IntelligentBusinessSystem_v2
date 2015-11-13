package com.centerm.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_counterapptype")
public class CounterAppType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "counterapptype_id")
	private Integer id;
	
	@Column(name = "counterapptype_code")
	private String code;
	
	@Column(name = "counterapptype_name")
	private String name;
	
	@Column(name = "counterapptype_version")
	private String version;
	
	@Column(name = "counterapptype_updatetime")
	private Date updatetime;
	
	@Column(name = "counterapptype_description")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "CounterAppType [id=" + id + ", code=" + code + ", name=" + name
				+ ", version=" + version + ", updatetime=" + updatetime
				+ ", description=" + description + "]";
	}
	
}
