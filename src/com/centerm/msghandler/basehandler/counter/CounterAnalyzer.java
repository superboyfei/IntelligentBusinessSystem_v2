package com.centerm.msghandler.basehandler.counter;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.centerm.msghandler.IMessageAnalyzer;
import com.centerm.msghandler.IMessageCombiner;
import com.centerm.msghandler.basehandler.common.AnalyzerParams;
import com.centerm.msghandler.basehandler.common.IWork;
import com.centerm.msghandler.basehandler.counter.work.CounterBusinessWork;
import com.centerm.util.BeanFactory;
import com.centerm.util.ByteUtil;
import com.centerm.util.MessageCrypter;
import com.centerm.util.ThreeDESTool;

@Component("counterAnalyzer")
@Scope("prototype")
public class CounterAnalyzer implements IMessageAnalyzer{
	
	private static final Logger logger = LogManager.getLogger(CounterAnalyzer.class);
	
	private static Map<Byte, String> WORK_MAP = AnalyzerParams.COUNTER_WORK_MAP;
	
	private static final int MSG_TYPELENGTH = 2;
	
	private static final byte OPERATION = 0x00;
	private static final byte CHECKFAIL = 0x65;// 101
	private static final byte DEALERROR = 0x66;// 102
	
	@Resource(name = "beanFactory")
	private BeanFactory beanFactory;
	@Resource(name = "counterCombiner")
	private IMessageCombiner combiner;
	@Resource(name = "counterBusinessWork")
	private CounterBusinessWork counterBusinessWork;
	
	@Override
	public byte[] analyzeMessage(byte[] msg) {
		// 兼容处理：依照原来的报文头和格式
		byte[] header = Arrays.copyOf(msg, 3);
		msg = Arrays.copyOfRange(msg, 7, msg.length);
		if(!checkHeader(header)){
			return counterBusinessWork.analyzeMessage(msg);
		}
		
		// 依照终端报文格式，同时需要加报文头
		byte msgCheckValue = getMessageCheckValue(msg);
		byte[] msgType = getMessageType(msg);
		byte[] msgData = getMessageData(msg, msgType);
		
		//冗余校验数据
		byte[] checkData = new byte[msgType.length + msgData.length];	
		System.arraycopy(msgType, 0, checkData, 0, msgType.length);
		System.arraycopy(msgData, 0, checkData, msgType.length, msgData.length);
		
		byte checkValue = ByteUtil.RedundancyCheck(checkData);
		if ( checkValue != msgCheckValue )	
		{
			//冗余校验错误
			logger.debug(checkValue+":"+msgCheckValue);
			logger.debug("冗余校验错误");
			try {
				// 拆分再封装报文
				byte[] checkFailMsg = ThreeDESTool.encryptMode(ThreeDESTool.KEYBYTES, "{}".getBytes("utf-8"));
				return combiner.combineMessage(new byte[]{OPERATION,CHECKFAIL}, 
						checkFailMsg);
			} catch (Exception e) {
				logger.error("冗余校验错误的报文出现异常",e);
			}
		}
		
		byte workType = msgType[0];
		if(WORK_MAP.get(workType) == null){
			logger.error("报文解析类中未添加相应的处理类的映射");
			try {
				byte[] failMsg = failMsg("报文解析类中未添加相应的处理类的映射");
				return combiner.combineMessage(new byte[]{workType,DEALERROR}, 
						failMsg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(workType != (byte) 0x08){
			// 还原解密
			try {
				msgData = ByteUtil.hexStringToBytes(new String(msgData,"UTF-8"));
				msgData = ThreeDESTool.decryptMode(ThreeDESTool.KEYBYTES, msgData);
			} catch (Exception e) {
				logger.error("柜台报文解密出错",e);
				try {
					byte[] failMsg = failMsg("柜台报文解密出错");
					return combiner.combineMessage(new byte[]{workType,DEALERROR}, 
							failMsg);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		IWork work = (IWork) beanFactory.getBean(WORK_MAP.get(workType));
		return work.doWork(msgType, msgData);
	}

	/*
	 * 获得报文类型
	 */
	private byte[] getMessageType(byte[] msg)
	{
		byte[] msgType = new byte[MSG_TYPELENGTH];
		System.arraycopy(msg, 0, msgType, 0, msgType.length);
		return msgType;
	}
	
	/*
	 * 获得报文校验位
	 */
	private byte getMessageCheckValue(byte[] msg)
	{
		return msg[msg.length - 1];
	}
	
	/*
	 * 获得报文数据
	 */
	private byte[] getMessageData(byte[] msg, byte[] msgType)
	{
		byte[] msgData = new byte[msg.length - msgType.length - 1];
		System.arraycopy(msg, msgType.length, msgData, 0, msgData.length);
		return msgData;
	}
	
	private boolean checkHeader(byte[] header){
		if(header[0] == (byte) 0xff && header[1] == 0x00 && header[2] == 0x00){
			return true;
		}
		return false;
	}

	@Override
	public void addClientIp(String ip) {
		counterBusinessWork.addClientIp(ip);
	}
	
	public byte[] failMsg(String msg) throws Exception{
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("exception", msg);
		return MessageCrypter.encrypt(jsonmsg.toString().getBytes("utf-8"));
	}
}
