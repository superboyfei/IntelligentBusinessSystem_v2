package com.centerm.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.centerm.entity.Function;

@Repository("functionDao")
public class FunctionDao extends BaseDao<Function>
{
	/**
	 * 通过用户id查询用户功能记录
	 * @param userid	用户id
	 * @return	用户功能记录列表
	 */
	public List<?> queryUserFunctionByUserid(Integer userid)
	{
		String hql = "select f from Function f, Authority a where f.id = a.functionid and a.userid = :userid order by f.sortid";	//通过sortid排序，保证界面功能节点树构建顺序
		Query query = getSession().createQuery(hql);
		query.setParameter("userid", userid);
		List<?> list = query.list();
		return list;
	}
	
	/**
	 * 查询所有功能记录
	 * @return	功能记录列表
	 */
	public List<?> queryAllFunction()
	{
		String hql = "from Function order by sortid";	//通过sortid排序，保证界面功能节点树构建顺序
		Query query = getSession().createQuery(hql);
		List<?> list = query.list();
		return list;
	}
}
