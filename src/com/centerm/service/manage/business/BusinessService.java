package com.centerm.service.manage.business;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.AppDao;
import com.centerm.dao.BusinessDao;
import com.centerm.dao.OutletsDao;
import com.centerm.entity.App;
import com.centerm.entity.Business;
import com.centerm.entity.Outlets;
import com.centerm.entity.po.AppXmlPo;
import com.centerm.listener.FtpServerListener;
import com.opensymphony.xwork2.ActionContext;

@Service("businessService")
public class BusinessService
{
	private static final Logger logger = LogManager.getLogger(BusinessService.class);
	
	@Resource(name = "businessDao")
	private BusinessDao businessDao;
	@Resource(name = "appDao")
	private AppDao appDao;
	@Resource(name = "outletsDao")
	private OutletsDao outletsDao;
	
	/**
	 * 业务id-业务名称映射表
	 */
	public static Map<Integer, String> businessMap = new HashMap<Integer, String>();
	
	/**
	 * 获得所有业务列表
	 * @return	业务列表，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> getAllBusiness()
	{
		List<?> businessList = businessDao.queryAllBusiness();
		if ( businessList == null )
		{
			logger.debug("businessList为null");
		}
		
		return businessList;
	}
	
	/**
	 * 获得非父业务个数
	 * @return	业务个数，如果失败则为-1
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public int getBusinessCountExceptParent()
	{
		Long count = businessDao.queryAllBusinessExceptParent();
		if ( count == null )
		{
			logger.debug("count为null");
			return -1;
		}
		
		return count.intValue();
	}
	
	/**
	 * 业务是否存在
	 * @param code	业务号
	 * @param isParent	是否为业务组
	 * @param parentid	所属业务组id
	 * @param name	业务名
	 * @return
	 * true - 存在<br>
	 * false - 不存在
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean isBusinessExist(String code, boolean isParent, Integer parentid, String name)
	{
		if ( code == null || parentid == null || name == null )
		{
			logger.debug("code或者parentid或者name为null");
			return false;
		}
		
		List<?> businessList = businessDao.queryBusinessByCode(code);
		if ( businessList.isEmpty() )	//不存在相同业务号的记录
		{
			return false;
		}
		else
		{
			if ( isParent == false )	//如果是业务，则不允许存在相同业务号的情况
			{
				return true;
			}
			else
			{
				for ( int i = 0; i < businessList.size(); i++ )
				{
					Business business = (Business)businessList.get(i);
					Integer businessParentid = business.getParentid();
					String businessName = business.getName();
					if ( businessParentid == parentid && businessName.equals(name) )	//在同一父业务组下不能存在同名业务组
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 新增业务组
	 * @param name	业务组名称
	 * @param isParent	是否为父业务
	 * @param parentid	父业务id
	 * @param feature	业务描述
	 * @return	新增业务组id，如果失败则为null
	 */
	@Transactional(rollbackFor = Exception.class)
	public Integer addBusinessGroup(String name, boolean isParent, Integer parentid, String feature)
	{
		if ( name == null || parentid == null || feature == null )
		{
			logger.debug("name或者parentid或者feature为null");
			return null;
		}
		
		Business business = new Business();
		business.setName(name);
		business.setCode("-1");	//业务组编号默认为-1
		if ( isParent == true )
		{
			business.setIsparent(1);
		}
		else
		{
			business.setIsparent(0);
		}
		business.setParentid(parentid);
		business.setSortid(-1);	//默认设置为-1
		business.setFeature(feature);
		business.setApp(-1);	//网点组没有对应的应用
		Integer id = (Integer)businessDao.save(business);
		if ( id == null )
		{
			logger.debug("id为null");
			return null;
		}

		businessMap.put(id, name);
		return id;
	}
	
	/**
	 * 新增业务
	 * @param isParent	是否为父业务
	 * @param parentid	父业务id
	 * @return	新增业务id，如果失败则为null
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public Integer addBusiness(boolean isParent, Integer parentid) throws Exception
	{
		if ( parentid == null )
		{
			logger.debug("parentid为null");
			return null;
		}
		
		AppXmlPo appXmlPo = (AppXmlPo)ActionContext.getContext().getSession().get("appXmlPo");
		Business business = new Business();
		business.setCode(appXmlPo.getCode());
		business.setName(appXmlPo.getName());
		if ( isParent == true )
		{
			business.setIsparent(1);
		}
		else
		{
			business.setIsparent(0);
		}
		business.setParentid(parentid);
		business.setSortid(-1);	//默认为-1
		business.setFeature(appXmlPo.getName());	//默认使用name作为feature
		
		List<?> appList = (List<?>)ActionContext.getContext().getSession().get("appList");
		StringBuffer appIds = new StringBuffer();	//应用id组合
		for ( int i = 0; i < appList.size(); i++ )
		{
			App app = (App)appList.get(i);
			String name = app.getName();
			String iconName = name.substring(0, name.lastIndexOf(".")) + ".png";
			app.setIconpath(File.separator + "app" + File.separator + "icon" + File.separator + iconName);	//将图标路径从临时目录改为正式目录
			Integer appId = (Integer)appDao.save(app);
			if ( appId == null )
			{
				logger.debug("appId为null");
				throw new Exception();	//需要回滚，所以直接抛出异常
			}
			
			if ( i == 0 )
			{
				appIds.append(appId);
			}
			else
			{
				appIds.append("," + appId);
			}
		}
		business.setApp(Integer.parseInt(appIds.toString()));	//默认一个业务只对应一个应用
		
		Integer id = (Integer)businessDao.save(business);
		if ( id == null )
		{
			logger.debug("id为null");
			throw new Exception();	//需要回滚，所以直接抛出异常
		}
		
		for ( int i = 0; i < appList.size(); i++ )
		{
			App app = (App)appList.get(i);
			String name = app.getName();
			String iconName = name.substring(0, name.lastIndexOf(".")) + ".png";
			
			File tmpFile = new File(FtpServerListener.USER_DIR + File.separator + "app" + File.separator + "tmp" + File.separator + name);
			File file = new File(FtpServerListener.USER_DIR + File.separator + "app" + File.separator + name);
			if ( file.exists() )
			{
				boolean ret = FileUtils.deleteQuietly(file);	//防止文件重名异常
				if ( ret == false )
				{
					logger.debug("删除同名文件失败");
					throw new Exception();	//需要回滚，所以直接抛出异常
				}
			}
			FileUtils.moveFile(tmpFile, file);	//将应用文件从临时目录转移到FTP应用下载目录
			
			File tmpIconFile = new File(FtpServerListener.USER_DIR + File.separator + "app" + File.separator + "icon" + File.separator + "tmp" + File.separator + iconName);
			File iconFile = new File(FtpServerListener.USER_DIR + File.separator + "app" + File.separator + "icon" + File.separator + iconName);
			if ( iconFile.exists() )
			{
				boolean ret = FileUtils.deleteQuietly(iconFile);	//防止文件重名异常
				if ( ret == false )
				{
					logger.debug("删除同名图标文件失败");
					throw new Exception();	//需要回滚，所以直接抛出异常
				}
			}
			FileUtils.moveFile(tmpIconFile, iconFile);	//将应用文件图标从临时目录转移到FTP应用下载图标目录
		}
		
		businessMap.put(id, business.getName());
		return id;
	}
	
	/**
	 * 更新业务组
	 * @param id	业务组id
	 * @param name	业务组名
	 * @param feature	业务组描述
	 * @return
	 * true - 更新成功<br>
	 * false - 更新失败
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updateBusinessGroup(Integer id, String name, String feature)
	{
		if ( id == null || name == null || feature == null )
		{
			logger.debug("id或者name或者feature为null");
			return false;
		}
		
		Business business = businessDao.findById(id);
		if ( business == null )
		{
			logger.debug("业务" + id + "不存在");
			return false;
		}
		
		business.setName(name);
		business.setFeature(feature);
		businessDao.update(business);
		
		businessMap.put(id, name);
		return true;
	}
	
	/**
	 * 上传的业务是否与当前业务相同
	 * @param id	当前选中业务id
	 * @param code	上传业务编号
	 * @return
	 * true - 相同业务<br>
	 * false - 不同业务
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean isSameBusiness(Integer id, String code)
	{
		if ( id == null || code == null )
		{
			logger.debug("id或者code为null");
			return false;
		}
		
		Business business = businessDao.findById(id);
		if ( business == null )
		{
			logger.debug("业务" + id + "不存在");
			return false;
		}
		
		if ( business.getCode().equals(code) )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 业务是否需要更新
	 * @param id	当前选中业务id
	 * @param version	上传业务版本号
	 * @return
	 * true - 需要更新<br>
	 * false - 不需要更新
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean isNeedUpdate(Integer id, String version)
	{
		if ( id == null || version == null )
		{
			logger.debug("id或者version为null");
			return false;
		}
		
		Business business = businessDao.findById(id);
		if ( business == null )
		{
			logger.debug("业务" + id + "不存在");
			return false;
		}
		
		App app = appDao.findById(business.getApp());
		if ( app == null )
		{
			logger.debug("应用" + business.getApp() + "不存在");
			return false;
		}
		
		if ( app.getVersion().equals(version) )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * 更新业务
	 * @param id	业务id
	 * @return
	 * true - 更新成功<br>
	 * false - 更新失败
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updateBusiness(Integer id) throws Exception
	{
		if ( id == null )
		{
			logger.debug("id为null");
			return false;
		}
		
		AppXmlPo appXmlPo = (AppXmlPo)ActionContext.getContext().getSession().get("appXmlPo");
		Business business = businessDao.findById(id);
		if ( business == null )
		{
			logger.debug("业务" + id + "不存在");
			return false;
		}
		
		business.setCode(appXmlPo.getCode());
		business.setName(appXmlPo.getName());
		business.setFeature(appXmlPo.getName());	//默认使用name作为feature
		
		App oldApp = appDao.findById(business.getApp());
		if ( oldApp != null )
		{
			oldApp.setStatus(0);
			appDao.update(oldApp);
		}
		else
		{
			logger.debug("oldApp为null");
		}
		List<?> appList = (List<?>)ActionContext.getContext().getSession().get("appList");
		StringBuffer appIds = new StringBuffer();	//应用id组合
		for ( int i = 0; i < appList.size(); i++ )
		{
			App app = (App)appList.get(i);
			String name = app.getName();
			String iconName = name.substring(0, name.lastIndexOf(".")) + ".png";
			app.setIconpath(File.separator + "app" + File.separator + "icon" + File.separator + iconName);	//将图标路径从临时目录改为正式目录
			Integer appId = (Integer)appDao.save(app);
			if ( appId == null )
			{
				logger.debug("appId为null");
				throw new Exception();	//需要回滚，所以直接抛出异常
			}
			
			if ( i == 0 )
			{
				appIds.append(appId);
			}
			else
			{
				appIds.append("," + appId);
			}
		}
		business.setApp(Integer.parseInt(appIds.toString()));	//默认一个业务只对应一个应用
		businessDao.update(business);
		
		List<Outlets> outletsList = outletsDao.findAll();
		for ( Outlets outlets : outletsList )	//替换所有网点的对应应用id
		{
			String outletsApp = outlets.getApp();	//获取该网点下所有的业务
			String[] outletsApps = outletsApp.split(",");
			StringBuffer outletsAppBuffer = new StringBuffer();
			for ( int i = 0; i < outletsApps.length; i++ )
			{
				String outletsAppId = outletsApps[i];
				if ( outletsAppId.equals(String.valueOf(oldApp.getId())) )	//该网点包含老业务
				{
					outletsApps[i] = appIds.toString();	//将老业务id替换为新业务id
				}
				
				if ( i == 0 )
				{
					outletsAppBuffer.append(outletsApps[i]);
				}
				else
				{
					outletsAppBuffer.append("," + outletsApps[i]);
				}
			}
			outletsApp = outletsAppBuffer.toString();
			outlets.setApp(outletsApp);
			outletsDao.update(outlets);
		}
		
		for ( int i = 0; i < appList.size(); i++ )
		{
			App app = (App)appList.get(i);
			String name = app.getName();
			String iconName = name.substring(0, name.lastIndexOf(".")) + ".png";
			
			File tmpFile = new File(FtpServerListener.USER_DIR + File.separator + "app" + File.separator + "tmp" + File.separator + name);
			File file = new File(FtpServerListener.USER_DIR + File.separator + "app" + File.separator + name);
			if ( file.exists() )
			{
				boolean ret = FileUtils.deleteQuietly(file);	//防止文件重名异常
				if ( ret == false )
				{
					logger.debug("删除同名文件失败");
					throw new Exception();	//需要回滚，所以直接抛出异常
				}
			}
			FileUtils.moveFile(tmpFile, file);	//将应用文件从临时目录转移到FTP应用下载目录
			
			File tmpIconFile = new File(FtpServerListener.USER_DIR + File.separator + "app" + File.separator + "icon" + File.separator + "tmp" + File.separator + iconName);
			File iconFile = new File(FtpServerListener.USER_DIR + File.separator + "app" + File.separator + "icon" + File.separator + iconName);
			if ( iconFile.exists() )
			{
				boolean ret = FileUtils.deleteQuietly(iconFile);	//防止文件重名异常
				if ( ret == false )
				{
					logger.debug("删除同名图标文件失败");
					throw new Exception();	//需要回滚，所以直接抛出异常
				}
			}
			FileUtils.moveFile(tmpIconFile, iconFile);	//将应用文件图标从临时目录转移到FTP应用下载图标目录
		}
		
		businessMap.put(id, business.getName());
		return true;
	}

	/**
	 * 初始化交易id-交易名对应MAP
	 * @return
	 * true - 初始化成功<br>
	 * false - 初始化失败
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public void initBusinessMap()
	{
		List<Business> businessList = businessDao.findAll();
		if ( businessList != null && businessList.size() > 0 )
		{
			for ( Business business : businessList )
			{
				businessMap.put(business.getId(), business.getName());
			}
		}
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Integer addBusinessGroup(Business business)
	{
		return (Integer) businessDao.save(business);
	}

//	@Transactional(readOnly = false, rollbackFor = Exception.class)
//	public Integer saveOrUpdateBusinessAndApp(App uploadAppInfo, XmlAppInfo xmlAppInfo, Integer parentid) throws IOException
//	{
//		Business business = businessDao.queryBusinessByCode(xmlAppInfo.getCode());
//		Integer businessId = null;
//		if(business == null)
//		{
//			Integer appId = (Integer) appDao.save(uploadAppInfo);//保存app信息
//			business = new Business();//新增business信息
//			business.setApp(appId);
//			business.setName(xmlAppInfo.getName());
//			business.setCode(xmlAppInfo.getCode());
//			business.setFeature(xmlAppInfo.getName());
//			business.setSortid(-1);
//			business.setIsparent(0);
//			business.setParentid(parentid);
//			businessId = (Integer)businessDao.save(business);
//		}
//		else
//		{
//			//更新business信息
//			business.setName(xmlAppInfo.getName());
//			//更新app信息
//			App savedApp = appDao.findById(business.getApp());
//			savedApp.setName(uploadAppInfo.getName());
//			savedApp.setMd5(uploadAppInfo.getMd5());
//			savedApp.setStatus(1);
//			savedApp.setVersion(uploadAppInfo.getVersion());
//			savedApp.setVersioncode(uploadAppInfo.getVersioncode());
//			savedApp.setFilepath(uploadAppInfo.getFilepath());
//			savedApp.setIconpath(uploadAppInfo.getIconpath());
//			savedApp.setUploadtime(new Date());
//			savedApp.setDeletetime(uploadAppInfo.getDeletetime());
//			savedApp.setDescription(xmlAppInfo.getDescription());
//			
//			businessId = business.getId();
//		}
//		moveFile(uploadAppInfo);
//		return businessId;
//	}
//
//	/**
//	 * 将临时目录下的app文件移到正式目录下
//	 * @param uploadAppInfo
//	 * @throws IOException
//	 */
//	private void moveFile(App uploadAppInfo) throws IOException
//	{
//		File tmpFile = new File(FtpServerListener.USER_DIR + File.separator + "app" + File.separator + "tmp" + File.separator + uploadAppInfo.getName());
//		String targetFilePath = FtpServerListener.USER_DIR + File.separator + "app" + File.separator + uploadAppInfo.getName();
//		File file = new File(targetFilePath);
//		if ( file.exists() )
//		{
//			FileUtils.deleteQuietly(file);	//防止文件重名异常
//		}
//		FileUtils.moveFile(tmpFile, file);	//将固件文件从临时目录转移到FTP固件下载目录
//		//DownloadFileXmlService.saveFileInfoToXmlFile(targetFilePath, uploadAppInfo.getName(), uploadAppInfo.getDescription());
//	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean exsitName(String name)
	{
		return businessDao.queryBusinessByName(name);
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public Business getBusinessByCode(String code){
		List<?> list = businessDao.queryBusinessByCode(code);
		if(list != null && list.size() > 0) {
			return (Business) list.get(0);
		} else {
			return null;
		}
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public Business getBusinessById(Integer id){
		return businessDao.findById(id);
	}
}
