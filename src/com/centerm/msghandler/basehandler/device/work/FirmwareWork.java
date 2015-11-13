package com.centerm.msghandler.basehandler.device.work;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.centerm.entity.Device;
import com.centerm.entity.Firmware;
import com.centerm.entity.FirmwareType;
import com.centerm.msghandler.basehandler.common.BaseWork;
import com.centerm.service.device.DeviceService;
import com.centerm.service.device.FirmwareService;
import com.centerm.vo.FirmwareDownfileVo;

@Component("firmwareWork")
public class FirmwareWork extends BaseWork
{
	private static final byte WORKBYTE = 0x04;
	
	@Resource(name = "deviceService")
	private DeviceService deviceService;
	@Resource(name = "firmwareService")
	private FirmwareService firmwareService;

	@Override
	public byte getWorkByte() {
		return WORKBYTE;
	}

	@Override
	public Map<Byte, String> getMsgTypeMap() {
		Map<Byte,String> methodMap = new HashMap<Byte, String>();
		methodMap.put( (byte) 0x04, "compareVersion");
		methodMap.put( (byte) 0x05, "downloadFile");
		return methodMap;
	}
	
	/**
	 *  对比固件版本，需要逐级升级
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public byte[] compareVersion(byte[] data) throws Exception{
		
		String content = new String(data, 0, data.length, ENCODE_UTF8).trim();
		JSONObject json = JSONObject.fromObject(content);
		
		// 通过序列号获取device
		String serial = json.getString("serial");
		Device device = deviceService.getDeviceBySerial(serial);
		if(device == null){
			throw new Exception("设备序列号:"+serial+"不正确"); 
		}
		
		// 获取客户端现有的固件版本，根据版本号判断是否升级到下一版本
		String lastversion = json.getString("version");
		String code = json.getString("code");
		
		//获取下一个版本
		FirmwareType firmwareType = firmwareService.getFirmwareTypeByCode(code);
		if(firmwareType == null || firmwareType.getVersion().equals(lastversion)){
			return createMsg( WORKBYTE, (byte) 0x00, new JSONObject().toString());
		}
		Firmware clientNextFirm = firmwareService.findFirmwareByCodeAndVersion(code, lastversion);
		if(clientNextFirm == null){
			return createMsg( WORKBYTE, (byte) 0x00, new JSONObject().toString());
		}
		
		// 需要升级发送下一版本的下载信息
		FirmwareDownfileVo downFirm = firmwareService.findFirmwareForDownfile(code, clientNextFirm.getVersion());
		JSONObject downJson = JSONObject.fromObject(downFirm);
		JSONObject ftpJson = getFtpMessage();
		
		JSONObject resultJson = new JSONObject();
		resultJson.putAll(downJson);
		resultJson.putAll(ftpJson);
		return createMsg( WORKBYTE, (byte) 0x04, resultJson.toString());
	}
	
	public byte[] downloadFile(byte[] data) throws Exception{
		
		return createMsg(WORKBYTE, (byte) 0x05, new JSONObject().toString());
	}
	

}
