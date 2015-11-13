package com.centerm.service.manage.outlets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.OutletsDao;
import com.centerm.entity.Outlets;

@Service("outletsService")
public class OutletsService
{
	private static final Logger logger = LogManager.getLogger(OutletsService.class);
	
	@Resource(name = "outletsDao")
	private OutletsDao outletsDao;
	
	/**
	 * 网点id-网点名称映射表
	 */
	public static Map<Integer, String> outletsMap = new HashMap<Integer, String>();
	
	/**
	 * 获得所有网点列表
	 * @return	网点列表，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> getAllOutlets()
	{
		List<?> outletsList = outletsDao.queryAllOutlets();
		if ( outletsList == null )
		{
			logger.debug("outletsList为null");
		}
		
		return outletsList;
	}
	
	/**
	 * 获得非父网点个数
	 * @return	网点个数，如果失败则为-1
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public int getOutletsCountExceptParent()
	{
		Long count = outletsDao.queryAllOutletsExceptParent();
		if ( count == null )
		{
			logger.debug("count为null");
			return -1;
		}
		
		return count.intValue();
	}
	
	/**
	 * 通过id获取网点信息
	 * @return	网点记录，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public Outlets getOutletsById(Integer id)
	{
		Outlets outlets = outletsDao.findById(id);
		if ( outlets == null )
		{
			logger.debug("outlets为null");
		}
		
		return outlets;
	}
	
	/**
	 * 网点是否存在
	 * @param code	网点号
	 * @param isParent	是否为网点组
	 * @param parentid	所属网点组id
	 * @param name	网点名
	 * @return
	 * true - 存在<br>
	 * false - 不存在
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean isOutletsExist(String code, boolean isParent, Integer parentid, String name)
	{
		if ( code == null || parentid == null || name == null )
		{
			logger.debug("code或者parentid或者name为null");
			return false;
		}
		
		List<?> outletsList = outletsDao.queryOutletsByCode(code);
		if ( outletsList.isEmpty() )	//不存在相同网点号的记录
		{
			return false;
		}
		else
		{
			if ( isParent == false )	//如果是网点，则不允许存在相同网点号的情况
			{
				return true;
			}
			else
			{
				for ( int i = 0; i < outletsList.size(); i++ )
				{
					Outlets outlets = (Outlets)outletsList.get(i);
					Integer outletsParentid = outlets.getParentid();
					String outletsName = outlets.getName();
					if ( outletsParentid == parentid && outletsName.equals(name) )	//在同一父网点组下不能存在同名网点组
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 新增网点
	 * @param name	网点名称
	 * @param code	网点号
	 * @param isParent	是否为网点组
	 * @param parentid	所属网点组id
	 * @param description	网点描述
	 * @return	新增网点id，如果失败则为null
	 */
	@Transactional(rollbackFor = Exception.class)
	public Integer addOutlets(String name, String code, boolean isParent, Integer parentid, String description)
	{
		if ( name == null || code == null || parentid == null || description == null )
		{
			logger.debug("name或者code或者parentid或者description为null");
			return null;
		}
		
		Outlets outlets = new Outlets();
		outlets.setName(name);
		outlets.setCode(code);
		if ( isParent == true )
		{
			outlets.setIsparent(1);
		}
		else
		{
			outlets.setIsparent(0);
		}
		outlets.setParentid(parentid);
		outlets.setSortid(-1);	//默认设置为-1
		outlets.setDescription(description);
		outlets.setApp("");	//默认没有应用
		outlets.setRecordcode("000000");	//默认从0开始计数
		Integer id = (Integer)outletsDao.save(outlets);
		if ( id == null )
		{
			logger.debug("id为null");
			return null;
		}

		outletsMap.put(id, name);
		return id;
	}
	
	/**
	 * 通过网点id更新网点信息
	 * @param id	网点id
	 * @param code	网点号
	 * @param name	网点名称
	 * @param description	网点描述
	 * @return
	 * true - 更新成功<br>
	 * false - 更新失败
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updateOutlets(Integer id, String code, String name, String description)
	{
		if ( id == null || code == null || name == null || description == null )
		{
			logger.debug("id或者code或者name或者description为null");
			return false;
		}
		
		Outlets outlets = outletsDao.findById(id);
		if ( outlets == null )
		{
			logger.debug("网点" + id + "不存在");
			return false;
		}
		
		outlets.setCode(code);
		outlets.setName(name);
		outlets.setDescription(description);
		outletsDao.update(outlets);
		
		outletsMap.put(id, name);
		return true;
	}
	
	/**
	 * 初始化网点id-网点名对应MAP
	 * @return
	 * true - 初始化成功<br>
	 * false - 初始化失败
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public void initOutletsMap()
	{
		List<Outlets> outletsList = outletsDao.findAll();
		if ( outletsList != null && outletsList.size() > 0 )
		{
			for ( Outlets outlets : outletsList )
			{
				outletsMap.put(outlets.getId(), outlets.getName());
			}
		}
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> queryAllOutletsForDevice()
	{
		return outletsDao.queryAllOutletsForDevice();
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Outlets findOutletsById(Integer id)
	{
		return outletsDao.findById(id);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void updateOutlets(Outlets outlets)
	{
		outletsDao.update(outlets);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public Outlets getOutletsByCode(String code)
	{
		return outletsDao.getOutletsByCode(code);
	}
}
