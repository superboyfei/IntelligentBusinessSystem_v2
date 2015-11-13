package com.centerm.msghandler.basehandler.device.work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.centerm.entity.Device;
import com.centerm.entity.DeviceType;
import com.centerm.entity.Outlets;
import com.centerm.msghandler.basehandler.common.BaseWork;
import com.centerm.service.device.DeviceService;
import com.centerm.service.device.DeviceTypeService;
import com.centerm.service.device.FirmwareService;
import com.centerm.service.manage.outlets.OutletsService;
import com.centerm.task.CheckDeviceStatusTask;

@Component("deviceWork")
public class DeviceWork extends BaseWork
{
	private static final byte WORKBYTE = 0x01;
	
	@Resource(name = "deviceService")
	private DeviceService deviceService;
	@Resource(name = "outletsService")
	private OutletsService outletsService;
	@Resource(name = "deviceTypeService")
	private DeviceTypeService deviceTypeService;
	@Resource(name = "firmwareService")
	private FirmwareService firmwareService;
	@Resource(name = "checkDeviceStatusTask")
	private CheckDeviceStatusTask checkDeviceStatusTask;
	
	@Override
	public byte getWorkByte() {
		return WORKBYTE;
	}

	@Override
	public Map<Byte, String> getMsgTypeMap() {
		Map<Byte,String> methodMap = new HashMap<Byte, String>();
		methodMap.put((byte) 0x01, "registeDevice");
		methodMap.put((byte) 0x02, "updateDeviceStatus");
		methodMap.put((byte) 0x03, "getAllDeviceType");
		return methodMap;
	}
	
	/**
	 * 设备注册申请
	 * 
	 * @param dev
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public synchronized byte[] registeDevice(byte[] data) throws Exception {
		String content = new String(data, 0, data.length, ENCODE_UTF8).trim();
		JSONObject json = JSONObject.fromObject(content);
		
		// 通过代码查询设备类型和网点
		DeviceType deviceType_tmp = deviceTypeService.getDeviceTypeByCode(json.getString("type"));
		if(deviceType_tmp == null){
			throw new Exception("设备类型:"+json.getString("type")+"不存在");
		}
		Outlets outlets = outletsService.getOutletsByCode(json.getString("outlets"));
		if(outlets == null){
			throw new Exception("网点代码:"+json.getString("outlets")+"不存在");
		} 
		Device device = deviceService.getDeviceBySerial(json.getString("serial"));
		int statusId = deviceService.getDeviceStatusIdByCode(1);
		if(device != null){
			//判断网点是否一致
			if(device.getOutlets() != outlets.getId()){
				device.setOutlets(outlets.getId());
				device.setApp(outlets.getApp());
			}
			device.setStatus(statusId);
			deviceService.updateDevice(device);
			// 添加到设备的在线列表中
			checkDeviceStatusTask.put(json.getString("serial"), System.currentTimeMillis());
			return createMsg(WORKBYTE, (byte) 0x11, new JSONObject().toString());
		}
		
		// 获取网点的app
		String outlets_app = outlets.getApp() == null ? "" : outlets.getApp();
		
		List<Map<String,Integer>> firmList = (List<Map<String, Integer>>) 
					firmwareService.findLatestFirmwareForRegist();
		StringBuilder firmware = new StringBuilder("");
		for(Map<String,Integer> map : firmList){
			firmware.append(map.get("code")+"_"+map.get("id")+",");
		}
		String firmwareStr = firmware.toString();
		if(firmwareStr.length() > 1){
			firmwareStr = firmwareStr.substring(0, firmwareStr.length() - 1);
		}
		
		json.put("firmware", firmwareStr);
		json.put("type", deviceType_tmp.getId());
		json.put("outlets", outlets.getId());
		json.put("app", outlets_app);
		json.put("status", statusId);
		
		deviceService.DeviceRegister(json);
		// 添加到设备的在线列表中
		checkDeviceStatusTask.put(json.getString("serial"), System.currentTimeMillis());
		return createMsg(WORKBYTE, (byte) 0x01, new JSONObject().toString());
	}
	
	/**
	 * 修改设备信息
	 * 
	 * @param content
	 * @throws Exception 
	 */
	public byte[] updateDeviceStatus(byte[] data) throws Exception {
		String content = new String(data, 0, data.length, ENCODE_UTF8).trim();
		JSONObject json = JSONObject.fromObject(content);
		String serial = json.getString("serial");
		Device device = deviceService.getDeviceBySerial(serial);
		if(device == null) {
			throw new Exception("设备未注册");
		}
		int status = json.getInt("status");
		if(status == 1) {
			//1,上线 0,下线
			checkDeviceStatusTask.put(serial, System.currentTimeMillis());
		} else if(status == 0) {
			status = deviceService.getDeviceStatusIdByCode(0);
		}
		device.setStatus(status);
		deviceService.updateDevice(device);
		return createMsg(WORKBYTE, (byte) 0x02, new JSONObject().toString());
	}
	
	/**
	 * 获取设备类型列表
	 * 
	 * @param content
	 * @return
	 * @throws Exception 
	 */
	public byte[] getAllDeviceType(byte[] data) throws Exception {
		//String content = new String(data, 0, data.length, ENCODE).trim();
		String result = null;
		List<DeviceType> listType = deviceTypeService.getAllDeviceType();
		if (listType != null ) {
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(listType);
			result = jsonArray.toString();
		}
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("type", result);
		return createMsg(WORKBYTE, (byte) 0x03, resultJson.toString());
	}
	
}
