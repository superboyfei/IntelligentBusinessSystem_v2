package com.centerm.msghandler.basehandler.common;

import java.util.HashMap;
import java.util.Map;

public class AnalyzerParams {
	
	private static final byte DEVICE = 0x01;
	private static final byte OUTLETS = 0x02;
	private static final byte BUSINESS = 0x03;
	private static final byte FIRMWARE = 0x04;
	private static final byte APP = 0x05;
	private static final byte RESOURCE = 0x06;
	
	private static final byte TELNET = 0x07;
	private static final byte COUNTER_CONFIG = 0x08;
	
	public static final Map<Byte, String> DEVICE_WORK_MAP = getDeviceWorkMap();
	public static final Map<Byte, String> COUNTER_WORK_MAP = getCounterWorkMap();
			
	public static Map<Byte, String> getDeviceWorkMap(){
		Map<Byte, String> map = new HashMap<Byte, String>();
		map.put(DEVICE, "deviceWork");
		map.put(OUTLETS, "outletsWork");
		map.put(BUSINESS, "businessWork");
		map.put(FIRMWARE, "firmwareWork");
		map.put(APP, "appWork");
		map.put(RESOURCE, "resourceWork");
		
		return map;
	}
	
	public static Map<Byte, String> getCounterWorkMap(){
		Map<Byte, String> map = new HashMap<Byte, String>();
		map.put(TELNET, "telnetWork");
		map.put(COUNTER_CONFIG, "counterConfigWork");
		
		return map;
	}

}
