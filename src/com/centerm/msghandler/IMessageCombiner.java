package com.centerm.msghandler;

/**
 * 报文组装接口
 *
 */
public interface IMessageCombiner
{
	public byte[] combineMessage(byte[] type, byte[] data);
}
