package com.centerm.action.system.outlets;

import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import com.centerm.BaseTest;

public class OutletsMainActionTest extends BaseTest
{
	private Logger logger = LogManager.getLogger(OutletsMainActionTest.class);
	@Resource
	private OutletsMainAction outletsMainAction;
	
	@Test
	public void testAddOutlets()
	{
		outletsMainAction.setName("测试网点1");
		outletsMainAction.setCode("1");
		outletsMainAction.setDescription("测试");
		outletsMainAction.setIsParent(true);
		outletsMainAction.setParentid(0);
		outletsMainAction.addOutlets();
		logger.info("新增网点信息测试");
		Assert.assertTrue(outletsMainAction.isResult() == true);
	}
	
	@Test
	public void testUpdateOutlets()
	{
		outletsMainAction.setName("测试网点1");
		outletsMainAction.setCode("1");
		outletsMainAction.setDescription("测试");
		outletsMainAction.setIsParent(true);
		outletsMainAction.setParentid(0);
		outletsMainAction.addOutlets();
		Assert.assertTrue(outletsMainAction.isResult() == true);
		Integer id = Integer.valueOf(outletsMainAction.getOutletsVo().getId());
		outletsMainAction.setId(id);
		outletsMainAction.setName("修改测试网点1");
		outletsMainAction.setCode("2");
		outletsMainAction.setDescription("修改测试");
		outletsMainAction.updateOutlets();
		logger.info("更新网点信息测试");
		Assert.assertTrue(outletsMainAction.isResult() == true);
	}
	
	@Test
	public void testLoadOutletsInfoById()
	{
		outletsMainAction.setName("网点信息1");
		outletsMainAction.setCode("5501");
		outletsMainAction.setDescription("网点信息");
		outletsMainAction.setIsParent(true);
		outletsMainAction.setParentid(0);
		outletsMainAction.addOutlets();
		Assert.assertTrue(outletsMainAction.isResult() == true);
		Integer id = Integer.valueOf(outletsMainAction.getOutletsVo().getId());
		outletsMainAction.setId(id);
		outletsMainAction.loadOutletsInfoById();
		logger.info("获取网点信息测试");
		Assert.assertTrue(outletsMainAction.isResult() == true);
		Assert.assertTrue(outletsMainAction.getOutletsVo().getName().equals("网点信息1"));
		Assert.assertTrue(outletsMainAction.getOutletsVo().getDescription().equals("网点信息"));
		Assert.assertTrue(outletsMainAction.getOutletsVo().getCode().equals("5501"));
	}
	
}
