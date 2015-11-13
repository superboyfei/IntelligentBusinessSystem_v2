package com.centerm.msghandler.basehandler.common;

/**
 * 报文处理类接口
 *
 */
public interface IWork
{
	public byte[] doWork(byte[] type, byte[] data);
}
