package com.centerm.vo;

public class FunctionVo
{
	private String id;	//节点
	private String parentid;	//父节点id
	private String name;	//节点名
	private boolean isparent;	//是否父节点
	private String url;	//节点点击访问路径
	private String icon;	//节点图标路径
	private String target;	//节点样式
	private boolean checked;	//是否为勾选节点
	private boolean chkDisabled;	//是否禁止勾选
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getParentid()
	{
		return parentid;
	}
	
	public void setParentid(String parentid)
	{
		this.parentid = parentid;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean getIsparent()
	{
		return isparent;
	}
	
	public void setIsparent(boolean isparent)
	{
		this.isparent = isparent;
	}

	public String getUrl()
	{
		return url;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public String getIcon()
	{
		return icon;
	}
	
	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getTarget()
	{
		return target;
	}

	public void setTarget(String target)
	{
		this.target = target;
	}

	public boolean getChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}

	public boolean getChkDisabled()
	{
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled)
	{
		this.chkDisabled = chkDisabled;
	}
}
