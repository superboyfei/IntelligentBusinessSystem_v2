package com.centerm.msghandler.basehandler.device.work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.centerm.msghandler.basehandler.common.BaseWork;
import com.centerm.service.manage.outlets.OutletsService;

@Component("outletsWork")
public class OutletsWork extends BaseWork
{
	private static final byte WORKBYTE = 0x02;
	

	@Resource(name = "outletsService")
	private OutletsService outletsService;

	@Override
	public byte getWorkByte() {
		return WORKBYTE;
	}

	@Override
	public Map<Byte, String> getMsgTypeMap() {
		Map<Byte,String> methodMap = new HashMap<Byte, String>();
		methodMap.put((byte) 0x03, "getAllOutlets");
		return methodMap;
	}
	
	@SuppressWarnings("unchecked")
	public byte[] getAllOutlets(byte[] data) throws Exception {

		String result = null;
		List<Map<String, String>> outletsList = (List<Map<String, String>>) outletsService
				.queryAllOutletsForDevice();
		if (outletsList != null ) {
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(outletsList);
			result = jsonArray.toString();
		}
		JSONObject resultJson = new JSONObject();
		resultJson.put("outlets", result);
		return createMsg(WORKBYTE, (byte) 0x03, resultJson.toString());
	}


}
