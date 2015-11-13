package com.centerm.action.business.statistics.querybytime;

import javax.annotation.Resource;
import org.junit.Test;

import com.centerm.BaseTest;

public class QueryByTimeMainActionTest extends BaseTest{
	//private Logger logger = LogManager.getLogger(QueryByTimeMainActionTest.class);
	@Resource
	private QueryByTimeMainAction queryByTimeMainAction;
	
	@Test
	public void loadBusinessStatisticsTest(){
		queryByTimeMainAction.setStartDate("2014-01-01");
		queryByTimeMainAction.setEndDate("2015-01-01");
		queryByTimeMainAction.setOutlet("1,2");
		queryByTimeMainAction.setBussType("1,2,3,4");
		queryByTimeMainAction.setPage(1);
		queryByTimeMainAction.setRows(20);
		
		queryByTimeMainAction.setQueryType("3");
		
		queryByTimeMainAction.loadBusinessStatistics();
	}
}
