package com.centerm.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Des
{
	private static final Logger logger = LogManager.getLogger(Des.class);
	
	private static String des = "DES/ECB/NoPadding";	//des加、解密算法/模式/填充
	private static String triDes = "DESede/ECB/NoPadding";	//3des加、解密算法/模式/填充
	
	/**
	 * DES加密
	 * @param key	密钥
	 * @param data	明文
	 * @return	密文
	 */
	public static byte[] des_crypt(byte[] key, byte[] data)
	{
		try
		{
			KeySpec keySpec = new DESKeySpec(key);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
			
			Cipher cipher = Cipher.getInstance(des);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipher.doFinal(data);
		}
		catch(InvalidKeyException ike)
		{
			logger.error("DES加密异常", ike);
			return null;
		}
		catch(NoSuchAlgorithmException nsae)
		{
			logger.error("DES加密异常", nsae);
			return null;
		}
		catch(InvalidKeySpecException ikse)
		{
			logger.error("DES加密异常", ikse);
			return null;
		}
		catch(NoSuchPaddingException nspe)
		{
			logger.error("DES加密异常", nspe);
			return null;
		}
		catch(BadPaddingException bpe)
		{
			logger.error("DES加密异常", bpe);
			return null;
		}
		catch(IllegalBlockSizeException ibse)
		{
			logger.error("DES加密异常", ibse);
			return null;
		}
	}
	
	/**
	 * DES解密
	 * @param key	密钥
	 * @param data	密文
	 * @return	明文
	 */
	public static byte[] des_decrypt_(byte[] key, byte[] data)
	{
		try
		{
			KeySpec keySpec = new DESKeySpec(key);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
			
			Cipher cipher = Cipher.getInstance(des);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return cipher.doFinal(data);
		}
		catch(InvalidKeyException ike)
		{
			logger.error("DES解密异常", ike);
			return null;
		}
		catch(NoSuchAlgorithmException nsae)
		{
			logger.error("DES解密异常", nsae);
			return null;
		}
		catch(InvalidKeySpecException ikse)
		{
			logger.error("DES解密异常", ikse);
			return null;
		}
		catch(NoSuchPaddingException nspe)
		{
			logger.error("DES解密异常", nspe);
			return null;
		}
		catch(BadPaddingException bpe)
		{
			logger.error("DES解密异常", bpe);
			return null;
		}
		catch(IllegalBlockSizeException ibse)
		{
			logger.error("DES解密异常", ibse);
			return null;
		}
	}
	
	/**
	 * 3DES加密
	 * @param key	密钥
	 * @param data	明文
	 * @return	密文
	 */
	public static byte[] trides_crypt(byte[] key, byte[] data)
	{
		byte[] triKey = new byte[24];
		byte[] triData = null;
		
		if ( key.length == 16 )
		{
			System.arraycopy(key, 0, triKey, 0, key.length);
			System.arraycopy(key, 0, triKey, key.length, triKey.length - key.length);
		}
		else
		{
			System.arraycopy(key, 0, triKey, 0, triKey.length);
		}
		
		int dataLen = data.length;
		if ( dataLen % 8 != 0 )
		{
			dataLen = dataLen - dataLen % 8 + 8;
		}
		if ( dataLen != 0 )
		{
			triData = new byte[dataLen];
		}
		for ( int i = 0; i < dataLen; i++ )
		{
			triData[i] = (byte)0x00;
		}
		System.arraycopy(data, 0, triData, 0, data.length);
		
		try
		{
			KeySpec keySpec = new DESedeKeySpec(triKey);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
			
			Cipher cipher = Cipher.getInstance(triDes);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipher.doFinal(triData);
		}
		catch(InvalidKeyException ike)
		{
			logger.error("3DES加密异常",ike);
			return null;
		}
		catch(NoSuchAlgorithmException nsae)
		{
			logger.error("3DES加密异常", nsae);
			return null;
		}
		catch(InvalidKeySpecException ikse)
		{
			logger.error("3DES加密异常", ikse);
			return null;
		}
		catch(NoSuchPaddingException nspe)
		{
			logger.error("3DES加密异常", nspe);
			return null;
		}
		catch(BadPaddingException bpe)
		{
			logger.error("3DES加密异常", bpe);
			return null;
		}
		catch(IllegalBlockSizeException ibse)
		{
			logger.error("3DES加密异常", ibse);
			return null;
		}
	}
	
	/**
	 * 3DES解密
	 * @param key	密钥
	 * @param data	密文
	 * @return	明文
	 */
	public static byte[] trides_decrypt(byte[] key, byte[] data)
	{
		byte[] triKey = new byte[24];
		byte[] triData = null;
		
		if ( key.length == 16 )
		{
			System.arraycopy(key, 0, triKey, 0, key.length);
			System.arraycopy(key, 0, triKey, key.length, triKey.length - key.length);
		}
		else
		{
			System.arraycopy(key, 0, triKey, 0, triKey.length);
		}
		
		int dataLen = data.length;
		if ( dataLen % 8 != 0 )
		{
			dataLen = dataLen - dataLen % 8 + 8;
		}
		if ( dataLen != 0 )
		{
			triData = new byte[dataLen];
		}
		for ( int i = 0; i < dataLen; i++ )
		{
			triData[i] = (byte)0x00;
		}
		System.arraycopy(data, 0, triData, 0, data.length);
		
		try
		{
			KeySpec keySpec = new DESedeKeySpec(triKey);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
			
			Cipher cipher = Cipher.getInstance(triDes);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return cipher.doFinal(triData);
		}
		catch( InvalidKeyException ike )
		{
			logger.error("3DES解密异常", ike);
			return null;
		}
		catch(NoSuchAlgorithmException nsae)
		{
			logger.error("3DES解密异常", nsae);
			return null;
		}
		catch(InvalidKeySpecException ikse)
		{
			logger.error("3DES解密异常", ikse);
			return null;
		}
		catch(NoSuchPaddingException nspe)
		{
			logger.error("3DES解密异常", nspe);
			return null;
		}
		catch(BadPaddingException bpe)
		{
			logger.error("3DES解密异常", bpe);
			return null;
		}
		catch(IllegalBlockSizeException ibse)
		{
			logger.error("3DES解密异常", ibse);
			return null;
		}
	}
}
