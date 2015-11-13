package com.centerm.socket.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * socket读取操作类 
 * 
 * @author ke
 *
 */
public class ClientUtil {
	
	private Logger logger = LogManager.getLogger(ClientUtil.class);
	
	private Socket client;
	private byte[] msgBytes;
	
	public ClientUtil(Socket client, byte[] msgBytes) {
		this.client = client;
		this.msgBytes = msgBytes;
	}

	public byte[] run() {
		
		InputStream in = null;
		OutputStream out = null;
		
		try {
			in = client.getInputStream();
			out = client.getOutputStream();
			
			if(msgBytes != null){
				out.write(msgBytes);
				out.flush();
			}
			
			byte[] readBytes = new byte[1024];
			ClientSession session = new ClientSession();
			while(client.isConnected()){
				int read = in.read(readBytes);
				try {
					if(read >= 0){
						byte[] temp = new byte[1024];
						System.arraycopy(readBytes, 0, temp, 0, read);
						// 读取到的字节添加到session中
						session.addBytes(temp);
						if(!session.isReadOver()){
							continue;
						}else{
							break;
						}
					}
				} catch (Exception e) {
					logger.error("对话异常关闭");
					break;
				}
			}
			
			return session.getMsgBytes();
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
		return null;
	}

}
