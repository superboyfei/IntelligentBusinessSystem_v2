package com.centerm.dao.hubei;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.centerm.dao.BaseDao;
import com.centerm.entity.hubei.OutletsReflect;

@Repository("outletsReflectDao")
public class OutletsReflectDao extends BaseDao<OutletsReflect> {
	
	public OutletsReflect findByIp(String ip, Integer num){
		String ip_like = ip.substring(0, ip.lastIndexOf(".") + 1) + "%";
		String hql = " From OutletsReflect ip where ip.start like :ip_like and ip.startnum <= :num1 and ip.endnum >= :num2";
		Query query = getSession().createQuery(hql);
		query.setParameter("ip_like", ip_like);
		query.setInteger("num1", num);
		query.setInteger("num2", num);
		return (OutletsReflect) query.uniqueResult();
	}
	
	public OutletsReflect findByCode(String code){
		String hql = "From OutletsReflect ip where ip.code = :code";
		Query query = getSession().createQuery(hql);
		query.setParameter("code", code);
		return (OutletsReflect) query.uniqueResult();
	}
	
	public List<?> findByOutlets(String city, String outlets){
		String hql = "From OutletsReflect ip where ip.outlets = :outlets and ip.city = :city";
		Query query = getSession().createQuery(hql);
		query.setParameter("outlets", outlets);
		query.setParameter("city", city);
		return query.list();
	}
	
	public List<?> findBetweenIps(String start, String end, Integer startnum, Integer endnum){
		String ip_like = start.substring(0, start.lastIndexOf(".") + 1) + "%";
		String hql = "From OutletsReflect ip where ip.start like :ip_like and ip.startnum between :num1 and :num2";
		Query query = getSession().createQuery(hql);
		query.setParameter("ip_like", ip_like);
		query.setInteger("num1", startnum);
		query.setInteger("num2", endnum);
		return query.list();
	}
	
}
