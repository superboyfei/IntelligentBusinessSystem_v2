package com.centerm.service.manage.user;

import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.centerm.dao.UserDao;
import com.centerm.entity.User;
import com.centerm.util.UserPasswordCrypter;

@Service("userService")
public class UserService
{
	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	@Resource(name = "userDao")
	private UserDao userDao;
	
	/**
	 * 验证用户
	 * @param uid	用户账号
	 * @param password	用户密码密文
	 * @return
	 * true - 验证通过<br>
	 * false - 验证不通过
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean validateUser(String uid, String password)
	{
		if ( uid == null || password == null )
		{
			logger.debug("uid或者password为null");
			return false;
		}
		
		User user = userDao.queryUserByUid(uid);
		if ( user == null )
		{
			logger.debug("用户" + uid + "不存在");
			return false;
		}
		
		if( !uid.equals(user.getUid()))
		{
			logger.debug("用户" + uid + "的账号不匹配");
			return false;
		}
		
		if ( !user.getPassword().equals(password) )
		{
			logger.debug("用户" + uid + "的密码不匹配");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 通过用户账号获取用户记录
	 * @param id	用户账号
	 * @return	用户记录，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public User getUserByUid(String uid)
	{
		User user = userDao.queryUserByUid(uid);
		if ( user == null )
		{
			logger.debug("用户" + uid + "不存在");
			return null;
		}
		
		return user;
	}
	
	/**
	 * 校验用户密码
	 * @param uid	用户账号
	 * @param keyPassword	用户密码
	 * @return
	 * true - 密码正确
	 * false - 密码错误
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean validatePassword(String uid, String password)
	{
		if ( uid == null || password == null )
		{
			logger.debug("uid或者password为null");
			return false;
		}
		
		User user = userDao.queryUserByUid(uid);
		if ( user == null )
		{
			logger.debug("用户" + uid + "不存在");
			return false;
		}
		
		String userPassword = user.getPassword();
		String keyPassword = UserPasswordCrypter.crypt(password);
		if ( userPassword.equals(keyPassword) )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 通过用户账号更新用户信息
	 * @param uid	用户账号
	 * @param name	用户姓名
	 * @param password	用户密码（如果为空则不更新）
	 * @return
	 * true - 更新成功<br>
	 * false - 更新失败
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updateUserByUid(String uid, String name, String password)
	{
		if ( uid == null || name == null )
		{
			logger.debug("uid或者name");
			return false;
		}
		
		User user = userDao.queryUserByUid(uid);
		if ( user == null )
		{
			logger.debug("用户" + uid + "不存在");
			return false;
		}
		
		user.setName(name);
		if ( StringUtils.isNotBlank(password) )
		{
			String keyPassword = UserPasswordCrypter.crypt(password);
			user.setPassword(keyPassword);
		}
		
		userDao.update(user);
		return true;
	}
	
	/**
	 * 分页查询匹配用户账号字段的用户记录
	 * @param matchUid	用户账号匹配字段
	 * @param currentPage	当前页
	 * @param recordNumInPage	每页记录数
	 * @return	当前页的用户记录列表，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> getUserListByUidMatchAndPage(String matchUid, Integer currentPage, Integer recordNumInPage)
	{
		if ( matchUid == null || currentPage == null || recordNumInPage == null )
		{
			logger.debug("matchUid或者currentPage或者recordNumInPage为null");
			return null;
		}
		
		Integer start = currentPage * recordNumInPage - recordNumInPage;
		Integer limit = recordNumInPage;
		
		List<?> userList;
		if ( matchUid.isEmpty() )	//如果参数未指定，则查询所有用户
		{
			userList = userDao.findByPage(start, limit);
		}
		else
		{
			userList = userDao.queryUserByUidMatchAndPage(matchUid, start, limit);
		}
		
		if ( userList == null )
		{
			logger.debug("userList为null");
		}
		
		return userList;
	}
	
	/**
	 * 获得匹配用户账号字段的用户个数
	 * @param matchUid	用户账号匹配字段
	 * @return	用户个数，如果失败则为-1
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public int getUserCountByUidMatch(String matchUid)
	{
		if ( matchUid == null )
		{
			logger.debug("matchUid为null");
			return -1;
		}
		
		Long count;
		if ( matchUid.isEmpty() )	//如果参数未指定，则查询所有用户个数
		{
			count = userDao.getRecordCount();
		}
		else
		{
			count = userDao.queryUserCountByUidMatch(matchUid);
		}
		
		if ( count == null )
		{
			logger.debug("count为null");
			return -1;
		}
		
		return count.intValue();
	}
	
	/**
	 * 删除用户
	 * @param id	用户记录id
	 * @return
	 * true - 删除成功<br>
	 * false - 删除失败
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteUser(Integer id)
	{
		if ( id == null )
		{
			logger.debug("id为null");
			return false;
		}
		
		User user = userDao.findById(id);
		if ( user == null )
		{
			logger.debug("user为null");
			return false;
		}
		
		userDao.delete(user);
		return true;
	}
	
	/**
	 * 新增用户
	 * @param uid	用户账号
	 * @param name	用户密码
	 * @param password	用户密码
	 * @param isadmin	是否为管理员
	 * @return	新增用户id，如果失败则为null
	 */
	@Transactional(rollbackFor = Exception.class)
	public Integer addUser(String uid, String name, String password, Integer isadmin)
	{
		if ( uid == null || name == null || password == null || isadmin == null )
		{
			logger.debug("uid或者name或者password或者isadmin为null");
			return null;
		}
		
		User user = new User();
		user.setUid(uid);
		user.setName(name);
		user.setPassword(UserPasswordCrypter.crypt(password));
		user.setIsadmin(isadmin);
		Integer id = (Integer)userDao.save(user);
		if ( id == null )
		{
			logger.debug("id为null");
			return null;
		}
		
		return id;
	}
	
	/**
	 * 用户账号是否被使用
	 * @param uid	用户账号
	 * @return
	 * true - 已被使用<br>
	 * false - 未被使用
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean isUidUsed(String uid)
	{
		User user = userDao.queryUserByUid(uid);
		if ( user == null )
		{
			return false;
		}
		
		if( !uid.equals(user.getUid()))
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * 通过id获取用户记录
	 * @param id	用户id
	 * @return	用户记录，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public User getUserById(Integer id)
	{
		User user = userDao.findById(id);
		if ( user == null )
		{
			logger.debug("user为null");
		}
		
		return user;
	}
}
