package com.centerm.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.centerm.entity.DeviceType;

@Repository("deviceTypeDao")
public class DeviceTypeDao extends BaseDao<DeviceType>
{	
	public DeviceType getDeviceTypeByCode(String code)
	{
		String hql = "from DeviceType t where t.code = :code";
		Query query = getSession().createQuery(hql);
		query.setParameter("code", code);
		return (DeviceType) query.uniqueResult();
	}
}
