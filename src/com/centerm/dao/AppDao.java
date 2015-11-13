package com.centerm.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.centerm.entity.App;
import com.centerm.vo.AppDownfileVo;

@Repository("appDao")
public class AppDao extends BaseDao<App>
{
	/**
	 * 通过包名查找应用记录
	 * @param packagename	包名
	 * @return	应用记录列表
	 */
	public List<?> queryAppByPackagename(String packagename)
	{
		String hql = "from App where packagename = :packagename order by uploadtime desc";	//按照上传时间倒序排列
		Query query = getSession().createQuery(hql);
		query.setParameter("packagename", packagename);
		List<?> list = query.list();
		return list;
	}
	
	public List<?> findAppByIds(Integer[] ids){
		String hql = "select new map(a.packagename as packagename,a.versioncode as version) From App a,Business b "
				+" where a.id = b.app and a.status = :status and a.id in (:ids) ";
		Query query = getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		query.setParameter("status", 1);
		return query.list();
	}

	public AppDownfileVo findAppByPackagenameAndVersion(String packagename, Integer version) {

		String hql = "select a.versioncode as version, a.filepath as filepath,a.md5 as md5,a.name as name,"
				+"a.packagename as packagename from App a, Business b where a.status = :status "
				+"and a.id = b.app and a.versioncode = :version and a.packagename = :packagename";
		
		Query query = getSession().createQuery(hql);
		query.setParameter("status", 1);
		query.setParameter("packagename", packagename);
		query.setParameter("version", version);
		query.setResultTransformer(Transformers.aliasToBean(AppDownfileVo.class));
		return (AppDownfileVo) query.uniqueResult();
	}

	public App queryAppInfoByVersion(String version)
	{
		String hql = "from App a where a.version = :version";
		Query query = getSession().createQuery(hql);
		query.setParameter("version", version);
		return (App) query.uniqueResult();
	}
	
	public List<?> findAllAppForLoad(){
		String hql = "select new map(a.id as id, b.name as name, a.iconpath as iconpath) from App a, Business b"
				+" where b.app = a.id";
		Query query = getSession().createQuery(hql);
		return query.list();
	}

}
