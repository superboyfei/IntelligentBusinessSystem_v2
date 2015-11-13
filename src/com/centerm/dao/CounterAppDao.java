package com.centerm.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.centerm.entity.CounterApp;
import com.centerm.vo.CounterAppDownFileVo;

@Repository("counterAppDao")
public class CounterAppDao extends BaseDao<CounterApp> {
	/**
	 * 通过柜员应用类型id查询柜员应用列表
	 * @param type	柜员应用类型id
	 * @return	柜员应用记录列表
	 */
	public List<?> queryCounterAppByType(Integer type)
	{
		String hql = "from CounterApp where type = :type";
		Query query = getSession().createQuery(hql);
		query.setParameter("type", type);
		return query.list();
	}
	
	/**
	 * 查找下一版本的counterApp用于下载报文
	 * @param code
	 * @param lastVersion
	 * @return
	 */
	public CounterAppDownFileVo findCounterAppForDownfile(String typeCode,String lastVersion){
		String hql = "select t.code as code,c.version as version, c.name as name,"
				+" c.filepath as filepath,c.md5 as md5 from CounterApp c ,CounterAppType t"
				+" where c.type = t.id and t.code = :typeCode and t.version <> :version "
				+" and c.lastversion = :lastVersion and c.status = :status";
		Query query = getSession().createQuery(hql);
		query.setParameter("status", 1);
		query.setParameter("typeCode", typeCode);
		query.setParameter("version", lastVersion);
		query.setParameter("lastVersion", lastVersion);
		query.setResultTransformer(Transformers.aliasToBean(CounterAppDownFileVo.class));
		return (CounterAppDownFileVo) query.uniqueResult();
	}
	
	public CounterApp findCounterAppByCodeAndVersion(int code,String version){
		String sql = "select f from CounterApp c,CounterAppType t where c.type = t.id and c.version = :version "
				+" and t.code = :code and c.status = :status";
		Query query = getSession().createQuery(sql);
		query.setParameter("code", code);
		query.setParameter("version", version);
		query.setParameter("status", 1);
		return (CounterApp) query.uniqueResult();
	}

	public List<?> findLatestCounterAppIdAndCode() {
		String sql = "select new map(c.id as id ,t.code as code) from CounterApp c,CounterAppType t "
				+" where c.type = t.id and c.version = t.version and c.status = :status ";
				
		Query query = getSession().createQuery(sql);
		query.setParameter("status", 1);
		return query.list();
	}
}
