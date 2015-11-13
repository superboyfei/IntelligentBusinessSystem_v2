package com.centerm.socket;

import java.net.Socket;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.centerm.BaseTest;
import com.centerm.network.socket.server.MsgReceiver;
import com.centerm.socket.util.ClientUtil;
import com.centerm.socket.util.MsgUtil;

public class FirmwareMsgTest extends BaseTest {

	@Resource(name = "msgReceiver")
	private MsgReceiver msgReceiver;
	
	public byte[] getFirmwareMsg() throws Exception {
		JSONObject json = new JSONObject();
		json.put("serial", "dev_1");
		json.put("code", 10012);
		json.put("version", "3");
		
		return MsgUtil.createMsg((byte) 0x04, (byte) 0x04, json.toString());
	}
	
	@Test
	public void sengMsg(){
		msgReceiver.startDeviceReceive();
		//连接socket
		try {
			Socket clientSocket = new Socket("192.168.2.64", 3131);
			ClientUtil client = new ClientUtil(clientSocket, getFirmwareMsg());
			byte[] receiveMsg = client.run();
			if(receiveMsg != null){
				MsgUtil.doWork(receiveMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
