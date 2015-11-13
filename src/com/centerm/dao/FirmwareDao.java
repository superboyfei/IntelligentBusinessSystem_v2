package com.centerm.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.centerm.entity.Firmware;
import com.centerm.vo.FirmwareDownfileVo;

@Repository("firmwareDao")
public class FirmwareDao extends BaseDao<Firmware>
{
	/**
	 * 通过固件类型id查询固件列表
	 * @param type	固件类型id
	 * @return	固件记录列表
	 */
	public List<?> queryFirmwareByType(Integer type)
	{
		String hql = "from Firmware where type = :type and status = :status";
		Query query = getSession().createQuery(hql);
		query.setParameter("type", type);
		query.setParameter("status", 1);
		return query.list();
	}
	
	/**
	 * 查找下一版本的firmware用于下载报文
	 * @param code
	 * @param lastVersion
	 * @return
	 */
	public FirmwareDownfileVo findFirmwareForDownfile(String typeCode,String lastVersion){
		String hql = "select t.code as code,f.version as version, f.name as name,"
				+" f.filepath as filepath,f.md5 as md5,f.ismust as ismust from Firmware f ,FirmwareType t"
				+" where f.type = t.id and t.code = :typeCode and f.status = :status"
				+" and f.version = :lastVersion";
		Query query = getSession().createQuery(hql);
		query.setParameter("status", 1);
		query.setParameter("typeCode", typeCode);
		query.setParameter("lastVersion", lastVersion);
		query.setResultTransformer(Transformers.aliasToBean(FirmwareDownfileVo.class));
		return (FirmwareDownfileVo) query.uniqueResult();
	}

	public Firmware findFirmwareByCodeAndVersion(String code,String version){
		String sql = "select f from Firmware f,FirmwareType t where f.type = t.id and f.lastversion = :version "
				+" and t.code = :code and f.status = :status";
		Query query = getSession().createQuery(sql);
		query.setParameter("code", code);
		query.setParameter("version", version);
		query.setParameter("status", 1);
		return (Firmware) query.uniqueResult();
	}

	public List<?> findLatestFirmwareIdAndCode() {
		String sql = "select new map(f.id as id ,t.code as code) from Firmware f,FirmwareType t "
				+" where f.type = t.id and f.version = t.version and f.status = :status ";
				
		Query query = getSession().createQuery(sql);
		query.setParameter("status", 1);
		return query.list();
	}
	
	public Firmware findByVersionAndLastVersionAndType(String version, String lastversion, Integer type) {
		String sql = "from Firmware f where f.type = :type and f.version = :version "
					+" and f.status = :status and f.lastversion = :lastversion";
				
		Query query = getSession().createQuery(sql);
		query.setParameter("status", 1);
		query.setParameter("type", type);
		query.setParameter("version", version);
		query.setParameter("lastversion", lastversion);
		return (Firmware) query.uniqueResult();
	}

}
