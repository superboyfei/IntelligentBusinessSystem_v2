package com.centerm.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.centerm.entity.Authority;

@Repository("authorityDao")
public class AuthorityDao extends BaseDao<Authority>
{
	/**
	 * 通过用户id查询权限索引记录
	 * @param userid	用户id
	 * @return	权限索引记录列表
	 */
	public List<?> queryAuthorityByUserid(Integer userid)
	{
		String hql = "from Authority where userid = :userid";
		Query query = getSession().createQuery(hql);
		query.setParameter("userid", userid);
		List<?> list = query.list();
		return list;
	}
	
	/**
	 * 通过用户id删除权限索引记录
	 * @param userid	用户id
	 */
	public void deleteAuthorityByUserid(Integer userid)
	{
		String hql = "delete from Authority where userid = :userid";
		Query query = getSession().createQuery(hql);
		query.setParameter("userid", userid);
		query.executeUpdate();
	}
}
