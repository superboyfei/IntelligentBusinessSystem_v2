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
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.CounterAppDao;
import com.centerm.dao.CounterAppTypeDao;
import com.centerm.entity.CounterAppType;
import com.centerm.entity.CounterApp;
import com.centerm.entity.po.FileXmlPo;
import com.centerm.entity.po.CounterAppXmlPo;
import com.centerm.listener.FtpServerListener;
import com.centerm.util.ByteUtil;
import com.centerm.util.JaxbUtil;
import com.centerm.util.StringUtil;
import com.centerm.vo.CounterAppDownFileVo;
import com.opensymphony.xwork2.ActionContext;

@Service("counterAppService")
public class CounterAppService {
	private static final Logger logger = LogManager.getLogger(CounterAppService.class);
	
	private static final int DOWN_LENGTH = 1024*1024*10; //10M
	
	@Resource
	private CounterAppDao counterAppDao;
	@Resource
	private CounterAppTypeDao counterAppTypeDao;
	/**
	 * 获得所有柜员应用类型列表
	 * @return	柜员应用类型列表，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<CounterAppType> getAllCounterAppType()
	{
		List<CounterAppType> counterAppTypeList = counterAppTypeDao.findAll();
		if ( counterAppTypeList == null )
		{
			logger.debug("counterAppTypeList为null");
		}
		
		return counterAppTypeList;
	}
	
	/**
	 * 通过柜员应用code获得柜员应用升级日志
	 * @param code	柜员应用code
	 * @return	柜员应用升级日志内容，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public String getDescriptionById(Integer counterAppTypeId)
	{
		if ( counterAppTypeId == null )
		{
			logger.debug("counterAppTypeId为null");
			return null;
		}
		
		CounterAppType counterAppType = counterAppTypeDao.queryCounterAppTypeById(counterAppTypeId);
		if ( counterAppType == null )
		{
			logger.debug("counterAppType为null");
			return null;
		}
		
		return counterAppType.getDescription();
	}
	
	/**
	 * 删除指定id的柜员应用类型
	 * @param id	柜员应用类型id
	 * @return
	 * true - 删除成功<br>
	 * false - 删除失败
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteCounterAppTypeById(Integer id)
	{
		CounterAppType counterAppType = counterAppTypeDao.findById(id);
		List<?> counterAppList = counterAppDao.queryCounterAppByType(id);
		if ( counterAppList == null )
		{
			logger.debug("counterAppList为null");
			return false;
		}
		
		for ( int i = 0; i < counterAppList.size(); i++ )
		{
			CounterApp counterApp = (CounterApp)counterAppList.get(i);
			counterApp.setStatus(0);	//将柜员应用status置为不可用状态
			counterAppDao.update(counterApp);
		}
		
		counterAppTypeDao.delete(counterAppType);	//删除柜员应用类型记录
		return true;
	}
	
	/**
	 * 解析柜员应用dat包
	 * @param counterAppFile	dat文件
	 * @param counterAppFileName	dat文件名
	 * @return	柜员应用信息头对象，如果失败则为null
	 * @throws Exception
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public CounterAppXmlPo parseDatFile(File counterAppFile, String counterAppFileName) throws Exception
	{
		RandomAccessFile raf = new RandomAccessFile(counterAppFile, "rw");
		
		byte[] datHead = new byte[32];	//数据头
		raf.read(datHead);
		String head = new String(datHead).trim();
		logger.debug("数据头：" + head);
		
		CounterAppXmlPo counterAppXmlPo = new CounterAppXmlPo();
		if ( head.equals("telnet") )
		{
			byte[] datDataSize = new byte[4];	//数据文件大小
			raf.read(datDataSize);
			int dataSize = ByteUtil.byteArrayToLength(datDataSize);
			logger.debug("数据长度：" + dataSize);
			
			if ( dataSize + datHead.length + datDataSize.length == counterAppFile.length() )
			{
				byte[] datInfoHeadSize = new byte[4];	//信息头文件大小
				raf.read(datInfoHeadSize);
				int infoHeadSize = ByteUtil.byteArrayToLength(datInfoHeadSize);
				
				byte[] datInfoHead = new byte[infoHeadSize];	//信息头
				raf.read(datInfoHead);
				counterAppXmlPo = (CounterAppXmlPo)JaxbUtil.convertToObj(CounterAppXmlPo.class, datInfoHead);
				if ( counterAppXmlPo == null )
				{
					logger.debug("counterAppXmlPo为null");
					raf.close();
					return counterAppXmlPo;
				}
				
				//更新日志必须在180个字符内。
				String desTmp = counterAppXmlPo.getDescription();
				if(desTmp.getBytes("GBK").length > 512){
					counterAppXmlPo.setResult(false);
					counterAppXmlPo.setErrorMsg("error_上传的文件更新说明必须少于512个字符(256个汉字)");
					raf.close();
					return counterAppXmlPo;
				}
				
				MessageDigest digest = MessageDigest.getInstance("md5");
				List<CounterApp> counterAppList = new ArrayList<CounterApp>();
				
				for ( FileXmlPo fileXmlPo : counterAppXmlPo.getFileList().getFile() )	//循环解析每个柜员应用文件
				{
					digest.reset();
					int fileSize = Integer.parseInt(fileXmlPo.getSize());	//柜员应用文件大小
					byte[] fileData = new byte[fileSize];	//柜员应用文件内容
					
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
					
					CounterAppType counterAppType = counterAppTypeDao.queryCounterAppTypeByCode(counterAppXmlPo.getCode());
					
					if ( digestMd5.equalsIgnoreCase(md5) )
					{
						String fileName = fileXmlPo.getName();	//文件名
						String tmpPath = FtpServerListener.USER_DIR + File.separator + "counterApp" + File.separator + "tmp";	//柜员应用临时目录
						new File(tmpPath).mkdirs();
						File file = new File(tmpPath + File.separator + fileName);	//先将柜员应用文件保存在临时目录
						
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
						
						if(counterAppType != null){
							CounterApp counterApp_tmp = counterAppTypeDao.findByVersionAndLastVersionAndType(
									counterAppXmlPo.getVersion(), counterAppXmlPo.getLastVersion(), counterAppType.getId());
							if(counterApp_tmp != null){
								//已有文件记录
								counterAppXmlPo.setResult(false);
								counterAppXmlPo.setErrorMsg("warning_上传的文件服务端已存在，请勿重复上传");
								raf.close();
								return counterAppXmlPo;
							}
							
							if(!counterAppType.getVersion().equals(counterAppXmlPo.getLastVersion())){
								counterAppXmlPo.setResult(false);
								counterAppXmlPo.setErrorMsg("error_上传的文件适用版本不存在");
								raf.close();
								return counterAppXmlPo;
							}
						}
						
						CounterApp counterApp = new CounterApp();
						counterApp.setName(fileName);
						counterApp.setVersion(counterAppXmlPo.getVersion());
						counterApp.setLastversion(counterAppXmlPo.getLastVersion());
						counterApp.setStatus(1);
						counterApp.setDeletetime(DateUtils.parseDate("9999-12-31", "yyyy-MM-dd"));
						counterApp.setFilepath(File.separator + "counterApp");
						counterApp.setMd5(md5.toUpperCase());
						counterAppList.add(counterApp);
					}
					else	//MD5校验错误
					{
						counterAppXmlPo.setResult(false);
						counterAppXmlPo.setErrorMsg("error_MD5校验错误");
						raf.close();
						
						String tmpPath = FtpServerListener.USER_DIR + File.separator + "counterApp" + File.separator + "tmp";	//柜员应用临时目录
						File tmpDir = new File(tmpPath);
						String[] tmpFiles = tmpDir.list();	//遍历临时柜员应用目录
						for ( String tmpFile : tmpFiles )
						{
							File tmpCounterApp = new File(tmpPath + File.separator + tmpFile);
							if ( tmpCounterApp.isFile() )
							{
								FileUtils.deleteQuietly(tmpCounterApp);	//删除临时柜员应用文件
							}
						}
						
						return counterAppXmlPo;
					}
				}
				ActionContext.getContext().getSession().put("counterAppList", counterAppList);	//将柜员应用文件信息暂时保存在session中
			}
			else	//数据长度不匹配
			{
				counterAppXmlPo.setResult(false);
				counterAppXmlPo.setErrorMsg("error_数据长度不匹配");
				raf.close();
				return counterAppXmlPo;
			}
		}
		else	//数据头不合法
		{
			counterAppXmlPo.setResult(false);
			counterAppXmlPo.setErrorMsg("error_上传的文件非柜台端应用文件");
			raf.close();
			return counterAppXmlPo;
		}
		
		counterAppXmlPo.setResult(true);
		raf.close();
		return counterAppXmlPo;
	}
	
	/**
	 * 通过柜员应用code获得柜员应用类型
	 * @param code	柜员应用code
	 * @return	柜员应用类型，如果不存在则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public CounterAppType getCounterAppTypeByCode(String code)
	{
		CounterAppType counterAppType = counterAppTypeDao.queryCounterAppTypeByCode(code);
		if ( counterAppType == null )
		{
			logger.debug("counterAppType为null");
		}
		
		return counterAppType;
	}
	
	/**
	 * 通过柜员应用Id获得柜员应用类型
	 * @param Id	柜员应用Id
	 * @return	柜员应用类型，如果不存在则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public CounterAppType getCounterAppTypeById(Integer counterAppTypeId)
	{
		CounterAppType counterAppType = counterAppTypeDao.queryCounterAppTypeById(counterAppTypeId);
		if ( counterAppType == null )
		{
			logger.debug("counterAppType为null");
		}
		return counterAppType;
	}
	
	/**
	 * 通过柜员应用code获得柜员应用类型
	 * @param code	柜员应用code
	 * @return	柜员应用类型，如果不存在则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public CounterAppType getCounterAppTypeByName(String counterAppTypeByName)
	{
		CounterAppType counterAppType = counterAppTypeDao.queryCounterAppTypeByName(counterAppTypeByName);
		if ( counterAppType == null )
		{
			logger.debug("counterAppType为null");
		}
		return counterAppType;
	}
	
	/**
	 * 保存柜员应用信息
	 * @return
	 * true - 保存成功
	 * false - 保存失败
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean saveCounterAppInfo() throws Exception
	{
		Integer counterAppTypeId = null;
		CounterAppXmlPo counterXmlPo = (CounterAppXmlPo)ActionContext.getContext().getSession().get("counterAppXmlPo");
		CounterAppType counterAppType = counterAppTypeDao.queryCounterAppTypeByCode(counterXmlPo.getCode());
		logger.debug("更新或新增counterAppType");
		
		if(counterAppType == null){
			counterAppType = new CounterAppType();
			counterAppType.setName(counterXmlPo.getName());
			counterAppType.setCode(counterXmlPo.getCode());
			counterAppType.setVersion(counterXmlPo.getVersion());
			counterAppType.setUpdatetime(new Date());
			counterAppType.setDescription("&" + counterXmlPo.getVersion() + "&%" + StringUtil.killNullToSpace(counterXmlPo.getDescription()));
			counterAppTypeId = (Integer)counterAppTypeDao.save(counterAppType);
			
			if(counterAppTypeId == null){
				logger.debug("保存柜员应用信息失败");
				throw new Exception();	//需要回滚，所以直接抛出异常
			}
		}else {
			counterAppTypeId = counterAppType.getId();
			counterAppType.setName(counterXmlPo.getName());
			counterAppType.setCode(counterXmlPo.getCode());
			counterAppType.setVersion(counterXmlPo.getVersion());
			counterAppType.setUpdatetime(new Date());
			
			//只取最近八次的更新日志
			String[] counterAppDes = counterAppType.getDescription().split("%&");
			String newCounterAppDes = counterAppType.getDescription();
			if(counterAppDes.length >= 8) {
				newCounterAppDes = "";
				for(int i=0; i<counterAppDes.length-1; i++){
					if(i == counterAppDes.length-2) {
						newCounterAppDes = newCounterAppDes + counterAppDes[i];
					} else {
						newCounterAppDes = newCounterAppDes + counterAppDes[i] + "%&";
					}
				}
			}
			
			counterAppType.setDescription("&" + counterXmlPo.getVersion() + "&%" + StringUtil.killNullToSpace(counterXmlPo.getDescription()) + "%" + newCounterAppDes);//在原有的更新日志上追加
			counterAppTypeDao.update(counterAppType);
		}
		
		List<?> counterAppList = (List<?>)ActionContext.getContext().getSession().get("counterAppList");
		for ( int i = 0; i < counterAppList.size(); i++ )
		{
			CounterApp counterApp = (CounterApp)counterAppList.get(i);
			counterApp.setType(counterAppTypeId);
			counterApp.setUploadtime(new Date());
			Integer counterAppId = (Integer)counterAppDao.save(counterApp);
			if ( counterAppId == null )
			{
				logger.debug("保存柜员应用信息失败");
				throw new Exception();	//需要回滚，所以直接抛出异常
			}
		}
		
		for ( int i = 0; i < counterAppList.size(); i++ )
		{
			CounterApp counterApp = (CounterApp)counterAppList.get(i);
			String name = counterApp.getName();
			String targetFilePath = FtpServerListener.USER_DIR + File.separator + "counterApp" + File.separator + name;
			File tmpFile = new File(FtpServerListener.USER_DIR + File.separator + "counterApp" + File.separator + "tmp" + File.separator + name);
			File file = new File(targetFilePath);
			if ( file.exists() )
			{
				FileUtils.deleteQuietly(file);	//防止文件重名异常
			}
			FileUtils.moveFile(tmpFile, file);	//将柜员应用文件从临时目录转移到FTP柜员应用下载目录
			//下面注释内容为将上传文件信息添加到下载列表xml文件中，需要时打开此注释
			//DownloadFileXmlService.saveFileInfoToXmlFile(targetFilePath, name, counterXmlPo.getDescription());
		}
		
		return true;
	}

	/**
	 * 删除临时柜员应用文件
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public void deleteTmpCounterAppFile()
	{
		String tmpPath = FtpServerListener.USER_DIR + File.separator + "counterApp" + File.separator + "tmp";	//柜员应用临时目录
		File tmpDir = new File(tmpPath);
		String[] tmpFiles = tmpDir.list();	//遍历临时柜员应用目录
		for ( String tmpFile : tmpFiles )
		{
			File tmpCounterApp = new File(tmpPath + File.separator + tmpFile);
			if ( tmpCounterApp.isFile() )
			{
				boolean ret = FileUtils.deleteQuietly(tmpCounterApp);	//删除临时柜员应用文件
				if ( ret == false )
				{
					logger.debug("删除临时柜员应用文件失败：" + tmpCounterApp.getName());
				}
			}
		}
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public CounterAppDownFileVo findCounterAppForDownfile(String code,String lastVersion){
		return counterAppDao.findCounterAppForDownfile(code,lastVersion);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void saveCounterApp(CounterApp firmware)
	{
		counterAppDao.save(firmware);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public CounterApp findCounterAppByCodeAndVersion(int code,String version)
	{
		return counterAppDao.findCounterAppByCodeAndVersion(code, version);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> findLatestCounterAppForRegist(){
		return counterAppDao.findLatestCounterAppIdAndCode();
	}
}
