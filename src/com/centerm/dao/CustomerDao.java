package com.centerm.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.centerm.entity.Customer;

@Repository("customerDao")
public class CustomerDao extends BaseDao<Customer>
{
	
	public Customer findByAccount(String account){
		String hql = "from Customer c where c.account like :account";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("account", "%"+account+"%");
		return (Customer) query.uniqueResult();
	}
	
	public Customer findByIdnum(String idnum){
		String hql = "from Customer c where c.idnum = :idnum";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("idnum", idnum);
		return (Customer) query.uniqueResult();
	}
}
