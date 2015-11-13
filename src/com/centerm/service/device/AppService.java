package com.centerm.service.device;

import java.io.File;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.AppDao;
import com.centerm.dao.BusinessDao;
import com.centerm.entity.App;
import com.centerm.entity.Business;
import com.centerm.entity.po.AppXmlPo;
import com.centerm.entity.po.FileXmlPo;
import com.centerm.listener.FtpServerListener;
import com.centerm.util.ByteUtil;
import com.centerm.util.JaxbUtil;
import com.centerm.util.apkutil.entity.ApkInfo;
import com.centerm.util.apkutil.utils.ApkUtil;
import com.centerm.util.apkutil.utils.IconUtil;
import com.centerm.vo.AppDownfileVo;
import com.opensymphony.xwork2.ActionContext;

@Service("appService")
public class AppService
{
	private static final Logger logger = LogManager.getLogger(AppService.class);
	
	@Resource(name = "businessDao")
	private BusinessDao businessDao;
	@Resource(name = "appDao")
	private AppDao appDao;
	
	/**
	 * 通过业务id获取app
	 * @param businessid	业务id
	 * @return	app，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public App getAppByBusinessid(Integer businessid)
	{
		if ( businessid == null )
		{
			logger.debug("businessid为null");
			return null;
		}
		
		Business business = businessDao.findById(businessid);
		if ( business == null )
		{
			logger.debug("business为null");
			return null;
		}
		
		App app = appDao.findById(business.getApp());
		if ( app == null )
		{
			logger.debug("app为null");
		}
		
		return app;
	}
	
	/**
	 * 解析应用dat包
	 * @param appFile	dat文件
	 * @return	应用信息头对象，如果失败则为null
	 * @throws Exception
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public AppXmlPo parseDatFile(File appFile) throws Exception
	{
		RandomAccessFile raf = new RandomAccessFile(appFile, "rw");
		
		byte[] datHead = new byte[32];	//数据头
		raf.read(datHead);
		String head = new String(datHead).trim();
		logger.debug("数据头：" + head);
		
		AppXmlPo appXmlPo = new AppXmlPo();
		if ( head.equals("app") )
		{
			byte[] datDataSize = new byte[4];	//数据文件大小
			raf.read(datDataSize);
			int dataSize = ByteUtil.byteArrayToLength(datDataSize);
			logger.debug("数据长度：" + dataSize);
			
			if ( dataSize + datHead.length + datDataSize.length == appFile.length() )
			{
				byte[] datInfoHeadSize = new byte[4];	//信息头文件大小
				raf.read(datInfoHeadSize);
				int infoHeadSize = ByteUtil.byteArrayToLength(datInfoHeadSize);
				
				byte[] datInfoHead = new byte[infoHeadSize];	//信息头
				raf.read(datInfoHead);
				appXmlPo = (AppXmlPo)JaxbUtil.convertToObj(AppXmlPo.class, datInfoHead);
				if ( appXmlPo == null )
				{
					logger.debug("appXmlPo为null");
					raf.close();
					return null;
				}
				
				MessageDigest digest = MessageDigest.getInstance("md5");
				List<App> appList = new ArrayList<App>();
				for ( FileXmlPo fileXmlPo : appXmlPo.getFileList().getFile() )	//循环解析每个应用文件
				{
					digest.reset();
					int fileSize = Integer.parseInt(fileXmlPo.getSize());	//应用文件大小
					byte[] fileData = new byte[fileSize];	//应用文件内容
					raf.read(fileData);
					digest.update(fileData);	//计算MD5值
					String digestMd5 = ByteUtil.bytesToHexString(digest.digest());
					logger.debug("计算MD5值：" + digestMd5.toUpperCase());
					String md5 = fileXmlPo.getMd5();	//文件MD5值
					logger.debug("文件MD5值：" + md5.toUpperCase());
					
					if ( digestMd5.equalsIgnoreCase(md5) )
					{
						String fileName = fileXmlPo.getName();	//文件名
						logger.debug("文件名：" + fileName);
						String tmpPath = FtpServerListener.USER_DIR + File.separator + "app" + File.separator + "tmp";	//应用临时目录
						File file = new File(tmpPath + File.separator + fileName);	//先将应用文件保存在临时目录
						FileUtils.writeByteArrayToFile(file, fileData);
						
						String aaptPath = ServletActionContext.getServletContext().getRealPath("") + File.separator + "tool" + File.separator + "aapt";
						String apkPath = file.getPath();
						ApkInfo apkInfo = new ApkUtil().getApkInfo(aaptPath, apkPath);	//解析apk文件
						String iconName = fileName.substring(0, fileName.lastIndexOf(".")) + ".png";
						IconUtil.extractFileFromApk(apkPath, apkInfo.getApplicationIcon(), FtpServerListener.USER_DIR + File.separator + "app" + File.separator + "icon" + File.separator + "tmp" + File.separator + iconName);
						
						App app = new App();
						app.setName(fileName);
						app.setPackagename(apkInfo.getPackageName());
						app.setVersion(appXmlPo.getVersion());
						app.setVersioncode(Integer.parseInt(apkInfo.getVersionCode()));
						app.setStatus(1);
						app.setUploadtime(new Date());
						app.setDeletetime(DateUtils.parseDate("9999-12-31", "yyyy-MM-dd"));
						app.setFilepath(File.separator + "app");
						app.setMd5(md5.toUpperCase());
						app.setIconpath(File.separator + "app" + File.separator + "icon" + File.separator + "tmp" + File.separator + iconName);
						app.setDescription(appXmlPo.getDescription());
						appList.add(app);
					}
					else	//MD5校验错误
					{
						logger.debug("MD5校验错误");
						raf.close();
						
						String tmpPath = FtpServerListener.USER_DIR + File.separator + "app" + File.separator + "tmp";	//应用临时目录
						File tmpDir = new File(tmpPath);
						String[] tmpFiles = tmpDir.list();	//遍历临时应用目录
						for ( String tmpFile : tmpFiles )
						{
							File tmpApp = new File(tmpPath + File.separator + tmpFile);
							if ( tmpApp.isFile() )
							{
								boolean ret = FileUtils.deleteQuietly(tmpApp);	//删除临时应用文件
								if ( ret == false )
								{
									logger.debug("删除临时应用文件错误");
								}
							}
						}
						
						return null;
					}
				}
				ActionContext.getContext().getSession().put("appList", appList);	//将应用文件信息暂时保存在session中
			}
			else	//数据长度不匹配
			{
				logger.debug("数据长度不匹配");
				raf.close();
				return null;
			}
		}
		else	//数据头不合法
		{
			logger.debug("数据头不合法");
			raf.close();
			return null;
		}
		
		raf.close();
		return appXmlPo;
	}
	
	/**
	 * 删除临时应用文件
	 * @return
	 * true - 删除成功<br>
	 * false - 删除失败
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean deleteTmpAppFile()
	{
		List<?> appList = (List<?>)ActionContext.getContext().getSession().get("appList");
		for ( int i = 0; i < appList.size(); i++ )
		{
			App app = (App)appList.get(i);
			String fileName = app.getName();
			
			File tmpFile = new File(FtpServerListener.USER_DIR + app.getFilepath() + File.separator + "tmp" + File.separator + fileName);
			boolean ret = FileUtils.deleteQuietly(tmpFile);
			if ( ret == false )
			{
				logger.debug("删除临时应用文件失败");
				return false;
			}
			
			File tmpIconFile = new File(FtpServerListener.USER_DIR + app.getIconpath());
			ret = FileUtils.deleteQuietly(tmpIconFile);
			if ( ret == false )
			{
				logger.debug("删除临时应用文件图标失败");
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 通过业务id获取应用更新日志
	 * @param businessid	业务id
	 * @return	应用更新日志信息
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public String getAppUpdateInfo(Integer businessid)
	{
		if ( businessid == null )
		{
			logger.debug("businessid为null");
			return null;
		}
		
		Business business = businessDao.findById(businessid);
		if ( business == null )
		{
			logger.debug("业务" + businessid + "不存在");
			return null;
		}
		
		Integer appId = business.getApp();
		if ( appId == null )
		{
			logger.debug("appId为null");
			return null;
		}
		
		App app = appDao.findById(appId);
		if ( app == null )
		{
			logger.debug("应用" + appId + "不存在");
			return null;
		}
		
		List<?> appList = appDao.queryAppByPackagename(app.getPackagename());
		if ( appList == null )
		{
			logger.debug("appList为null");
			return null;
		}
		
		String updateInfo = "";
		for ( int i = 0; i < appList.size(); i++ )
		{
			App tmpApp = (App)appList.get(i);
			String version = tmpApp.getVersion();
			String description = tmpApp.getDescription();
			
			updateInfo += "&" + version + "&";	//分割线内容
			updateInfo += description;	//更新内容
			updateInfo += "%";	//换行
		}
		
		return updateInfo;
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> findAppByDeviceApp(String dev_app)
	{
		if(dev_app == null)
		{
			return null;
		}
		String[] apps = dev_app.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for(String app:apps)
		{	
			if(StringUtils.isNotBlank(app))
			{				
				idList.add(Integer.parseInt(app));
			}
		}
		if(idList.isEmpty())
		{
			return null;
		}
		return appDao.findAppByIds(idList.toArray(new Integer[]{}));
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public AppDownfileVo findAppByPackagenameAndVersion(String packagename, Integer version) 
	{
		return appDao.findAppByPackagenameAndVersion(packagename, version);
	}
}
