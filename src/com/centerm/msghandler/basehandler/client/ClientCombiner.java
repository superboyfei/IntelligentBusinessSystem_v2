package com.centerm.msghandler.basehandler.client;

import org.springframework.stereotype.Component;

import com.centerm.msghandler.basehandler.device.DeviceCombiner;


/**
 * 主动发送报文组装类
 *
 */
@Component("clientCombiner")
public class ClientCombiner extends DeviceCombiner{
	
	/**
	 * 要求状态更新
	 * @return
	 */
	public byte[] updateStatus(){
		return combineMessage(new byte[]{1,2},new byte[]{});
	}
	
	/**
	 * 对比固件版本
	 * @return
	 */
	public byte[] compareFirmware(){
		return combineMessage(new byte[]{4,4},new byte[]{});
	}
	
	/**
	 * 对比应用版本
	 * @return
	 */
	public byte[] compareApp(){
		return combineMessage(new byte[]{5,4},new byte[]{});
	}
	
	/**
	 * 对比资源版本
	 * @return
	 */
	public byte[] compareResource(){
		return combineMessage(new byte[]{6,4},new byte[]{});
	}
	
	/**
	 * 网点变更
	 * @return
	 */
	public byte[] updateOutlets(){
		return combineMessage(new byte[]{2,2},new byte[]{});
	}
	
	/**
	 * 清空注册信息
	 * @return
	 */
	public byte[] clearRegisteMsg(){
		return combineMessage(new byte[]{0,10},new byte[]{});
	}
	
}
