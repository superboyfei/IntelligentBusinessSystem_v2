package com.centerm.msghandler.basehandler.device.work;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.JDBCException;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

import com.centerm.entity.Business;
import com.centerm.entity.Device;
import com.centerm.entity.Outlets;
import com.centerm.msghandler.basehandler.common.BaseWork;
import com.centerm.msghandler.basehandler.common.BusinessRecordCode;
import com.centerm.service.business.BusinessRecordService;
import com.centerm.service.business.CustomerService;
import com.centerm.service.device.DeviceService;
import com.centerm.service.manage.business.BusinessService;
import com.centerm.service.manage.outlets.OutletsService;
import com.centerm.task.CheckDeviceStatusTask;

@Component("businessWork")
public class BusinessWork extends BaseWork
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final byte WORKBYTE = 0x03;
	
	@Resource(name = "deviceService")
	private DeviceService deviceService;
	@Resource(name = "outletsService")
	private OutletsService outletsService;
	@Resource(name = "businessService")
	private BusinessService businessService;
	@Resource(name = "customerService")
	private CustomerService customerService;
	@Resource(name = "businessRecordCode")
	private BusinessRecordCode businessRecordCode;
	@Resource(name = "businessRecordService")
	private BusinessRecordService businessRecordService;
	@Resource(name = "checkDeviceStatusTask")
	private CheckDeviceStatusTask checkDeviceStatusTask;
	//@Resource(name = "transRecordService")
	//private TransRecordService transRecordService;
	
	@Override
	public byte getWorkByte() {
		return WORKBYTE;
	}

	@Override
	public Map<Byte, String> getMsgTypeMap() {
		Map<Byte,String> methodMap = new HashMap<Byte, String>();
		methodMap.put((byte) 0x01, "addBusinessRecord");
		return methodMap;
	}
	
	/**
	 * 添加交易记录
	 * 
	 * @param content
	 * @throws Exception 
	 */
	public synchronized byte[] addBusinessRecord(byte[] data) throws Exception {
		String content = new String(data, 0, data.length, ENCODE_UTF8).trim();
		JSONObject json = JSONObject.fromObject(content);
		
		// 通过获取的代码查询相应的设备，网点和业务类型
		Device device_tmp = deviceService.getDeviceBySerial(json.getString("serial"));
		if(device_tmp == null){
			throw new Exception("设备序列号:"+json.getString("serial")+"不正确");
		}
		Outlets outlets_tmp = outletsService.getOutletsByCode(json.getString("outlets"));
		if(outlets_tmp == null){
			throw new Exception("网点代码:"+json.getString("outlets")+"不存在");
		}
		Business business_tmp = businessService.getBusinessByCode(json.getString("business"));
		if(business_tmp == null){
			throw new Exception("业务代码:"+json.getString("business")+"不存在");
		}
		
		if(device_tmp.getOutlets() != outlets_tmp.getId()){
			throw new Exception("网点代码与设备网点不相符");
		}
		
		//判断设备是否拥有此业务
		boolean isExist = false;
		String[] appIds = outlets_tmp.getApp().split(",");
		for(String id : appIds){
			if(id.trim().equals(( "" + business_tmp.getApp()).trim())){
				isExist = true;
				break;
			}
		}
		if(!isExist){
			throw new Exception("该设备还未添加此项业务");
		}
		
		String time = sdf.format(new Date());
		String outletsCode = json.getString("outlets");
		String code = businessRecordCode.getRecordCode(outletsCode);//排队号
		
		json.put("code", code);
		json.put("time", time);
		json.put("device", device_tmp.getId());
		json.put("outlets", outlets_tmp.getId());
		json.put("business", business_tmp.getId());
		businessRecordService.saveBusinessRecord(json);
		/*//湖北数据库兼容
		if(VersionService.HUBEIYOUZHENG.equals(VersionService.SYSTEM_VERSION)){
			transRecordService.saveTransRecord(json);
		}*/
		
		//1上线,0下线
		int onlineStatusId = deviceService.getDeviceStatusIdByCode(1);
		if(device_tmp.getStatus() != onlineStatusId){
			device_tmp.setStatus(onlineStatusId);
			deviceService.updateDevice(device_tmp);
		}
		checkDeviceStatusTask.put(json.getString("serial"), System.currentTimeMillis());
		
		// 客户信息防止错误影响业务报文正常回复
		try{
			customerService.addCustomerByBusinessRecord(json.getString("data"));
		}catch(Exception e){
			String errorMsg = "";
			if(e instanceof JDBCException){
				JDBCException exe = (JDBCException) e;
				errorMsg = exe.getSQLException().getMessage();
			}else{
				errorMsg = e.getMessage();
			}
			logger.error("添加客户信息出现异常："+errorMsg);
		}
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("code", code);
		resultJson.put("time",time);
		return createMsg( WORKBYTE, (byte) 0x01, resultJson.toString());
	}

}
