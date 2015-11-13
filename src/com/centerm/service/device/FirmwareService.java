package com.centerm.service.device;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.DeviceDao;
import com.centerm.dao.FirmwareDao;
import com.centerm.dao.FirmwareTypeDao;
import com.centerm.entity.Device;
import com.centerm.entity.Firmware;
import com.centerm.entity.FirmwareType;
import com.centerm.entity.po.FileXmlPo;
import com.centerm.entity.po.FirmwareXmlPo;
import com.centerm.listener.FtpServerListener;
import com.centerm.util.ByteUtil;
import com.centerm.util.JaxbUtil;
import com.centerm.util.StringUtil;
import com.centerm.vo.FirmwareDownfileVo;
import com.opensymphony.xwork2.ActionContext;

@Service("firmwareService")
public class FirmwareService
{
	private static final Logger logger = LogManager.getLogger(FirmwareService.class);
	
	private static final int DOWN_LENGTH = 1024*1024*10; //10M
	
	@Resource(name = "firmwareDao")
	private FirmwareDao firmwareDao;
	@Resource(name = "firmwareTypeDao")
	private FirmwareTypeDao firmwareTypeDao;
	@Resource(name = "deviceDao")
	private DeviceDao deviceDao;
	
	/**
	 * 获得所有固件类型列表
	 * @return	固件类型列表，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<FirmwareType> getAllFirmwareType()
	{
		List<FirmwareType> firmwareTypeList = firmwareTypeDao.findAll();
		if ( firmwareTypeList == null )
		{
			logger.debug("firmwareTypeList为null");
		}
		
		return firmwareTypeList;
	}
	
	/**
	 * 通过固件code获得固件升级日志
	 * @param code	固件code
	 * @return	固件升级日志内容，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public String getDescriptionByCode(String code)
	{
		if ( code == null )
		{
			logger.debug("code为null");
			return null;
		}
		
		FirmwareType firmwareType = firmwareTypeDao.queryFirmwareTypeByCode(code);
		if ( firmwareType == null )
		{
			logger.debug("firmwareType为null");
			return null;
		}
		
		return firmwareType.getDescription();
	}
	
	/**
	 * 删除指定id的固件类型
	 * @param id	固件类型id
	 * @return
	 * true - 删除成功<br>
	 * false - 删除失败
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteFirmwareTypeById(Integer id)
	{
		FirmwareType firmwareType = firmwareTypeDao.findById(id);
		List<Device> deviceList = deviceDao.findAll();
		List<?> firmwareList = firmwareDao.queryFirmwareByType(id);
		if ( firmwareList == null )
		{
			logger.debug("firmwareList为null");
			return false;
		}
		String deviceFirmware = "";
		if(!deviceList.isEmpty())
		{
			Device device = deviceList.get(0);
			deviceFirmware = device.getFirmware();
		}
		String code = firmwareType.getCode();
		for ( int i = 0; i < firmwareList.size(); i++ )
		{
			Firmware firmware = (Firmware)firmwareList.get(i);
			Integer firmwareId = firmware.getId();
			if ( deviceFirmware.indexOf(code + "_" + firmwareId) == 0 )
			{
				deviceFirmware = deviceFirmware.replace(code + "_" + firmwareId, "");	//移除无效的固件id
				if ( deviceFirmware.indexOf(",") == 0 )	//如果以逗号开头，则把开头的逗号移除
				{
					deviceFirmware = deviceFirmware.substring(1);
				}
			}
			else	//当id不在开头时，需要多删除一个逗号
			{
				deviceFirmware = deviceFirmware.replace("," + code + "_" + firmwareId, "");	//移除无效的固件id
			}
			firmware.setStatus(0);	//将固件status置为不可用状态
			firmwareDao.update(firmware);
		}
		deviceDao.updateAllDeviceFirmware(deviceFirmware);
		
		firmwareTypeDao.delete(firmwareType);	//删除固件类型记录
		return true;
	}
	
	/**
	 * 解析固件dat包
	 * @param firmwareFile	dat文件
	 * @param firmwareFileName	dat文件名
	 * @return	固件信息头对象，如果失败则为null
	 * @throws Exception
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public FirmwareXmlPo parseDatFile(File firmwareFile, String firmwareFileName) throws Exception
	{
		RandomAccessFile raf = new RandomAccessFile(firmwareFile, "rw");
		
		byte[] datHead = new byte[32];	//数据头
		raf.read(datHead);
		String head = new String(datHead).trim();
		logger.debug("数据头：" + head);
		
		FirmwareXmlPo firmwareXmlPo = new FirmwareXmlPo();
		if ( head.equals("firmware") )
		{
			byte[] datDataSize = new byte[4];	//数据文件大小
			raf.read(datDataSize);
			int dataSize = ByteUtil.byteArrayToLength(datDataSize);
			logger.debug("数据长度：" + dataSize);
			
			if ( dataSize + datHead.length + datDataSize.length == firmwareFile.length() )
			{
				byte[] datInfoHeadSize = new byte[4];	//信息头文件大小
				raf.read(datInfoHeadSize);
				int infoHeadSize = ByteUtil.byteArrayToLength(datInfoHeadSize);
				
				byte[] datInfoHead = new byte[infoHeadSize];	//信息头
				raf.read(datInfoHead);
				firmwareXmlPo = (FirmwareXmlPo)JaxbUtil.convertToObj(FirmwareXmlPo.class, datInfoHead);
				if ( firmwareXmlPo == null )
				{
					logger.debug("firmwareXmlPo为null");
					raf.close();
					return firmwareXmlPo;
				}
				
				//更新日志必须在180个字符内。
				String desTmp = firmwareXmlPo.getDescription();
				if(desTmp.getBytes("GBK").length > 512){
					firmwareXmlPo.setResult(false);
					firmwareXmlPo.setErrorMsg("error_上传的文件更新说明必须少于512个字符(256个汉字)");
					raf.close();
					return firmwareXmlPo;
				}
				
				MessageDigest digest = MessageDigest.getInstance("md5");
				List<Firmware> firmwareList = new ArrayList<Firmware>();
				for ( FileXmlPo fileXmlPo : firmwareXmlPo.getFileList().getFile() )	//循环解析每个固件文件
				{
					digest.reset();
					int fileSize = Integer.parseInt(fileXmlPo.getSize());	//固件文件大小
					byte[] fileData = new byte[fileSize];	//固件文件内容
					
					byte[] tmp = new byte[DOWN_LENGTH];
					int nums = 0;
					int lastRead = 0;
					while(nums < fileSize){
						if(nums + DOWN_LENGTH > fileSize){
							lastRead = fileSize - nums;
							raf.read(tmp);
							break;
						}
						raf.read(tmp);
						System.arraycopy(tmp, 0, fileData, nums, DOWN_LENGTH);
						nums += DOWN_LENGTH;
					}
					if(lastRead > 0){
						System.arraycopy(tmp, 0, fileData, nums, lastRead);
					}
					
					//raf.read(fileData);
					digest.update(fileData);	//计算MD5值
					String digestMd5 = ByteUtil.bytesToHexString(digest.digest());
					logger.debug("计算MD5值：" + digestMd5.toUpperCase());
					String md5 = fileXmlPo.getMd5();	//文件MD5值
					logger.debug("文件MD5值：" + md5.toUpperCase());
					
					FirmwareType firmwareType = firmwareTypeDao.queryFirmwareTypeByCode(firmwareXmlPo.getFirmwareCode());
					if ( digestMd5.equalsIgnoreCase(md5) )
					{
						String fileName = fileXmlPo.getName();	//文件名
						String tmpPath = FtpServerListener.USER_DIR + File.separator + "firmware" + File.separator + "tmp";	//固件临时目录
						new File(tmpPath).mkdirs();
						File file = new File(tmpPath + File.separator + fileName);	//先将固件文件保存在临时目录
						FileOutputStream output = new FileOutputStream(file);
						byte[] file_tmp = new byte[DOWN_LENGTH];
						nums = 0;
						lastRead = 0;
						while(nums < fileSize){
							if(nums + DOWN_LENGTH > fileSize){
								lastRead = fileSize - nums;
								break;
							}
							System.arraycopy(fileData, nums, file_tmp, 0, DOWN_LENGTH);
							logger.debug("nums:"+nums);
							output.write(file_tmp);
							nums += DOWN_LENGTH;
						}
						if(lastRead > 0){
							byte[] last_tmp = new byte[lastRead];
							System.arraycopy(fileData, nums, last_tmp, 0, lastRead);
							output.write(last_tmp);
						}
						output.flush();
						output.close();
						//FileUtils.writeByteArrayToFile(file, fileData);
						if(firmwareType != null) {
							
							Firmware firmware_tmp = firmwareDao.findByVersionAndLastVersionAndType(
									firmwareXmlPo.getVersion(), firmwareXmlPo.getLastVersion(), firmwareType.getId());
							if(firmware_tmp != null) {
								//已有文件记录
								firmwareXmlPo.setResult(false);
								firmwareXmlPo.setErrorMsg("warning_上传的文件服务端已存在，请勿重复上传");
								raf.close();
								return firmwareXmlPo;
							}
							
							if(!firmwareType.getVersion().equals(firmwareXmlPo.getLastVersion())) {
								firmwareXmlPo.setResult(false);
								firmwareXmlPo.setErrorMsg("error_上传的文件适用版本不存在");
								raf.close();
								return firmwareXmlPo;
							}
						}
						
						Firmware firmware = new Firmware();
						firmware.setName(fileName);
						firmware.setIsmust(firmwareXmlPo.getIsmust());
						firmware.setVersion(firmwareXmlPo.getVersion());
						firmware.setLastversion(firmwareXmlPo.getLastVersion());
						firmware.setStatus(1);
						firmware.setDeletetime(DateUtils.parseDate("9999-12-31", "yyyy-MM-dd"));
						firmware.setFilepath(File.separator + "firmware");
						firmware.setMd5(md5.toUpperCase());
						firmwareList.add(firmware);
					}
					else	//MD5校验错误
					{
						firmwareXmlPo.setResult(false);
						firmwareXmlPo.setErrorMsg("error_文件MD5值校验错误");
						raf.close();
						
						String tmpPath = FtpServerListener.USER_DIR + File.separator + "firmware" + File.separator + "tmp";	//固件临时目录
						File tmpDir = new File(tmpPath);
						String[] tmpFiles = tmpDir.list();	//遍历临时固件目录
						for ( String tmpFile : tmpFiles )
						{
							File tmpFirmware = new File(tmpPath + File.separator + tmpFile);
							if ( tmpFirmware.isFile() )
							{
								FileUtils.deleteQuietly(tmpFirmware);	//删除临时固件文件
							}
						}
						
						return firmwareXmlPo;
					}
				}
				ActionContext.getContext().getSession().put("firmwareList", firmwareList);	//将固件文件信息暂时保存在session中
			}
			else	//数据长度不匹配
			{
				firmwareXmlPo.setResult(false);
				firmwareXmlPo.setErrorMsg("error_数据长度不匹配");
				raf.close();
				return firmwareXmlPo;
			}
		}
		else	//数据头不合法
		{
			firmwareXmlPo.setResult(false);
			firmwareXmlPo.setErrorMsg("error_上传的文件非固件文件");
			raf.close();
			return firmwareXmlPo;
		}
		
		firmwareXmlPo.setResult(true);
		raf.close();
		return firmwareXmlPo;
	}
	
	/**
	 * 通过固件code获得固件类型
	 * @param code	固件code
	 * @return	固件类型，如果不存在则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public FirmwareType getFirmwareTypeByCode(String code)
	{
		FirmwareType firmwareType = firmwareTypeDao.queryFirmwareTypeByCode(code);
		if ( firmwareType == null )
		{
			logger.debug("firmwareType为null");
		}
		
		return firmwareType;
	}
	
	/**
	 * 保存固件信息
	 * @return
	 * true - 保存成功<br>
	 * false - 保存失败
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean saveFirmwareInfo() throws Exception
	{
		FirmwareXmlPo firmwareXmlPo = (FirmwareXmlPo)ActionContext.getContext().getSession().get("firmwareXmlPo");
		String code = firmwareXmlPo.getFirmwareCode();
		Integer type;	//固件类型id
		boolean isAdd;	//是否为新增固件
		FirmwareType firmwareType = firmwareTypeDao.queryFirmwareTypeByCode(code);
		if ( firmwareType == null )
		{
			logger.debug("新增firmwareType");
			isAdd = true;
			
			firmwareType = new FirmwareType();
			firmwareType.setName(firmwareXmlPo.getName());
			firmwareType.setCode(code);
			firmwareType.setVersion(firmwareXmlPo.getVersion());
			firmwareType.setUpdatetime(new Date());
			firmwareType.setDescription("&" + firmwareXmlPo.getVersion() + "&%" + StringUtil.killNullToSpace(firmwareXmlPo.getDescription()));
			type = (Integer)firmwareTypeDao.save(firmwareType);
			
			if ( type == null )
			{
				logger.debug("保存固件类型信息失败");
				return false;
			}
		}
		else
		{
			logger.debug("更新firmwareType");
			isAdd = false;
			
			firmwareType.setName(firmwareXmlPo.getName());
			firmwareType.setCode(code);
			firmwareType.setVersion(firmwareXmlPo.getVersion());
			firmwareType.setUpdatetime(new Date());
			
			//只取最近八次的更新日志
			String[] firmDes = firmwareType.getDescription().split("%&");
			String newFirmDes = firmwareType.getDescription();
			if(firmDes.length >= 8) {
				newFirmDes = "";
				for(int i=0; i<firmDes.length-1; i++){
					if(i == firmDes.length-2) {
						newFirmDes = newFirmDes + firmDes[i];
					} else {
						newFirmDes = newFirmDes + firmDes[i] + "%&";
					}
					
				}
			}
				
			firmwareType.setDescription("&" + firmwareXmlPo.getVersion() + "&%" + StringUtil.killNullToSpace(firmwareXmlPo.getDescription()) + "%" + newFirmDes);	//在原有的更新日志上追加
			firmwareTypeDao.update(firmwareType);
			type = firmwareType.getId();
		}
		
		List<Device> deviceList = deviceDao.findAll();
		String deviceFirmware = "";
		if(!deviceList.isEmpty())
		{
			Device device = deviceList.get(0);
			deviceFirmware = device.getFirmware();	//获得设备对应的固件列表
		}
		if ( isAdd == false )	
		{
			//如果不是新增，需要先移除所有设备关联该固件类型的信息
			List<?> firmwareList = firmwareDao.queryFirmwareByType(type);
			for ( int i = 0; i < firmwareList.size(); i++ )
			{
				Firmware firmware = (Firmware)firmwareList.get(i);
				Integer firmwareId = firmware.getId();
					
				if ( deviceFirmware.indexOf(code + "_" + firmwareId) == 0 )
				{
					deviceFirmware = deviceFirmware.replace(code + "_" + firmwareId, "");	//移除无效的固件id
					if ( deviceFirmware.indexOf(",") == 0 )	//如果以逗号开头，则把开头的逗号移除
					{
						deviceFirmware = deviceFirmware.substring(1);
					}
				}
				else	//当id不在开头时，需要多删除一个逗号
				{
					deviceFirmware = deviceFirmware.replace("," + code + "_" + firmwareId, "");	//移除无效的固件id
				}
			}
				//deviceDao.updateAllDeviceFirmware(deviceFirmware);
		}
		
		List<?> firmwareList = (List<?>)ActionContext.getContext().getSession().get("firmwareList");
		StringBuffer firmwareIds = new StringBuffer();	//固件id组合
		for ( int i = 0; i < firmwareList.size(); i++ )
		{
			Firmware firmware = (Firmware)firmwareList.get(i);
			firmware.setType(type);
			firmware.setUploadtime(new Date());
			Integer firmwareId = (Integer)firmwareDao.save(firmware);
			if ( firmwareId == null )
			{
				logger.debug("保存固件信息失败");
				throw new Exception();	//需要回滚，所以直接抛出异常
			}
			
			if ( i == 0 )
			{
				firmwareIds.append(code + "_" + firmwareId);
			}
			else
			{
				firmwareIds.append("," + code + "_" + firmwareId);
			}
		}
		
		if(!deviceList.isEmpty()){
			
			if ( StringUtils.isBlank(deviceFirmware) )
			{
				deviceFirmware = firmwareIds.toString();
			}
			else
			{
				deviceFirmware += "," + firmwareIds.toString();
			}
			deviceDao.updateAllDeviceFirmware(deviceFirmware);
		}
		
		
		for ( int i = 0; i < firmwareList.size(); i++ )
		{
			Firmware firmware = (Firmware)firmwareList.get(i);
			String name = firmware.getName();
			//String targetFilePath = FtpServerListener.USER_DIR + File.separator + "firmware" + File.separator + name;
			File tmpFile = new File(FtpServerListener.USER_DIR + File.separator + "firmware" + File.separator + "tmp" + File.separator + name);
			File file = new File(FtpServerListener.USER_DIR + File.separator + "firmware" + File.separator + name);
			if ( file.exists() )
			{
				FileUtils.deleteQuietly(file);	//防止文件重名异常
			}
			FileUtils.moveFile(tmpFile, file);	//将固件文件从临时目录转移到FTP固件下载目录
			//下面注释内容为将上传文件信息添加到下载列表xml文件中，需要时打开此注释
			//DownloadFileXmlService.saveFileInfoToXmlFile(targetFilePath, name, firmwareXmlPo.getDescription());
		}
		
		return true;
	}
	
	/**
	 * 删除临时固件文件
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public void deleteTmpFirmwareFile()
	{
		String tmpPath = FtpServerListener.USER_DIR + File.separator + "firmware" + File.separator + "tmp";	//固件临时目录
		File tmpDir = new File(tmpPath);
		String[] tmpFiles = tmpDir.list();	//遍历临时固件目录
		for ( String tmpFile : tmpFiles )
		{
			File tmpFirmware = new File(tmpPath + File.separator + tmpFile);
			if ( tmpFirmware.isFile() )
			{
				boolean ret = FileUtils.deleteQuietly(tmpFirmware);	//删除临时固件文件
				if ( ret == false )
				{
					logger.debug("删除临时固件文件失败：" + tmpFirmware.getName());
				}
			}
		}
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public FirmwareDownfileVo findFirmwareForDownfile(String code,String lastVersion){
		return firmwareDao.findFirmwareForDownfile(code,lastVersion);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void saveFirmware(Firmware firmware)
	{
		firmwareDao.save(firmware);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public Firmware findFirmwareByCodeAndVersion(String code,String version)
	{
		return firmwareDao.findFirmwareByCodeAndVersion(code, version);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> findLatestFirmwareForRegist(){
		return firmwareDao.findLatestFirmwareIdAndCode();
	}
}
