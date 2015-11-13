package com.centerm.entity.hubei;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ibs_outlets_reflect")
public class OutletsReflect {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "outletsreflect_id")
	private Integer id;
	
	@Column(name = "outletsreflect_city")
	private String city;
	
	@Column(name = "outletsreflect_outlets")
	private String outlets;
	
	@Column(name = "outletsreflect_code")
	private String code;
	
	@Column(name = "outletsreflect_submask")
	private String submask;
	
	@Column(name = "outletsreflect_ipstart")
	private String start;
	
	@Column(name = "outletsreflect_ipend")
	private String end;
	
	@Column(name = "outletsreflect_startnum")
	private Integer startnum;
	
	@Column(name = "outletsreflect_endnum")
	private Integer endnum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOutlets() {
		return outlets;
	}

	public void setOutlets(String outlets) {
		this.outlets = outlets;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSubmask() {
		return submask;
	}

	public void setSubmask(String submask) {
		this.submask = submask;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Integer getStartnum() {
		return startnum;
	}

	public void setStartnum(Integer startnum) {
		this.startnum = startnum;
	}

	public Integer getEndnum() {
		return endnum;
	}

	public void setEndnum(Integer endnum) {
		this.endnum = endnum;
	}

}
