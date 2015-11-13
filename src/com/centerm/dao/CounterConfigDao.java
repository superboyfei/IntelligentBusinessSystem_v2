package com.centerm.dao;


import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.centerm.entity.CounterConfig;

@Repository("counterConfigDao")
public class CounterConfigDao extends BaseDao<CounterConfig> {
	
	public CounterConfig findByOutletsAndIp(String outletsCode, String ip ,Boolean isPublic) {
		String hql = "select c from Outlets o, CounterConfig c where o.id = c.outlets "
				+"and c.isPublic = :isPublic and o.code = :outletsCode and c.ip = :ip";
		Query query = getSession().createQuery(hql);
		query.setParameter("isPublic", isPublic);
		query.setParameter("outletsCode", outletsCode);
		query.setParameter("ip", ip);
		return (CounterConfig) query.uniqueResult();
	}
	
	public CounterConfig findByOutlets(String outletsCode, Boolean isPublic) {
		String hql = "select c from Outlets o, CounterConfig c where o.id = c.outlets "
				+"and c.isPublic = :isPublic and o.code = :outletsCode";
		Query query = getSession().createQuery(hql);
		query.setParameter("isPublic", isPublic);
		query.setParameter("outletsCode", outletsCode);
		return (CounterConfig) query.uniqueResult();
	}

	public List<?> findPublicForDown(String outletsCode, Boolean isPublic) {
		String hql = "select new map(c.name as name,c.filepath as filepath) "
				+"from Outlets o, CounterConfig c where o.id = c.outlets "
				+"and c.isPublic = :isPublic and o.code = :outletsCode";
		Query query = getSession().createQuery(hql);
		query.setParameter("isPublic", isPublic);
		query.setParameter("outletsCode", outletsCode);
		return query.list();
	}
	
	public List<?> findPrivateForDown(String outletsCode, String ip, Boolean isPublic) {
		String hql = "select new map(c.name as name,c.filepath as filepath) "
				+"from Outlets o, CounterConfig c where o.id = c.outlets "
				+"and c.isPublic = :isPublic and o.code = :outletsCode and c.ip = :ip";
		Query query = getSession().createQuery(hql);
		query.setParameter("isPublic", isPublic);
		query.setParameter("outletsCode", outletsCode);
		query.setParameter("ip", ip);
		return query.list();
	}

}
