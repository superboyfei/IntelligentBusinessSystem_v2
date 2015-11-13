package com.centerm.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.centerm.entity.Device;

@Repository("deviceDao")
public class DeviceDao extends BaseDao<Device>
{
	/**
	 * 通过筛选条件分页查询设备记录
	 * @param outlets	网点集合
	 * @param deviceTypes	设备类型集合
	 * @param start	起始记录
	 * @param limit	记录条数
	 * @return	当前页的设备记录表
	 */
	public List<?> queryDeviceByPage(String[] outlets, String[] deviceTypes, Integer start, Integer limit)
	{
		String hql = "from Device where outlets in (:outlets) and type in (:deviceTypes)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("outlets", outlets, StringType.INSTANCE);
		query.setParameterList("deviceTypes", deviceTypes, StringType.INSTANCE);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.list();
	}
	
	/**
	 * 通过筛选条件查询设备记录总数
	 * @param outlets	网点集合
	 * @param deviceTypes	设备类型集合
	 * @return	记录总数
	 */
	public int queryDeviceCount(String[] outlets, String[] deviceTypes)
	{
		String hql = "select count(*) from Device where outlets in (:outlets) and type in (:deviceTypes)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("outlets", outlets, StringType.INSTANCE);
		query.setParameterList("deviceTypes", deviceTypes, StringType.INSTANCE);
		return Integer.valueOf(query.uniqueResult().toString());
	}
	
	public Device getDeviceBySerial(String serial){
		String hql = " from Device where serial = :serial";
		Query query = getSession().createQuery(hql);
		query.setParameter("serial", serial);
		return (Device) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<String> getDeviceAppByOutlets(String outlet)
	{
		String hql = "select o.app from Outlets o where o.id in (:outlet)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("outlet", outlet.split(","), StringType.INSTANCE);
		List<String> deviceAppIds = query.list();
		return deviceAppIds;
	}

	public void publishApp(String outlet, String selectValue)
	{
		String hql = "update Device d set d.app=:selectValue where d.outlets in (:outlet)";
		Query query = getSession().createQuery(hql);
		query.setParameter("selectValue", selectValue);
		query.setParameterList("outlet", outlet.split(","), StringType.INSTANCE);
		query.executeUpdate();
		hql = "update Outlets o set o.app=:selectValue where o.id in (:outlet) and o.isparent = :isparent";
		query = getSession().createQuery(hql);
		query.setParameter("selectValue", selectValue);
		query.setParameter("isparent", 0);
		query.setParameterList("outlet", outlet.split(","), StringType.INSTANCE);
		query.executeUpdate();
	}

	public int shutDownDevice(List<String> serialList, Integer status){
		
		String hql = "update Device d set d.status = :status where d.serial in (:serialList) ";
		Query query = getSession().createQuery(hql);
		query.setParameter("status", status);
		query.setParameterList("serialList", serialList);
		return query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Device> findAllOnlineDevice(int status){
		String hql = "From Device d where d.status = :status";
		Query query = getSession().createQuery(hql);
		query.setParameter("status", status);
		return query.list();
	}
	
	public int updateAllDeviceFirmware(String firmware){
		String hql = "update Device d set d.firmware = :firmware";
		Query query = getSession().createQuery(hql);
		query.setParameter("firmware", firmware);
		return query.executeUpdate();
	}

}
