package com.centerm.util;

import java.io.UnsupportedEncodingException;

public class MessageCrypter {
	// 用于3des加密的24字节的密钥
    private static final byte[] KEYBYTES  = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10,0x40, 
        		0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD, 0x55,
        		0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2 };
    
    private static final String ENCODE = "UTF-8";
    
    /**
     * 报文转换和解密
     * @param orignMessage
     * @return
     * @throws Exception 
     */
    public static byte[] decrypt(byte[] data) throws Exception{
    	try {
			byte[] beforeDecrypt = ByteUtil.hexStringToBytes(new String(data,ENCODE));
			byte[] afterDecrypt = Des.trides_decrypt(KEYBYTES, beforeDecrypt);
			return afterDecrypt;
		} catch (UnsupportedEncodingException e) {
			throw new Exception("解密出现异常:"+e.getMessage());
		}
    }
    
    /**
     * 加密并且拆分
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data) throws Exception{
    	byte[] afterCrypt = Des.trides_crypt(KEYBYTES,data);
    	try {
			byte[] afterChange = ByteUtil.bytesToHexString(afterCrypt).getBytes(ENCODE);
			return afterChange;
		} catch (UnsupportedEncodingException e) {
			throw new Exception("加密出现异常:"+e.getMessage());
		}
    }
}
