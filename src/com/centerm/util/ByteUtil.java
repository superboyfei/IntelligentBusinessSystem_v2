package com.centerm.util;


public class ByteUtil 
{
	/**
	 * 单个byte拆分转换操作
	 * 
	 * @param oldbyte
	 * @return byte[]
	 */
	public static byte[] byteToByte2(byte oldbyte) {
		byte[] result = new byte[2];
		// 拆分
		byte first = 0;
		// 符号位
		if (oldbyte > 0) {
			first = (byte) (oldbyte >> 4);
		} else {
			first = (byte) ((byte) (oldbyte >> 4) & 0x0f);
		}

		byte second = (byte) (oldbyte & 0x0f);
		// 添加字节例：0x16 -> 0x31,0x36
		first = (byte) (first ^ 0x30);
		second = (byte) (second ^ 0x30);
		result[0] = first;
		result[1] = second;

		return result;
	}

	/**
	 * byte数组转换
	 * 
	 * @param oldbytes
	 * @return
	 */
	public static byte[] turnByteArray(byte[] oldbytes) {

		if (oldbytes != null && oldbytes.length > 0) {
			byte[] result = new byte[2 * oldbytes.length];

			for (int i = 0; i < oldbytes.length; i++) {
				byte[] bytes = byteToByte2(oldbytes[i]);
				result[2 * i] = bytes[0];
				result[2 * i + 1] = bytes[1];
			}

			return result;
		} else {
			return null;
		}
	}

	/**
	 * 还原单个byte
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte byte2ToByte(byte[] bytes) {

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) (bytes[i] & 0x0f);
		}
		// 合并
		bytes[0] = (byte) (bytes[0] << 4);
		byte result = (byte) (bytes[0] ^ bytes[1]);

		return result;
	}

	/**
	 * 还原bytes数组
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] byteArrayTurnBack(byte[] bytes) {
		if (bytes != null && (bytes.length / 2) > 0) {
			int length = bytes.length / 2;
			byte[] result = new byte[length];
			for (int i = 0; i < length; i++) {
				byte[] temp = new byte[] { bytes[2 * i], bytes[2 * i + 1] };
				result[i] = byte2ToByte(temp);
			}

			return result;
		} else {

			return null;
		}
	}

	/**
	 * 十六进制形式输出byte[]
	 * 1->2
	 * @param b
	 */
	public static String bytesToHexString(byte[] bytes) {
		if (bytes == null)
		{
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < bytes.length; i++)
		{
			String hex = Integer.toHexString(bytes[i] & 0xff);
			if (hex.length() == 1)
			{
				hex = '0' + hex;
			}
			builder.append(hex.toUpperCase());
		}
		return builder.toString();
	}
	
	/**
	 * 转变十六进制形式的byte[]
	 * 2->1
	 * @param orign
	 * @return
	 */
	public static byte[] hexStringToBytes(String orign){
		int length = orign.length()/2;
		byte[] result = new byte[length];
		for(int i=0; i<length; i++){
			result[i] = (byte) Integer.parseInt(orign.substring(i*2, i*2+2),16);
		}
		return result;
	}
	
	/**
	 * 将byte数组转换为int
	 * @param byteArray	byte数组
	 * @return	int数值
	 */
	public static int byteArrayToLength(byte[] byteArray)
	{
		int result = 0;
		for ( int i = 0; i < byteArray.length; i++ )
		{
			result = (byteArray[i] & 0x000000FF) ^ result;
			if ( i < byteArray.length - 1 )
			{
				result <<= 8;
			}
		}
		
		return result;
	}
	
	/**
	 * 将int转换为byte数组
	 * @param length	int数值
	 * @return	byte数组
	 */
	public static byte[] lengthToByteArray(int length)
	{
		byte[] byteArray = new byte[4];
		byteArray[0] = (byte)(length >>> 24);
		byteArray[1] = (byte)(length >>> 16);
		byteArray[2] = (byte)(length >>> 8);
		byteArray[3] = (byte)(length);
		return byteArray;
	}

	public static byte[] makeTypeByteArray(int left,int right) {
		byte[] result = new byte[2];
		result[0] = (byte) left;
		result[1] = (byte) right;
		return result;
	}

	/**
	 * 合并数组
	 * @param left
	 * @param right
	 * @return
	 */
	public static byte[] bytes2And(byte[] left, byte[] right) {
		byte[] result = new byte[left.length + right.length];
		System.arraycopy(left, 0, result, 0, left.length);
		System.arraycopy(right, 0, result, left.length, right.length);
		return result;
	}

	/**
	 * 合并数组
	 * @param left
	 * @param right
	 * @return
	 */
	public static byte[] bytes3And(byte[] left, byte[] middle, byte[] right) {
		byte[] result = new byte[left.length + middle.length + right.length];
		System.arraycopy(left, 0, result, 0, left.length);
		System.arraycopy(middle, 0, result, left.length, middle.length);
		System.arraycopy(right, 0, result, left.length+middle.length, right.length);
		return result;
	}
	
	/**
	 * 计算冗余校验值
	 * @param data	校验数据
	 * @return	校验值
	 */
	public static byte RedundancyCheck(byte[] data)
	{
		byte checkCode = (byte)0x00;
		for ( int i = 0; i < data.length; i++ )
		{
			checkCode = (byte)(checkCode ^ data[i]);
		}
		
		return checkCode;
	}
	
}
