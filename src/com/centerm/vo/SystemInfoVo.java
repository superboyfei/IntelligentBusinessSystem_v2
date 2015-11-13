package com.centerm.vo;

public class SystemInfoVo
{
	private String systemVersion;	//系统版本
	private String ip;	//本地IP
	private String mac;	//MAC地址
	private String hdd;	//硬盘容量
	private String ram;	//内存大小
	private String lastStartupTime;	//最后一次启动时间
	private String onlineCount;	//在线人数
	
	public String getSystemVersion()
	{
		return systemVersion;
	}
	
	public void setSystemVersion(String systemVersion)
	{
		this.systemVersion = systemVersion;
	}
	
	public String getIp()
	{
		return ip;
	}
	
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	
	public String getMac()
	{
		return mac;
	}
	
	public void setMac(String mac)
	{
		this.mac = mac;
	}
	
	public String getHdd()
	{
		return hdd;
	}
	
	public void setHdd(String hdd)
	{
		this.hdd = hdd;
	}
	
	public String getRam()
	{
		return ram;
	}
	
	public void setRam(String ram)
	{
		this.ram = ram;
	}
	
	public String getLastStartupTime()
	{
		return lastStartupTime;
	}
	
	public void setLastStartupTime(String lastStartupTime)
	{
		this.lastStartupTime = lastStartupTime;
	}
	
	public String getOnlineCount()
	{
		return onlineCount;
	}
	
	public void setOnlineCount(String onlineCount)
	{
		this.onlineCount = onlineCount;
	}
}
