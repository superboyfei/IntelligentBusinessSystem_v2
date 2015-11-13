package com.centerm.msghandler.basehandler.device.work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.centerm.entity.Device;
import com.centerm.entity.Outlets;
import com.centerm.msghandler.basehandler.common.BaseWork;
import com.centerm.service.device.AppService;
import com.centerm.service.device.DeviceService;
import com.centerm.service.manage.outlets.OutletsService;
import com.centerm.vo.AppDownfileVo;

@Component("appWork")
public class AppWork extends BaseWork {
	
	private static final byte WORKBYTE = 0x05;

	@Resource(name = "deviceService")
	private DeviceService deviceService;
	@Resource(name = "outletsService")
	private OutletsService outletsService;
	@Resource(name = "appService")
	private AppService appService;

	@Override
	public byte getWorkByte() {
		return WORKBYTE;
	}

	@Override
	public Map<Byte, String> getMsgTypeMap() {
		Map<Byte, String> methodMap = new HashMap<Byte, String>();
		methodMap.put((byte) 0x04, "compareVersion");
		methodMap.put((byte) 0x05, "downloadFile");
		return methodMap;
	}
	
	/**
	 *  对比app版本
	 *  每个business的code对应app的id
	 * @param content
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public byte[] compareVersion(byte[] data) throws Exception {
		String content = new String(data, 0, data.length, ENCODE_UTF8).trim();
		JSONObject json = JSONObject.fromObject(content);
		String serial = json.getString("serial");
		
		Device device = deviceService.getDeviceBySerial(serial);
		if(device == null){
			throw new Exception("设备序列号:"+serial+"不正确");
		}
		Outlets outlets = outletsService.getOutletsById(device.getOutlets());
		
		// 返回设备应有的app及最新版本
		List<Map<String,String>> dev_apps = 
				(List<Map<String,String>>) appService.findAppByDeviceApp(outlets.getApp());
		JSONArray jsonArray = new JSONArray();
		if(dev_apps != null){
			jsonArray.addAll(dev_apps);
		}
		JSONObject resultJson = new JSONObject();
		resultJson.put("app", jsonArray.toString());
		return createMsg(WORKBYTE, (byte) 0x04, resultJson.toString());
	}

	/**
	 * @param content 获取code和version
	 * @return
	 * @throws Exception
	 */
	public byte[] downloadFile(byte[] data) throws Exception {
		String content = new String(data, 0, data.length, ENCODE_UTF8).trim();
		
		JSONObject json = JSONObject.fromObject(content);
		AppDownfileVo downfile = appService.findAppByPackagenameAndVersion(
				json.getString("packagename"), json.getInt("version"));
		
		JSONObject jsonFile = JSONObject.fromObject(downfile);
		JSONObject jsonFtp = getFtpMessage();
		
		JSONObject resultJson = new JSONObject();
		resultJson.putAll(jsonFile);
		resultJson.putAll(jsonFtp);
		return createMsg(WORKBYTE, (byte) 0x05,resultJson.toString());
	}

}
