package com.centerm.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.centerm.entity.FirmwareType;

@Repository("firmwareTypeDao")
public class FirmwareTypeDao extends BaseDao<FirmwareType>
{
	/**
	 * 通过固件code查询固件类型记录
	 * @param code	固件code
	 * @return	固件类型记录
	 */
	public FirmwareType queryFirmwareTypeByCode(String code)
	{
		String hql = "from FirmwareType where code = :code";
		Query query = getSession().createQuery(hql);
		query.setParameter("code", code);
		return (FirmwareType)query.uniqueResult();
	}

	public FirmwareType findFirmwareTypeByCode(Integer firmwareCode)
	{
		String hql = "from FirmwareType f where f.code = :firmwareCode";
		Query query = getSession().createQuery(hql);
		query.setParameter("firmwareCode", firmwareCode);
		return (FirmwareType) query.uniqueResult();
	}

	public FirmwareType queryFirmwareByCodeAndVersion(String firmwareCode, String version)
	{
		String hql = "from FirmwareType f where f.code = :firmwareCode and f.version = :version";
		Query query = getSession().createQuery(hql);
		query.setParameter("firmwareCode", Integer.valueOf(firmwareCode));
		query.setParameter("version", version);
		return (FirmwareType) query.uniqueResult();
	}
	
}
