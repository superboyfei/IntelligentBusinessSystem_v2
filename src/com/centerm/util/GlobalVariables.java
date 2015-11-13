package com.centerm.util;

import java.util.HashMap;
import java.util.Map;

public class GlobalVariables
{
	public static Map<Integer, String> businessMap = new HashMap<Integer, String>();	//交易id-交易名对应MAP
	//public static Map<Integer, String> outletsMap = new HashMap<Integer, String>();	//网点id-网点名对应MAP
	public static Map<Integer, String> deviceTypeMap = new HashMap<Integer, String>();	//设备类型id-设备类型code对应MAP
	public static Map<Integer, String> deviceStatusMap = new HashMap<Integer, String>();	//设备状态id-设备状态description对应MAP
	
	public static Map<String,String> ftpParamsMap = new HashMap<String, String>(); //ftp参数
}
