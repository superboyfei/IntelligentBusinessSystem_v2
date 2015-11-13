package com.centerm.util;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64
{
	private final static Logger logger = LogManager.getLogger(Base64.class);
	
	/**
	 * BASE64加密
	 * @param data	明文
	 * @return	密文
	 */
	public static String b64Crypt(String data)
	{
		BASE64Encoder b64Encoder = new BASE64Encoder();
		return b64Encoder.encode(data.getBytes());
	}
	
	/**
	 * BASE64解密
	 * @param keyData	密文
	 * @return	明文
	 */
	public static String b64Decrypt(String keyData)
	{
		keyData = keyData.replaceAll(" ", "");	//每76个字符需要去除换行符
		BASE64Decoder b64Decoder = new BASE64Decoder();
		
		try
		{
			byte[] bData = b64Decoder.decodeBuffer(keyData);
			return new String(bData);
		}
		catch(IOException ioe)
		{
			logger.error("BASE64解密异常", ioe);
			return null;
		}
	}
}
