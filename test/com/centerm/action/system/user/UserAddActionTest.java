package com.centerm.action.system.user;

import java.util.Random;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.centerm.BaseTest;

public class UserAddActionTest extends BaseTest
{
	private Logger logger = LogManager.getLogger(UserAddActionTest.class);
	@Resource
	private UserAddAction userAddAction;
	
	@Test
	public void testValidateUid()
	{
		userAddAction.setUid("admin");
		userAddAction.validateUid();
		Assert.assertTrue(userAddAction.getResult() == false);
		logger.info("用户帐号已存在测试");
		userAddAction.setUid(String.valueOf(new Random().nextInt(999999)));
		userAddAction.validateUid();
		logger.info("用户帐号不存在测试");
		Assert.assertTrue(userAddAction.getResult() == true);
	}
	
	@Test
	public void testSaveUser()
	{
		userAddAction.setUid(String.valueOf(new Random().nextInt(999999)));
		userAddAction.setName("测试");
		userAddAction.setPassword("123");
		userAddAction.saveUser();
		Assert.assertTrue(userAddAction.getResult() == true);
		logger.info("保存用户帐号成功测试");
	}
}
