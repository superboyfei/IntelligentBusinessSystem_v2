package com.centerm.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.centerm.entity.Outlets;

@Repository("outletsDao")
public class OutletsDao extends BaseDao<Outlets>
{
	/**
	 * 查询所有网点记录
	 * @return	网点记录
	 */
	public List<?> queryAllOutlets()
	{
		String hql = "from Outlets order by sortid";	//通过sordid排序，保证界面网点节点树构建顺序
		Query query = getSession().createQuery(hql);
		List<?> list = query.list();
		return list;
	}
	
	/**
	 * 查询非父网点记录数
	 * @return	网点记录数
	 */
	public Long queryAllOutletsExceptParent()
	{
		String hql = "select count(*) from Outlets where isparent != 1";
		Query query = getSession().createQuery(hql);
		return (Long)query.uniqueResult();
	}
	
	/**
	 * 通过网点号查询网点记录
	 * @param code	网点号
	 * @return	网点记录
	 */
	public List<?> queryOutletsByCode(String code)
	{
		String hql = "from Outlets where code = :code";
		Query query = getSession().createQuery(hql);
		query.setParameter("code", code);
		return query.list();
	}
	
	/**
	 * 根据ID获取网点信息
	 * @return	网点记录
	 */
	public Outlets queryOutletsById(Integer outletsId)
	{
		String hql = "select o from Outlets o where o.id = :id";	//通过sordid排序，保证界面网点节点树构建顺序
		Query query = getSession().createQuery(hql);
		query.setParameter("id", outletsId);
		Outlets outlets = (Outlets) query.uniqueResult();
		return outlets;
	}
	
	/**
	 * 判断是否有指定名称的网点信息
	 * @return	boolean
	 */
	public boolean existName(String name,Integer parentid,boolean isParent)
	{
		String hql = "from Outlets o where o.name = :name and o.parentid = :parentid and o.isparent = :isparent";	//通过sordid排序，保证界面网点节点树构建顺序
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		query.setParameter("parentid", parentid);
		query.setParameter("isparent", isParent == true ? 1 : 0);
		Outlets outlets = (Outlets) query.uniqueResult();
		return outlets != null ? true : false;
	}
	
	/**
	 * 根据网点代码查询网点
	 * @param code 网点代码
	 * @return
	 */
	public Outlets getOutletsByCode(String code){
		String hql = "from Outlets o where o.code = :code";
		Query query = getSession().createQuery(hql);
		query.setParameter("code", code);
		return (Outlets) query.uniqueResult();
	}
	
	/**
	 * 
	 * @param code 网点代码
	 * @return
	 */
	public List<?> queryAllOutletsForDevice(){
		String hql = "select new map(o.code as code, o.name as name) from Outlets o where o.isparent = :isparent";
		Query query = getSession().createQuery(hql);
		query.setParameter("isparent", 0);
		return query.list();
	}

}
