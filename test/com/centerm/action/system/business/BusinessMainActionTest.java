package com.centerm.action.system.business;

import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import com.centerm.BaseTest;

public class BusinessMainActionTest extends BaseTest
{
	private Logger logger = LogManager.getLogger(BusinessMainActionTest.class);
	
	@Resource
	private BusinessMainAction businessMainAction;
	
	@Test
	public void testAddBusinessGroup()
	{
		businessMainAction.setName("测试业务");
		businessMainAction.setParent(true);
		businessMainAction.setParentid(0);
		businessMainAction.addBusinessGroup();
		logger.info("添加业务组测试");
		Assert.assertTrue(businessMainAction.isResult() == true);
	}
	
}
