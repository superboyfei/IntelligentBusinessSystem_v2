package com.centerm.msghandler.basehandler.common;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.JDBCException;
import org.springframework.orm.hibernate4.HibernateJdbcException;

import com.centerm.listener.FtpServerListener;
import com.centerm.msghandler.IMessageCombiner;
import com.centerm.util.BeanFactory;
import com.centerm.util.ByteUtil;
import com.centerm.util.MessageCrypter;

public abstract class BaseWork implements IWork {
	public static final Logger logger = LogManager.getLogger(BaseWork.class);
	
	public static final byte[] COUNTER_HEADER = new byte[]{(byte) 0xff, 0x00, 0x00};

	private static final byte DEALERROR = 0x66;// 102
	
	public static final String ENCODE_UTF8 = "UTF-8";
	public static final String ENCODE_GBK = "GBK";

	// work中方法与报文类型的映射关系(对应的work类中自己添加)
	private static Map<Byte, Map<Byte, String>> TYPE_MAP = new HashMap<Byte, Map<Byte, String>>();

	@Resource(name = "beanFactory")
	private BeanFactory beanFactory;
	@Resource(name = "deviceCombiner")
	private IMessageCombiner combiner;

	/**
	 * 获取work在报文所代表的类型
	 * 
	 * @return
	 */
	public abstract byte getWorkByte();

	/**
	 * 获取work中方法与报文类型的映射关系
	 * 
	 * @return
	 */
	public abstract Map<Byte, String> getMsgTypeMap();

	/*
	 * 具体的报文解析处理方法
	 */
	@Override
	public byte[] doWork(byte[] type, byte[] data) {
		initWorkBean();

		byte workType = type[0];
		byte workMethodType = type[1];
		byte[] resultMsg = null;// 最终结果
		String errorMsg = null;// 错误内容

		try {
			
			String methodName = TYPE_MAP.get(workType).get(workMethodType);
			//logger.debug("收到的报文内容:"+data.length);
			String content = "";
			if(type[0] == 0x08){
				byte[] contentLen = Arrays.copyOf(data, 4);
				int length = ByteUtil.byteArrayToLength(contentLen);
				byte[] contentBytes = new byte[length];
				System.arraycopy(data, 4, contentBytes, 0, length);
				content = new String(contentBytes, ENCODE_GBK).trim();
			} else {
				content = new String(data, 0, data.length, ENCODE_UTF8).trim();
			}
			
			logger.debug("报文类型:"+type[0]+","+type[1]+";收到的报文内容:"+content);
			
			// 调用方法
			if (methodName != null) {
				resultMsg = (byte[]) MethodUtils.invokeMethod(this, methodName,
						data);
			} else {
				errorMsg = "无此类型的报文";
			}
			
			//logger.debug("write out:" + resultMsg.length);
			
		} catch (InvocationTargetException e) {
			
			Throwable throwable = e.getTargetException();
			if (throwable instanceof JSONException) {
				errorMsg = "报文中JSON格式不正确";
			} else if(throwable instanceof JDBCException) {
				JDBCException exe = (JDBCException) throwable;
				errorMsg = "数据库操作异常:" + exe.getSQLException().getMessage();
			} else if(throwable instanceof HibernateJdbcException) {
				errorMsg = "Hibernate执行数据库操作异常:" + throwable.getMessage();
			} else {
				errorMsg = throwable.getMessage();
			}
			
			logger.error("处理报文出现异常：" + errorMsg);

		} catch (NoSuchMethodException e) {
			errorMsg = "服务端报文解析类中对应的类型方法不正确";
			logger.error("处理报文出现异常：" + errorMsg);

		} catch (Exception e) {
			errorMsg = e.getMessage();
			if (e instanceof NoSuchMethodException) {
				errorMsg = "服务端报文解析类中对应的类型方法不正确";
			}
			logger.error("处理报文出现异常：" + errorMsg);

		} finally {
			if (errorMsg != null) {
				try {
					JSONObject json = new JSONObject();
					json.put("exception", errorMsg);
					return createMsg(workType, DEALERROR, json.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return resultMsg;
	}

	/**
	 * 初始化work的信息：方法和报文映射关系
	 */
	private void initWorkBean() {
		byte workByte = getWorkByte();
		if (TYPE_MAP.get(workByte) == null) {
			Map<Byte, String> methodToMsgMap = getMsgTypeMap();
			TYPE_MAP.put(workByte, methodToMsgMap);
		}
	}

	/**
	 * 获取ftp下载文件的必备信息
	 * 
	 * @return
	 */
	public JSONObject getFtpMessage() {
		JSONObject json = new JSONObject();
		int port = FtpServerListener.PORT;
		String username = FtpServerListener.USER_NAME;
		String password = FtpServerListener.USER_PASSWORD;

		json.put("port", port);
		json.put("username", username);
		json.put("password", password);
		return json;
	}

	/**
	 * 组装返回报文字节数组
	 * 
	 * @param work
	 *            类型的第一个字节
	 * @param method
	 *            类型的第二个字节
	 * @param content
	 *            报文内容
	 * @return
	 * @throws Exception
	 */
	public byte[] createMsg(byte work, byte method, String content)
			throws Exception {
		logger.debug("发出的报文内容：" + content);
		byte[] msg = MessageCrypter.encrypt(content.getBytes(ENCODE_UTF8));
		return combiner.combineMessage(new byte[] { work, method }, msg);
	}

}
