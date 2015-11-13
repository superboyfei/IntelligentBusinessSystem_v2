package com.centerm.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.centerm.entity.User;
import com.centerm.util.UserPasswordCrypter;

@Repository("userDao")
public class UserDao extends BaseDao<User>
{	
	/**
	 * 通过账号查询用户记录
	 * @param uid	用户账号
	 * @return	用户记录
	 */
	public User queryUserByUid(String uid)
	{
		String hql = "from User where uid = :uid";
		Query query = getSession().createQuery(hql);
		query.setParameter("uid", uid);
		return (User)query.uniqueResult();
	}
	
	/**
	 * 分页查询符合uid条件的用户记录
	 * @param matchUid	uid匹配字段
	 * @param start	起始记录
	 * @param limit	记录条数
	 * @return	用户记录列表
	 */
	public List<?> queryUserByUidMatchAndPage(String matchUid, Integer start, Integer limit)
	{
		String hql = "from User where uid like \'%" + matchUid + "%\'";
		Query query = getSession().createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.list();
	}
	
	/**
	 * 查询符合uid条件的用户记录数
	 * @param matchUid	uid匹配字段
	 * @return	用户记录数
	 */
	public Long queryUserCountByUidMatch(String matchUid)
	{
		String hql = "select count(*) from User where uid like \'%" + matchUid + "%\'";
		Query query = getSession().createQuery(hql);
		return (Long)query.uniqueResult();
	}

//	public String validatePwdByUid(String uid)
//	{
//		String hql = "select u.password from User u where u.uid = :uid";
//		Query query = GetSession().createQuery(hql);
//		query.setParameter("uid", uid);
//		return (String) query.uniqueResult();
//	}

	public void updateUserByUid(String uid, String name, String password)
	{
		if(StringUtils.isNotBlank(password))
		{
			String hql = "update User u set u.name=:name, u.password=:password where u.uid=:uid";
			Query query = getSession().createQuery(hql);
			query.setParameter("name", name);
			query.setParameter("password", UserPasswordCrypter.crypt(password));
			query.setParameter("uid", uid);
			query.executeUpdate();
		}
		else
		{
			String hql = "update User u set u.name=:name where u.uid=:uid";
			Query query = getSession().createQuery(hql);
			query.setParameter("name", name);
			query.setParameter("uid", uid);
			query.executeUpdate();
		}
	}
}
