package com.centerm.dao;

import java.io.Serializable;

@SuppressWarnings("unchecked")
public class BusinessStatisticsBaseDao<T, TID> extends BaseDao<T>
{
	/**
	 * 根据复合主键查询记录
	 * @param tid	复合主键对象
	 * @return	记录实例
	 */
	protected T findByTid(TID tid)
	{
		return (T)getSession().get(entityClass, (Serializable)tid);
	}
}
