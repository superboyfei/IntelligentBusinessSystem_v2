package com.centerm.msghandler;

/**
 * 报文解析接口
 *
 */
public interface IMessageAnalyzer 
{
	/**
	 * 解析报文
	 * @param msg	待解析的报文
	 * @return	返回报文
	 */
	public byte[] analyzeMessage(byte[] msg);
	
	public void addClientIp(String ip);
}
