package com.centerm.socket;

import java.net.Socket;

import javax.annotation.Resource;

import org.junit.Test;

import net.sf.json.JSONObject;

import com.centerm.BaseTest;
import com.centerm.network.socket.server.MsgReceiver;
import com.centerm.socket.util.ClientUtil;
import com.centerm.socket.util.MsgUtil;

public class AppMsgTest extends BaseTest{
	
	@Resource(name = "msgReceiver")
	private MsgReceiver msgReceiver;
	
	public byte[] getCompareMsg() throws Exception{
		JSONObject json = new JSONObject();
		json.put("serial", "dev_1");
		return MsgUtil.createMsg((byte) 0x05, (byte) 0x04, json.toString());
	}
	
	public byte[] getDownMsg() throws Exception{
		JSONObject json = new JSONObject();
		json.put("version", "1.00.00");
		json.put("code", "0123");
		return MsgUtil.createMsg((byte) 0x05, (byte) 0x05, json.toString());
	}
	
	@Test
	public void compareVersionTest(){
		msgReceiver.startDeviceReceive();
		//连接socket
		try {
			Socket clientSocket = new Socket("192.168.2.64", 3131);
			ClientUtil client = new ClientUtil(clientSocket, getDownMsg());
			byte[] receiveMsg = client.run();
			if(receiveMsg != null){
				MsgUtil.doWork(receiveMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
