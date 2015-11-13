package com.centerm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_counterconfig")
public class CounterConfig {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "config_id")
	private Integer id;
	
	@Column(name = "config_name")
	private String name;
	
	@Column(name = "config_outlets")
	private Integer outlets;
	
	@Column(name = "config_filepath")
	private String filepath;
	
	@Column(name = "config_ip")
	private String ip;
	
	@Column(name = "config_uploadtime")
	private Date uploadtime;
	
	@Column(name = "config_updatetime")
	private Date updatetime;
	
	@Column(name = "config_ispublic")
	private Boolean isPublic;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOutlets() {
		return outlets;
	}

	public void setOutlets(Integer outlets) {
		this.outlets = outlets;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
	
}
