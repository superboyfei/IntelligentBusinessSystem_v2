package com.centerm.socket;

import java.net.Socket;

import javax.annotation.Resource;

import org.junit.Test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.centerm.BaseTest;
import com.centerm.network.socket.server.MsgReceiver;
import com.centerm.socket.util.ClientUtil;
import com.centerm.socket.util.MsgUtil;

public class BusinessRecordTest extends BaseTest{
	
	@Resource(name = "msgReceiver")
	private MsgReceiver msgReceiver;
	
	public byte[] getBusinessRecordMsg() throws Exception{
		JSONArray array = new JSONArray();
		JSONObject dataJson = new JSONObject();
		dataJson.put("account", "62220214053210");
		dataJson.put("ID_num", "330326199105140622");
		dataJson.put("mobile", "18259067964");
		dataJson.put("name", "张三");
		array.add(dataJson);
		
		JSONObject json = new JSONObject();
		json.put("serial","dev_1");
		json.put("business","1001");
		json.put("outlets","000002");
		json.put("data", array.toString());
		
		return MsgUtil.createMsg((byte) 0x03,(byte) 0x01, json.toString());
	}
	
	@Test
	public void sendMsg(){
		msgReceiver.startDeviceReceive();
		//连接socket
		try {
			Socket clientSocket = new Socket("192.168.2.64", 3131);
			ClientUtil client = new ClientUtil(clientSocket, getBusinessRecordMsg());
			byte[] receiveMsg = client.run();
			if(receiveMsg != null){
				MsgUtil.doWork(receiveMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
