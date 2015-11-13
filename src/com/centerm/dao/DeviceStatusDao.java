package com.centerm.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.centerm.entity.DeviceStatus;

@Repository("deviceStatusDao")
public class DeviceStatusDao extends BaseDao<DeviceStatus>
{
	public DeviceStatus getDeviceStatusByCode(Integer code){
		String hql = " From DeviceStatus where code = :code";
		Query query = getSession().createQuery(hql);
		query.setParameter("code", code);
		return (DeviceStatus) query.uniqueResult();
	}
}
