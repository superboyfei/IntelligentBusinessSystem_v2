package com.centerm.msghandler.basehandler.counter;

import org.springframework.stereotype.Component;

import com.centerm.msghandler.IMessageCombiner;
import com.centerm.util.ByteUtil;

@Component("counterCombiner")
public class CounterCombiner implements IMessageCombiner{
	private static final byte[] COUNTER_HEADER = new byte[]{(byte) 0xff, 0x00, 0x00};
	
	@Override
	public byte[] combineMessage(byte[] type, byte[] data) {
		byte[] length = ByteUtil.lengthToByteArray(type.length+data.length+1);
		
		data = ByteUtil.bytes2And(type, data);
		// 获取校验值
		byte checker = ByteUtil.RedundancyCheck(data);
		byte[] resultMsg = ByteUtil.bytes3And(length, data, new byte[]{checker});
		
		return  ByteUtil.bytes2And(COUNTER_HEADER, resultMsg);
	}

}
