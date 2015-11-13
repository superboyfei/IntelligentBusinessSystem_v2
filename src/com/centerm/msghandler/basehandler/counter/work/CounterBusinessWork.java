package com.centerm.msghandler.basehandler.counter.work;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.centerm.entity.hubei.OutletsReflect;
import com.centerm.msghandler.IMessageAnalyzer;
import com.centerm.service.business.BusinessRecordService;
import com.centerm.service.business.hubei.OutletsReflectService;
import com.centerm.util.ByteUtil;
import com.centerm.util.ThreeDESTool;
import com.centerm.vo.CounterVo;

@Component("counterBusinessWork")
@Scope("prototype")
public class CounterBusinessWork implements IMessageAnalyzer {
	private static final Logger logger = LogManager.getLogger(CounterBusinessWork.class);
	private static final String ENCODE = "UTF-8";
	private String clientIp = "";
	
	@Resource(name="businessRecordService")
	private BusinessRecordService businessRecordService;
	@Resource(name="outletsReflectService")
	private OutletsReflectService outletsReflectService;

	@Override
	public byte[] analyzeMessage(byte[] msg) {
		String retMsg = null;
		byte[] result = null;
		byte[] header = new byte[]{};
		try {
			// 3DES解密
			msg = ByteUtil.hexStringToBytes(new String(msg,ENCODE));
			byte[] msgBody = ThreeDESTool.decryptMode(ThreeDESTool.KEYBYTES, msg);
			
			JSONObject json = JSONObject.fromObject(new String(msgBody));
			logger.debug("柜台端获取记录收到的报文:" + json.toString());
			retMsg = getCounterDataByCode(json);
			logger.debug("柜台端获取记录发出的报文:" + retMsg);
			byte[] retMsgBytes =  retMsg.getBytes(ENCODE);
			
			// 加密，拆分
			result = ThreeDESTool.encryptMode(ThreeDESTool.KEYBYTES,retMsgBytes);
			String resultStr = ByteUtil.bytesToHexString(result);
			result = resultStr.getBytes(ENCODE);
			
			header = new byte[]{(byte) 0x00ff,(byte) 0x00ff,(byte) 0x0080};
		} catch (Exception e) {
			logger.error("解密或解析报文出现异常："+e.getMessage());
			// 返回错误信息
			retMsg = e.getMessage();
			try {
				result = ThreeDESTool.encryptMode(ThreeDESTool.KEYBYTES, retMsg.getBytes(ENCODE));
				String resultStr = ByteUtil.bytesToHexString(result);
				result = resultStr.getBytes(ENCODE);
				header = new byte[]{(byte) 0x00ff,(byte) 0x00ff,(byte) 0x00ff};
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		byte[] headerLen = ByteUtil.lengthToByteArray(result.length);
		result = ByteUtil.bytes3And(header, headerLen, result);
		logger.debug("write out:" + result.length);
		return result;
	}
	
	public String getCounterDataByCode(JSONObject json) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String outletsCode = null;
		//湖北邮政
		if(json.get("queryorg") == null){
			if(!clientIp.isEmpty()){
				//从ip表中获取网点代码
				String ip = clientIp.replace("/", "");
				OutletsReflect outletsIp = outletsReflectService.findByIp(ip);
				if(outletsIp == null){
					throw new Exception("此IP:"+ip+"无对应的网点");
				}
				outletsCode = outletsIp.getCode();
			} else {
				throw new Exception("获取网点IP出现异常");
			}
		} else {
			outletsCode = json.getString("queryorg");
		}
		// 网点号，排队号和日期
		String code = json.getString("queryno");
		String tableDate = sdf.format(new Date());
		
		// 获取日期，网点代码和唯一码获取交易记录
		CounterVo record = businessRecordService.getCounterDataByCode(tableDate, outletsCode, code);
		if(record == null){
			throw new Exception("记录不存在");
		}
		
		String data = record.getData();
		JSONObject resultJson = new JSONObject();
		resultJson.put("record", data);
		
		return resultJson.toString();
		
	}

	@Override
	public void addClientIp(String ip) {
		this.clientIp = ip;
	}

	public String getClientIp() {
		return clientIp;
	}

}
