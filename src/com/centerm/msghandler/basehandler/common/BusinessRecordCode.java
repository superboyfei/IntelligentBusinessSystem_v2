package com.centerm.msghandler.basehandler.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("businessRecordCode")
public class BusinessRecordCode {
	
	// 每个网点自己的交易记录的当日的唯一码（6位数字）
	private static Map<String, String> recordCodeMap = new HashMap<String, String>();
	
	public synchronized String getRecordCode(String outletsCode){
		String result = null;
		if(recordCodeMap == null){
			recordCodeMap = new HashMap<String, String>();
		}
		String linekey = recordCodeMap.get(outletsCode);
		if(linekey == null || linekey.length()<6){
			linekey = "000001";
			result = linekey;
		}else{
			linekey = (Integer.parseInt(linekey)+1)+"";
			int num = linekey.length();
			// 补0
			result = "000000".substring(0, 6-num)+linekey;
		}
		
		recordCodeMap.put(outletsCode, result);
		return result;
	}
	
	/**
	 * 清空
	 */
	public void clearRecordCodeMap(){
		if(recordCodeMap != null){
			recordCodeMap.clear();
		}
	}
	
	public void putCode(String outletsCode,String code){
		if(recordCodeMap != null){
			recordCodeMap.put(outletsCode,code);
		}
	}
	
}
