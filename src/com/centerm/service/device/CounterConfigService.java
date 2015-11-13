package com.centerm.service.device;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.CounterConfigDao;
import com.centerm.entity.CounterConfig;

@Service("counterConfigService")
public class CounterConfigService {
	
	private static final Boolean IS_PRIVATE = false;
	private static final Boolean IS_PUBLIC = true;
	
	@Resource(name = "counterConfigDao")
	private CounterConfigDao counterConfigDao;
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> findPublicForDown(String outletsCode){
		return counterConfigDao.findPublicForDown(outletsCode, IS_PUBLIC);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<?> findPrivateForDown(String outletsCode, String ip){
		return counterConfigDao.findPrivateForDown(outletsCode, ip, IS_PRIVATE);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public CounterConfig findPublicByOutlets(String outletsCode){
		return counterConfigDao.findByOutlets(outletsCode, IS_PUBLIC);
	}
	
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public CounterConfig findPrivateByOutletsAndIp(String outletsCode, String ip){
		return counterConfigDao.findByOutletsAndIp(outletsCode, ip, IS_PRIVATE);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Serializable save(CounterConfig config){
		return counterConfigDao.save(config);
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void update(CounterConfig config){
		counterConfigDao.update(config);
	}
}
