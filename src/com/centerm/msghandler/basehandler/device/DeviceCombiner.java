package com.centerm.msghandler.basehandler.device;

import org.springframework.stereotype.Component;

import com.centerm.msghandler.IMessageCombiner;
import com.centerm.util.ByteUtil;

/**
 * 报文组装实现类
 *
 */
@Component("deviceCombiner")
public class DeviceCombiner implements IMessageCombiner{
	
	@Override
	public byte[] combineMessage(byte[] type, byte[] data) {
		byte[] length = ByteUtil.lengthToByteArray(type.length+data.length+1);
		
		data = ByteUtil.bytes2And(type, data);
		// 获取校验值
		byte checker = ByteUtil.RedundancyCheck(data);
		byte[] resultMsg = ByteUtil.bytes3And(length, data, new byte[]{checker});
		
		return resultMsg;
	}

}
