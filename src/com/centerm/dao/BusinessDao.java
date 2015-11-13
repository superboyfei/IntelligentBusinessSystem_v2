package com.centerm.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.centerm.entity.Business;

@Repository("businessDao")
public class BusinessDao extends BaseDao<Business>
{
	/**
	 * 查询所有业务记录
	 * @return	业务记录
	 */
	public List<?> queryAllBusiness()
	{
		String hql = "from Business order by sortid";	//通过sordid排序，保证界面业务节点树构建顺序
		Query query = getSession().createQuery(hql);
		List<?> list = query.list();
		return list;
	}
	
	/**
	 * 查询非父业务记录数
	 * @return	业务记录
	 */
	public Long queryAllBusinessExceptParent()
	{
		String hql = "select count(*) from Business where isparent != 1";
		Query query = getSession().createQuery(hql);
		return (Long)query.uniqueResult();
	}

	/**
	 * 通过业务号查询网点记录
	 * @param code	网点号
	 * @return	业务记录
	 */
	public List<?> queryBusinessByCode(String code)
	{
		String hql = "from Business where code = :code";	
		Query query = getSession().createQuery(hql);
		query.setParameter("code", code);
		return query.list();
	}

	public boolean queryBusinessByName(String name)
	{
		String hql = "from Business b where b.name = :name";	
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		Business business = (Business) query.uniqueResult();
		return business != null ? true : false;
	}
}
