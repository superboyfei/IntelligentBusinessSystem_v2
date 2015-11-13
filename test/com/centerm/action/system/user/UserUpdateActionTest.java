package com.centerm.action.system.user;

import java.util.Random;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.centerm.BaseTest;

public class UserUpdateActionTest extends BaseTest
{
	private Logger logger = LogManager.getLogger(UserUpdateActionTest.class);
	@Resource
	private UserUpdateAction userUpdateAction;
	@Resource
	private UserAddAction userAddAction;
	
	@Test
	public void testUpdateUser()
	{
		userAddAction.setUid(String.valueOf(new Random().nextInt(999999)));
		userAddAction.setName("测试");
		userAddAction.setPassword("123");
		userAddAction.saveUser();
		Assert.assertTrue(userAddAction.getResult() == true);
		logger.info("保存用户帐号");
		String id = userAddAction.getUserVo().getId();
		userUpdateAction.setId(id);
		userUpdateAction.setName("测试更新");
		userUpdateAction.setNewPass("321");
		userUpdateAction.updateUser();
		logger.info("更新用户帐号成功测试");
		Assert.assertTrue(userUpdateAction.getResult() == true);
	}
}
