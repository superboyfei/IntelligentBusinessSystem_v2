package com.centerm.socket.util;

public class ClientSession 
{
	private static int LENGTH = 4;
	
	// 防止网络问题出现报文不完整接收
	private boolean length_readed = false;
	private byte[] lengthBytes = new byte[4];// 长度的字节数为4
	private int num_length_readed = 0;// 已读的长度
		
	private int num_msg = 0;
	private boolean msg_readed = false;// 已读的长度
	private byte[] msgBytes = null;
	
	/**
	 * 
	 * 读取报文字节数组
	 * @param bytes
	 * @throws Exception 
	 */
	public void addBytes(byte[] bytes) throws Exception{
		for(int i=0; i<bytes.length; i++){
			if( !length_readed ){
				// 长度信息未读完,读取长度信息
				lengthBytes[num_length_readed] = bytes[i];
				num_length_readed++;
				// 判断是否读满
				if(num_length_readed >= LENGTH){
					length_readed = true;
					
					// 初始化报文内容数组
					if(msgBytes == null){
						num_msg = byteArrayToInt(lengthBytes);
						if(num_msg < 0 || num_msg > 1024*1024*128){
							throw new Exception("报文的头字节包含的长度信息出现异常");
						}
						msgBytes = new byte[num_msg];
					}
				}
			}else{
				if(num_msg > 0){
					msgBytes[msgBytes.length - num_msg] = bytes[i];
					num_msg--;
					if(num_msg <= 0){
						msg_readed = true;
						break;
					}
				}
			}
		}
	}
	
	public static int byteArrayToInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < bytes.length; i++) {
			result = (bytes[i] & 0x000000ff) ^ result;
			if (i < bytes.length - 1) {
				result <<= 8;
			}
		}
		return result;
	}
	
	public byte[] getMsgBytes(){
		return msgBytes;
	}
	
	public boolean isReadOver(){
		return msg_readed;
	}
}
