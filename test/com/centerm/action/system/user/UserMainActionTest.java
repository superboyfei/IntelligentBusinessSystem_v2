package com.centerm.action.system.user;

import java.util.Random;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.centerm.BaseTest;

public class UserMainActionTest extends BaseTest
{
	private Logger logger = LogManager.getLogger(UserMainActionTest.class);
	@Resource
	private UserMainAction userMainAction;
	@Resource
	private UserAddAction userAddAction;
	
	@Test
	public void testDelUser()
	{
		userAddAction.setUid(String.valueOf(new Random().nextInt(999999)));
		userAddAction.setName("测试");
		userAddAction.setPassword("123");
		userAddAction.saveUser();
		Assert.assertTrue(userAddAction.getResult() == true);
		logger.info("保存用户帐号");
		String id = userAddAction.getUserVo().getId();
		userMainAction.setId(id);
		userMainAction.delUser();
		logger.info("删除用户帐号成功测试");
		Assert.assertTrue(userMainAction.getResult() == true);
		userMainAction.setId("0");
		userMainAction.delUser();
		logger.info("删除用户帐号失败测试");
		Assert.assertTrue(userMainAction.getResult() == false);
	}
	
	@Test
	public void testLoadUsersByPage()
	{
		userMainAction.setPage(0);
		userMainAction.setRows(20);
		userMainAction.loadUsersByPage();
		logger.info("获取用户信息列表测试");
		Assert.assertTrue(userMainAction.getResult() == true);
	}
}
