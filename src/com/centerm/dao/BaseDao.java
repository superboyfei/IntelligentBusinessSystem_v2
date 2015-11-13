package com.centerm.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@SuppressWarnings("unchecked")
public class BaseDao<T>
{
	protected final Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Resource(name = "sessionFactory")
    protected SessionFactory sessionFactory;
    
	protected Class<?> entityClass;
	
	public BaseDao()
	{
		ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
		entityClass = (Class<?>)type.getActualTypeArguments()[0];
	}
	
    protected Session getSession()
    {
    	return sessionFactory.getCurrentSession();
    }
    
    /**
     * 添加记录
     * @param t	记录实例
     * @return	记录id
     */
    public Serializable save(T t)
    {
    	Serializable id = getSession().save(t);
    	return id;
    }
    
    /**
     * 添加记录，如果记录存在，则更新该记录
     * @param t	记录实例
     */
    public void saveOrUpdate(T t)
    {
    	getSession().saveOrUpdate(t);
    }
    
    /**
     * 删除记录
     * @param t	记录实例
     */
    public void delete(T t)
    {
    	getSession().delete(t);
    }
    
    /**
     * 根据记录id查询记录
     * @param id	记录id
     * @return	记录实例
     */
    public T findById(Serializable id)
    {
    	return (T)getSession().get(entityClass, id);
    }
    
    /**
     * 根据条件查询记录
     * @param startLimit	起始记录位置
     * @param countLimit	最大记录条数
     * @return	记录列表
     */
    public List<T> findByPage(int startLimit, int countLimit)
    {
    	Criteria criteria = getSession().createCriteria(entityClass);
    	criteria.setFirstResult(startLimit);
    	criteria.setMaxResults(countLimit);
    	return criteria.list();
    }
    
    /**
     * 查询所有记录
     * @return	记录列表
     */
    public List<T> findAll()
    {
    	return getSession().createCriteria(entityClass).list();
    }
    
    /**
     * 更新记录
     * @param t	记录实例
     */
    public void update(T t)
    {
    	getSession().update(t);
    }
    
    /**
     * 获得记录条数
     * @return	记录条数
     */
    public Long getRecordCount()
    {
    	String hql = "select count(*) from " + entityClass.getSimpleName();
    	return (Long)getSession().createQuery(hql).uniqueResult();
    }
}
