package com.centerm.socket.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.centerm.msghandler.IMessageCombiner;
import com.centerm.msghandler.basehandler.device.DeviceCombiner;
import com.centerm.util.MessageCrypter;

public class MsgUtil {
	private static Logger logger = LogManager.getLogger(MsgUtil.class);
	
	private static final String ENCODE = "UTF-8";
	private static IMessageCombiner combiner = new DeviceCombiner();

	public static byte[] createMsg(byte work, byte method, String content)
			throws Exception {
		byte[] msg = MessageCrypter.encrypt(content.getBytes(ENCODE));
		return combiner.combineMessage(new byte[] { work, method }, msg);
	}
	
	private static byte[] getMessageType(byte[] msg) {
		byte[] msgType = new byte[2];
		System.arraycopy(msg, 0, msgType, 0, msgType.length);
		return msgType;
	}
	
	private static byte[] getMessageData(byte[] msg, byte[] msgType) {
		byte[] msgData = new byte[msg.length - msgType.length - 1];
		System.arraycopy(msg, msgType.length, msgData, 0, msgData.length);
		return msgData;
	}
	
	/**
	 * 处理报文
	 * @param msgBytes
	 */
	public static void doWork(byte[] msgBytes) {
		byte[] typeBytes = getMessageType(msgBytes);
		byte[] dataBytes = getMessageData(msgBytes,typeBytes);
		
		try {
			dataBytes = MessageCrypter.decrypt(dataBytes);
			String content = new String(dataBytes,0,dataBytes.length, ENCODE).trim();
			logger.debug("type:"+typeBytes[0]+","+typeBytes[1]+";content:"+content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
