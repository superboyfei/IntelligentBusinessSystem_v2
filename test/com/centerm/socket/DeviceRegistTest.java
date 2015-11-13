package com.centerm.socket;

import java.net.Socket;

import javax.annotation.Resource;

import org.junit.Test;

import net.sf.json.JSONObject;

import com.centerm.BaseTest;
import com.centerm.network.socket.server.MsgReceiver;
import com.centerm.socket.util.ClientUtil;
import com.centerm.socket.util.MsgUtil;

public class DeviceRegistTest extends BaseTest {
	public static final String ENCODE = "UTF-8";
	public static final String DEV = "dev_a1";
	
	@Resource(name = "msgReceiver")
	private MsgReceiver msgReceiver;

	public byte[] getRegistMsg() throws Exception {
		JSONObject json = new JSONObject();
		json.put("serial", "dev_a1");
		json.put("type", "E10-2");
		json.put("outlets", "000002");
		json.put("ip", "192.168.2.64");
		return MsgUtil.createMsg((byte) 0x01, (byte) 0x01, json.toString());
	}
	
	@Test
	public void sendMsg() {
		msgReceiver.startDeviceReceive();
		//连接socket
		try {
			Socket clientSocket = new Socket("192.168.2.64", 3131);
			ClientUtil client = new ClientUtil(clientSocket, getRegistMsg());
			byte[] receiveMsg = client.run();
			if(receiveMsg != null){
				MsgUtil.doWork(receiveMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
