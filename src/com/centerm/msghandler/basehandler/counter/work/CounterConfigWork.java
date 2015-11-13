package com.centerm.msghandler.basehandler.counter.work;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.centerm.entity.CounterConfig;
import com.centerm.entity.Outlets;
import com.centerm.msghandler.basehandler.common.BaseWork;
import com.centerm.msghandler.basehandler.counter.CounterCombiner;
import com.centerm.service.device.CounterConfigService;
import com.centerm.service.manage.outlets.OutletsService;
import com.centerm.util.ByteUtil;

import net.sf.json.JSONObject;

@Component("counterConfigWork")
public class CounterConfigWork extends BaseWork {
	
	private static final byte WORKBYTE = 0x08;
	private static final int CONTENT_LENGTH = 4;
	 
	private static final String PRIVATE = "private";
	private static final String PUBLIC = "public";
	
	@Resource(name = "outletsService")
	private OutletsService outletsService;
	@Resource(name = "counterConfigService")
	private CounterConfigService counterConfigService;
	@Resource(name = "counterCombiner")
	private CounterCombiner counterCombiner;

	@Override
	public byte getWorkByte() {
		return WORKBYTE;
	}

	@Override
	public Map<Byte, String> getMsgTypeMap() {
		Map<Byte, String> map = new HashMap<Byte, String>();
		map.put((byte) 0x05, "downloadPublicFile");
		map.put((byte) 0x06, "uploadPublicFile");
		map.put((byte) 0x07, "downloadPrivateFile");
		map.put((byte) 0x08, "uploadPrivateFile");
		return map;
	}
	
	/** 
	 * 获取配置文件的储存目录系统目录下的counterConfigFile文件夹
	 * @return
	 */
	private String getConfigFilePath(){
		String path = this.getClass().getClassLoader().getResource(File.separator).getPath();
		path = path.substring(0, path.indexOf("WEB-INF")) + "counterConfigFile";
		return path;
	}

	/**
	 * 下载对公配置文件
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public byte[] downloadPublicFile(byte[] data) throws Exception {
		String content = getContent(data);
		
		JSONObject json = JSONObject.fromObject(content);
		if(json.get("outlets") != null){
			String outletsCode = json.getString("outlets");
			
			// 获取配置文件目录
			List<Map<String,String>> list = (List<Map<String, String>>) counterConfigService.findPublicForDown(outletsCode);
			
			if(list != null && list.size() > 0){
				Map<String,String> map = list.get(0);
				byte[] fileBytes = getFileBytes(map);
				JSONObject jsonResult = new JSONObject();
				jsonResult.put("filesize", fileBytes.length);
				jsonResult.put("filename", map.get("name"));
				return createMsg(WORKBYTE, (byte) 0x05, jsonResult.toString(), fileBytes);
			} else {
				return noConfigFile();
			}
			
		} else {
			throw new Exception("报文信息不完整");
		}
	}
	
	/**
	 * 上传对公配置文件
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public byte[] uploadPublicFile(byte[] data) throws Exception {
		return uploadFile(data, PUBLIC, (byte) 0x06);
	}
	
	/**
	 * 下载对私配置文件
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public byte[] downloadPrivateFile(byte[] data) throws Exception{
		String content = getContent(data);
		
		JSONObject json = JSONObject.fromObject(content);
		if(json.get("outlets") != null && json.get("ip") != null){
			String outletsCode = json.getString("outlets");
			String ip = json.getString("ip");
			// 获取配置文件目录
			List<Map<String,String>> list = (List<Map<String, String>>) counterConfigService.findPrivateForDown(outletsCode, ip);
			
			if(list != null && list.size() > 0){
				Map<String,String> map = list.get(0);
				byte[] fileBytes = getFileBytes(map);
				JSONObject jsonResult = new JSONObject();
				jsonResult.put("filesize", fileBytes.length);
				jsonResult.put("filename", map.get("name"));
				return createMsg(WORKBYTE, (byte) 0x07, jsonResult.toString(), fileBytes);
			} else {
				return noConfigFile();
			}
			
		} else {
			throw new Exception("报文信息不完整");
		}
	}
	
	/**
	 * 上传对私配置文件
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public byte[] uploadPrivateFile(byte[] data) throws Exception{
		return uploadFile(data, PRIVATE, (byte) 0x08);
	}

	/**
	 * 文件上传处理
	 * @param data 报文字节数组
	 * @param type private or public
	 * @param method 报文类型字节
	 * @return
	 * @throws Exception
	 */
	public byte[] uploadFile(byte[] data, String type, byte method) throws Exception {
		boolean isPublic = type.equals(PUBLIC) ? true : false;
		String content = getContent(data);
		JSONObject json = JSONObject.fromObject(content);
		
		if(json.get("outlets") != null && json.get("filesize") != null && json.get("filename") != null){
			CounterConfig config = null;
			if(isPublic){
				//通过网点代码获取对公配置文件
				config = counterConfigService.findPublicByOutlets(json.getString("outlets").trim());
			} else {
				//通过网点代码和ip获取对私配置文件
				if(json.get("ip") != null){
					config = counterConfigService.findPrivateByOutletsAndIp(
							json.getString("outlets").trim(), json.getString("ip").trim());
				} else {
					throw new Exception("报文信息不完整");
				}
			}
			
			Date date = new Date();
			String fileName = json.getString("filename");
			Outlets outlets = outletsService.getOutletsByCode(json.getString("outlets").trim());
			if(outlets == null){
				throw new Exception("网点代码:"+json.getString("outlets")+"不存在");
			}
			//对公public文件夹下的网点代码，对私private文件夹下的网点代码文件夹下的ip文件夹
			String dir = File.separator + type + File.separator + json.getString("outlets").trim() 
					+ (isPublic ? "" : File.separator + json.getString("ip").trim());
			
			if(config == null){
				//如果没有记录就新增
				config = new CounterConfig();
				config.setFilepath(dir);
				config.setIp(isPublic ? "" : json.getString("ip").trim());
				config.setIsPublic(isPublic);
				config.setName(fileName);
				config.setOutlets(outlets.getId());
				config.setUploadtime(date);
				config.setUpdatetime(date);
				
				//获取文件数据并存储文件
				byte[] fileBytes = getFileBytesFromMsg(data, json.getInt("filesize"));
				if(fileBytes != null){
					saveConfigFile(fileBytes, type, fileName, dir);
				}
				
				counterConfigService.save(config);
				return createMsg(WORKBYTE, method, new JSONObject().toString());
				
			} else {
				//修改记录
				String oldFileName = config.getName();
				config.setOutlets(outlets.getId());
				byte[] fileBytes = getFileBytesFromMsg(data, json.getInt("filesize"));
				if(fileBytes != null){
					saveConfigFile(fileBytes, type, fileName, dir);
				}
				
				config.setName(fileName);
				config.setUpdatetime(date);
				counterConfigService.update(config);
				//删除旧的文件
				if(!oldFileName.equals(fileName)){
					File file = new File(getConfigFilePath() + dir + File.separator + oldFileName);
					file.delete();
				}
				return createMsg(WORKBYTE, method, new JSONObject().toString());
			}
		} else {
			throw new Exception("报文信息不完整");
		}
	}
	
	/**
	 * 从报文数据中读取文件数据
	 * @param data
	 * @param size
	 * @return
	 */
	private byte[] getFileBytesFromMsg(byte[] data, int size) {
		if(size != 0){
			int len2 = ByteUtil.byteArrayToLength(Arrays.copyOf(data, CONTENT_LENGTH));
			byte[] filebytes = new byte[size];
			System.arraycopy(data, CONTENT_LENGTH + len2, filebytes, 0, size);
			
			return filebytes;
		}else{
			return null;
		}
	}
	
	/**
	 * 保存文件
	 * @param fileBytes 文件数据
	 * @param type public or private
	 * @return
	 * @throws Exception
	 */
	private void saveConfigFile(byte[] fileBytes, String type, String fileName, String fileDir) throws Exception {
		FileOutputStream fileOutput = null;
		String path = getConfigFilePath() + fileDir;
		// 创建目录
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(getConfigFilePath() + fileDir + File.separator + fileName);
		try {
			fileOutput = new FileOutputStream(file);
			fileOutput.write(fileBytes);
			fileOutput.flush();
			
		} catch (Exception e) {
			fileName = "";
			throw new Exception("保存文件出错");
		}finally{
			try {
				if(fileOutput != null) {
					fileOutput.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	/**
	 * 有文件的报文组装
	 * @param work
	 * @param method
	 * @param content
	 * @param fileBytes
	 * @return
	 * @throws Exception
	 */
	public byte[] createMsg(byte work, byte method, String content, byte[] fileBytes) throws Exception {
		// 计算信息内容长度
		logger.debug("配置文件发出的报文内容：" + content);
		byte[] result = content.getBytes(ENCODE_GBK);
		byte[] len2 = ByteUtil.lengthToByteArray(result.length);
		result = ByteUtil.bytes3And(len2, result, fileBytes);
		
		result = counterCombiner.combineMessage(new byte[]{work, method}, result);
		return result;
	}
	
	/* 
	 * 无文件的报文组装
	 */
	@Override
	public byte[] createMsg(byte work, byte method, String content) throws Exception {
		logger.debug("配置文件发出的报文内容：" + content);
		byte[] result = content.getBytes(ENCODE_GBK);
		//信息内容的长度
		byte[] len2 = ByteUtil.lengthToByteArray(result.length);
		result = ByteUtil.bytes2And(len2, result);
		
		result = counterCombiner.combineMessage(new byte[]{work, method}, result);
		//return ByteUtil.bytes2And(COUNTER_HEADER, result);
		return result;
	}

	/**
	 * 无配置文件时返回的报文
	 * @return
	 * @throws Exception
	 */
	private byte[] noConfigFile() throws Exception{
		return createMsg(WORKBYTE, (byte) 0x67, new JSONObject().toString());
	}
	
	/**
	 * 通过map中的文件路径读取本地文件
	 * @param map
	 * @return
	 * @throws IOException
	 */
	private byte[] getFileBytes(Map<String,String> map) throws IOException{
		String path = getConfigFilePath();
		path += map.get("filepath")+ File.separator;
		File file = new File(path + map.get("name"));
		FileInputStream fileInput = new FileInputStream(file);
		BufferedInputStream bufInput = new BufferedInputStream(fileInput);
		byte[] fileBytes = new byte[(int) file.length()];
		bufInput.read(fileBytes);
		bufInput.close();
		fileInput.close();
		return fileBytes;
	}
	
	/**
	 * 取出报文中的信息内容
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getContent(byte[] data) throws UnsupportedEncodingException{
		// 获取长度
		byte[] contentLen = Arrays.copyOf(data, CONTENT_LENGTH);
		int length = ByteUtil.byteArrayToLength(contentLen);
		byte[] contentBytes = new byte[length];
		System.arraycopy(data, CONTENT_LENGTH, contentBytes, 0, length);
		return new String(contentBytes, ENCODE_GBK);
	}

}
