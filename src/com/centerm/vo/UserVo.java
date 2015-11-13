package com.centerm.vo;

public class UserVo
{
	private String id;	//用户id
	private String uid;	//用户账号
	private String name;	//用户昵称
	private boolean isadmin;	//是否管理员
	
	public String getUid()
	{
		return uid;
	}
	
	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
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

	public boolean getIsadmin()
	{
		return isadmin;
	}

	public void setIsadmin(boolean isadmin)
	{
		this.isadmin = isadmin;
	}
}
