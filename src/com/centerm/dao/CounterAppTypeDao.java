package com.centerm.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.centerm.entity.CounterApp;
import com.centerm.entity.CounterAppType;

@Repository("counterAppTypeDao")
public class CounterAppTypeDao extends BaseDao<CounterAppType> {
	/**
	 * 通过柜员应用Id查询柜员应用类型记录
	 * @param counterAppTypeId	柜员应用id
	 * @return	柜员应用类型记录
	 */
	public CounterAppType queryCounterAppTypeById(Integer counterAppTypeId)
	{
		String hql = "from CounterAppType where id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", counterAppTypeId);
		return (CounterAppType)query.uniqueResult();
	}
	
	/**
	 * 通过柜员应用名称查询柜员应用类型记录
	 * @param counterAppTypeName	柜员应用名称
	 * @return	柜员应用类型记录
	 */
	public CounterAppType queryCounterAppTypeByName(String counterAppTypeName)
	{
		String hql = "from CounterAppType where name = :name";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", counterAppTypeName);
		return (CounterAppType)query.uniqueResult();
	}
	/**
	 * 通过柜员应用code查询柜员应用类型记录
	 * @param code	柜员应用code
	 * @return	柜员应用类型记录
	 */
	public CounterAppType queryCounterAppTypeByCode(String code)
	{
		String hql = "from CounterAppType where code = :code";
		Query query = getSession().createQuery(hql);
		query.setParameter("code", code);
		return (CounterAppType)query.uniqueResult();
	}
	
	public CounterApp findByVersionAndLastVersionAndType(String version, String lastversion, Integer type) {
		String sql = "from CounterApp c where c.type = :type and c.version = :version "
				+" and c.status = :status and c.lastversion = :lastversion";
			
		Query query = getSession().createQuery(sql);
		query.setParameter("status", 1);
		query.setParameter("type", type);
		query.setParameter("version", version);
		query.setParameter("lastversion", lastversion);
		
		return (CounterApp) query.uniqueResult();
	}

}
