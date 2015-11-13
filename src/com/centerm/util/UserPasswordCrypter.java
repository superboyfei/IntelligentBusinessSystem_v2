package com.centerm.util;

public class UserPasswordCrypter
{
	/**
	 * 加密
	 * @param password	密码明文
	 * @return	密码密文
	 */
	public static String crypt(String password)
	{
		String keyPassword = Base64.b64Crypt(password);
		return keyPassword;
	}
	
	/**
	 * 解密
	 * @param keyPassword	密码密文
	 * @return	密码明文
	 */
	public static String decrypt(String keyPassword)
	{
		String password = Base64.b64Decrypt(keyPassword);
		return password;
	}
}
