package com.centerm.msghandler.basehandler.counter.work;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.centerm.msghandler.basehandler.common.BaseWork;
import com.centerm.msghandler.basehandler.counter.CounterCombiner;
import com.centerm.service.device.CounterAppService;
import com.centerm.util.ByteUtil;
import com.centerm.util.ThreeDESTool;
import com.centerm.vo.CounterAppDownFileVo;

import net.sf.json.JSONObject;

@Component("telnetWork")
public class TelnetWork extends BaseWork {
	
	private static final byte WORKBYTE = 0x07;
	
	// 暂定telnet的code为telnet（未用）
	private static final String CODE = "telnet";
	
	@Resource(name = "counterAppService")
	private CounterAppService counterAppService;
	@Resource(name = "counterCombiner")
	private CounterCombiner counterCombiner;

	@Override
	public byte getWorkByte() {
		return WORKBYTE;
	}

	@Override
	public Map<Byte, String> getMsgTypeMap() {
		Map<Byte, String> map = new HashMap<Byte, String>();
		map.put((byte) 0x04, "compareVersion");
		return map;
	}
	
	/**
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public byte[] compareVersion(byte[] data) throws Exception {
		String content = new String(data, 0, data.length, ENCODE_UTF8).trim();
		JSONObject json = JSONObject.fromObject(content);
		
		if(json.get("version") != null 
				&& StringUtils.isNotBlank(json.getString("version"))) {
			
			String version = json.getString("version").trim();
			
			CounterAppDownFileVo downVo = counterAppService.findCounterAppForDownfile(CODE,version);
			if(downVo != null) {
				
				JSONObject ftpJson = getFtpMessage();
				JSONObject resultJson = JSONObject.fromObject(downVo);
				resultJson.putAll(ftpJson);
				return createMsg(WORKBYTE, (byte) 0x04, resultJson.toString());
			}
			
		} else {
			throw new Exception("报文信息不完整");
		}
		// 无需升级
		return createMsg(WORKBYTE, (byte) 0x00, new JSONObject().toString());
	}

	@Override
	public byte[] createMsg(byte work, byte method, String content)
			throws Exception {
		// 加密拆分，添加报文头部的其他内容
		logger.debug("telnet升级发出的报文内容：" + content);
		byte[] result = ThreeDESTool.encryptMode(ThreeDESTool.KEYBYTES, content.getBytes(ENCODE_UTF8));
		String resultStr = ByteUtil.bytesToHexString(result);
		result = resultStr.getBytes(ENCODE_UTF8);
		result = counterCombiner.combineMessage(new byte[]{work, method}, result);
		return result;
	}
}
