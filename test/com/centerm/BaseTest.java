package com.centerm;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-test.xml")
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
//@TestExecutionListeners({
//	ServletTestExecutionListener.class,
//    DependencyInjectionTestExecutionListener.class,
//    DirtiesContextTestExecutionListener.class,
//    TransactionalTestExecutionListener.class,
//    SqlScriptsTestExecutionListener.class
//})
public class BaseTest
{
	
}
