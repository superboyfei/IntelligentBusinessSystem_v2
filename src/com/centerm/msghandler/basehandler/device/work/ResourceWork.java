package com.centerm.msghandler.basehandler.device.work;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.centerm.msghandler.basehandler.common.BaseWork;

@Component("resourceWork")
public class ResourceWork extends BaseWork
{
	private static final byte WORKBYTE = 0x06;
	
	@Override
	public byte getWorkByte() {
		return WORKBYTE;
	}

	@Override
	public Map<Byte, String> getMsgTypeMap() {
		Map<Byte,String> methodMap = new HashMap<Byte, String>();
		methodMap.put((byte) 0x04, "compareVersion");
		methodMap.put((byte) 0x05, "downloadFile");
		return methodMap;
	}
	
	
	public byte[] compareVersion(byte[] data){
		
		return null;
	}
	
	public byte[] downloadFile(byte[] data){
		return null;
	}

}
