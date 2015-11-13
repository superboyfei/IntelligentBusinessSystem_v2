package com.centerm.util;

import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 与柜台交互使用的3DES加密，解密类
 *
 */
public class ThreeDESTool
{
    // 定义加密算法,可用DES,DESede,Blowfish
    private static final String ALGORITHM = "DESede";
    // 定义加密模式
    private static final String ALGORITHM_MODE = "DESede/ECB/NoPadding";
    // 用于3des加密的24字节的密钥
    public static final byte[] KEYBYTES  = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10,0x40, 
        		0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD, 0x55,
        		0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2 };

    
    /**
     * 使用内置的密钥对数据进行加密，返回加密后的密文，字节不足8位，补0
     * 
     * @param src 源数据
     * @return 加密后的byte内容
     */
    public static byte[] encryptMode(byte[]keys, byte[] src) throws Exception
    {
    	byte[] newSrc = null;
    	if(src.length%8 != 0)
    	{
    		newSrc = new byte[src.length + 8 - src.length%8];
    		System.arraycopy(src, 0, newSrc, 0, src.length);
    	}
    	else
    	{
    		newSrc = src;
    	}
        SecretKey key = new SecretKeySpec(keys, ALGORITHM);  
        Cipher c = Cipher.getInstance(ALGORITHM_MODE);  
        c.init(Cipher.ENCRYPT_MODE, key);
        return c.doFinal(newSrc);
    }
    
    /**
     * 使用内置的密钥对加密内容进行解密，返回解密后的信息
     * 
     * @param src
     * @return
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     */
    public static byte[] decryptMode(byte[]keys, byte[] src) throws Exception
    {
    	SecretKey key = new SecretKeySpec(keys, ALGORITHM);  
        Cipher c = Cipher.getInstance(ALGORITHM_MODE);  
        c.init(Cipher.DECRYPT_MODE, key);
        return c.doFinal(src);
    }
    
    
}
