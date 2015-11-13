package com.centerm.service.manage.user;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.AuthorityDao;
import com.centerm.dao.FunctionDao;
import com.centerm.entity.Authority;

@Service("authorityService")
public class AuthorityService
{
	private static final Logger logger = LogManager.getLogger(AuthorityService.class);
	
	@Resource(name = "authorityDao")
	private AuthorityDao authorityDao;
	@Resource(name = "functionDao")
	private FunctionDao functionDao;
	
	/**
	 * 获得用户功能列表
	 * @param id	用户id
	 * @return	用户功能列表，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> getUserFunctionList(Integer id)
	{
		if ( id == null )
		{
			logger.debug("id为null");
			return null;
		}
		
		List<?> functionList = functionDao.queryUserFunctionByUserid(id);
		if ( functionList == null )
		{
			logger.debug("functionList为null");
		}
		
		return functionList;
	}
	
	/**
	 * 获得所有功能列表
	 * @return	功能列表，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> getAllFunction()
	{
		List<?> functionList = functionDao.queryAllFunction();
		if ( functionList == null )
		{
			logger.debug("functionList为null");
		}
		
		return functionList;
	}
	
	/**
	 * 通过用户id获得权限索引记录表
	 * @param userid	用户id
	 * @return	权限索引记录表，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> getAuthorityListByUserid(Integer userid)
	{
		if ( userid == null )
		{
			logger.debug("userid为null");
			return null;
		}
		
		List<?> authorityList = authorityDao.queryAuthorityByUserid(userid);
		if ( authorityList == null )
		{
			logger.debug("authorityList为null");
		}
		
		return authorityList;
	}
	
	/**
	 * 更新用户权限索引
	 * @param userid	用户id
	 * @param functionids	用户功能id
	 * @return
	 * true - 保存成功<br>
	 * false - 保存失败
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updateUserAuthority(Integer userid, String functionids)
	{
		if ( userid == null || functionids == null )
		{
			logger.debug("userid或者functionids为null");
			return false;
		}
		
		authorityDao.deleteAuthorityByUserid(userid);	//移除所有该用户的权限索引记录
		if( StringUtils.isNotBlank(functionids) )	//添加新的权限索引记录
		{
			String[] functionidList = functionids.split(",");
			for ( String functionid : functionidList )
			{
				Authority authority = new Authority();
				authority.setUserid(userid);
				authority.setFunctionid(Integer.parseInt(functionid));
				authorityDao.save(authority);
			}
		}
		
		return true;
	}
}
