package com.centerm.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {
	/**
	 * null字符串转换为""
	 * @param s：String 字符串
	 * @return 去空的字符串
	 * */
	public static String killNull(String s){
		if(s == null){
			return "";
		} else {
			return s;
		}
	}
	
	public static String killNullToSpace(String s) {
		if(s == null || "".equals(s)) {
			return " ";
		} else {
			return s;
		}
	}
	
	/**
	 * 将字符串数组转换为整数数组
	 * @param args：String[] 字符串数组
	 * @return 整数数组
	 * */
	public static Integer[] StringToIntegerArray(String[] args){
		Integer[] ints = new Integer[args.length];
		for(int i = 0;i < args.length;i ++){
			ints[i] = Integer.parseInt(args[i]);
		}
		return ints;
	}
	
	/**
	 * 将字符串进行格式抓换
	 * @param args：String 字符串
	 * @return 制定字符集的字符串
	 * */
	public static String StringUTF8ToGBK(String s){
		String result = s;
		try {
			result = new String(s.getBytes("UTF-8"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getMD5(byte[] bytes){
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("md5");
			digest.update(bytes);	//计算MD5值
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return ByteUtil.bytesToHexString(digest.digest());
	}
}
