package com.centerm.service.business;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.BusinessStatisticsDao;
import com.centerm.entity.po.QueryByDealPo;
import com.centerm.entity.po.QueryByOutletsPo;
import com.centerm.service.manage.business.BusinessService;
import com.centerm.service.manage.outlets.OutletsService;
import com.centerm.util.ExcelUtil;
import com.opensymphony.xwork2.ActionContext;

@Service("statisticsExportService")
public class StatisticsExportService
{
	private static final Logger logger = LogManager.getLogger(StatisticsExportService.class);
	
	@Resource(name = "businessStatisticsDao")
	private BusinessStatisticsDao businessStatisticsDao;
	
	/**
	 * 生成按时间查询excel表
	 * @return	HSSFWorkbook对象，如果失败则为null
	 * @throws IOException
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public HSSFWorkbook getExportDataByTime() throws IOException, ParseException
	{
		String queryType = (String)ActionContext.getContext().getSession().get("queryType");
		String bussType = (String)ActionContext.getContext().getSession().get("bussType");
		String outlet = (String)ActionContext.getContext().getSession().get("outlet");
		String startDate = (String)ActionContext.getContext().getSession().get("startDate");
		String endDate = (String)ActionContext.getContext().getSession().get("endDate");
		
		if ( StringUtils.isBlank(queryType) || StringUtils.isBlank(bussType) || StringUtils.isBlank(outlet) )
		{
			logger.debug("queryType或者 bussType或者outlet为空");
			return null;
		}
		if ( startDate == null || endDate == null )
		{
			logger.debug("startDate或者endDate为null");
			return null;
		}
		
		String[] business = bussType.split(",");
		String[] outlets = outlet.split(",");
		
		switch ( queryType )
		{
			case "0":
			{
				break;
			}
			case "1":
			{
				startDate += "-01";
				endDate += "-31";
				break;
			}
			case "2":
			{
				startDate += "-01-01";
				endDate += "-12-31";
				break;
			}
			case "3":
			{
				String countStartSeason = startDate.substring(5, 6);	//截取季度
				switch ( countStartSeason )
				{
					case "1":
					{
						startDate = startDate.substring(0, 4) + "-01-01";
						break;
					}
					case "2":
					{
						startDate = startDate.substring(0, 4) + "-04-01";
						break;
					}
					case "3":
					{
						startDate = startDate.substring(0, 4) + "-07-01";
						break;
					}
					case "4":
					{
						startDate = startDate.substring(0, 4) + "-10-01";
						break;
					}
					default:
					{
						logger.debug("countStartSeasion：" + countStartSeason + "——不合法");
						return null;
					}
				}
				
				String countEndSeason = endDate.substring(5, 6);	//截取季度
				switch ( countEndSeason )
				{
					case "1":
					{
						endDate = endDate.substring(0, 4) + "-03-31";
						break;
					}
					case "2":
					{
						endDate = endDate.substring(0, 4) + "-06-30";
						break;
					}
					case "3":
					{
						endDate = endDate.substring(0, 4) + "-9-30";
						break;
					}
					case "4":
					{
						endDate = endDate.substring(0, 4) + "-12-31";
						break;
					}
					default:
					{
						logger.debug("countEndSeasion：" + countEndSeason + "——不合法");
						return null;
					}
				}
				break;
			}
			default:
			{
				logger.debug("queryType：" + queryType + "——不合法");
				return null;
			}
		}
		
		String title = "按交易时间查询数据统计信息";
		StringBuffer excelRowHeaderStringBuffer = new StringBuffer("网点名称,交易数量");
		for(int i = 0;i < business.length;i ++) {
			excelRowHeaderStringBuffer.append("," + BusinessService.businessMap.get(Integer.parseInt(business[i])));
		}
		String[] excelHeader = excelRowHeaderStringBuffer.toString().split(","); 
		HSSFWorkbook wb = ExcelUtil.createWorkbook(title, excelHeader);
		HSSFSheet sheet = wb.getSheetAt(0);
		
		HSSFCellStyle style = wb.createCellStyle();
    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);	//居中
    	for(int i = 0;i < outlets.length;i ++) {
    		HSSFRow row = sheet.createRow(i + 2);
    		HSSFCell cell0 = row.createCell(0);
			cell0.setCellStyle(style);
			cell0.setCellValue(OutletsService.outletsMap.get(Integer.parseInt(outlets[i])));
			
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellStyle(style);
			cell1.setCellValue(businessStatisticsDao
					.queryBusinessStatisticsByOutlets(business, outlets[i], startDate, endDate)
					.getCount().toString());
			for(int j = 0;j < business.length;j ++) {
				HSSFCell cell = row.createCell(j + 2);
				cell.setCellStyle(style);
				cell.setCellValue(businessStatisticsDao
					.queryBusinessStatisticsByOutlets(new String[]{business[j]}, outlets[i], startDate, endDate)
					.getCount().toString());
			}
    	}
		
		return wb;
	}
	
	/**
	 * 生成按业务类型查询excel表
	 * @return	HSSFWorkbook对象，如果失败则为null
	 * @throws IOException
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public HSSFWorkbook getExportDataByDeal() throws IOException, ParseException
	{
		String bussType = (String)ActionContext.getContext().getSession().get("bussType");
		String outlet = (String)ActionContext.getContext().getSession().get("outlet");
		String startDate = (String)ActionContext.getContext().getSession().get("startDate");
		String endDate = (String)ActionContext.getContext().getSession().get("endDate");
		
		if ( StringUtils.isBlank(bussType) || StringUtils.isBlank(outlet) )
		{
			logger.debug("bussType或者outlet为空");
			return null;
		}
		if ( startDate == null || endDate == null )
		{
			logger.debug("startDate或者endDate为null");
			return null;
		}
		
		String[] business = bussType.split(",");
		String[] outlets = outlet.split(",");
		
		List<QueryByDealPo> queryByDealPoList = new ArrayList<QueryByDealPo>();
		for ( int i = 0; i < business.length; i++ )
		{
			QueryByDealPo queryByDealPo = businessStatisticsDao.queryBusinessStatisticsByDeal(business[i], outlets, startDate, endDate);
			queryByDealPoList.add(queryByDealPo);
		}
		
		for ( QueryByDealPo queryByDealPo : queryByDealPoList )
		{
			queryByDealPo.setBusinessName(BusinessService.businessMap.get(queryByDealPo.getBusiness()));
		}
		
		String title = "按业务类型查询数据统计信息";
		String[] excelHeader = { "业务类型", "交易数量" };    
		HSSFWorkbook wb = ExcelUtil.createWorkbook(title, excelHeader);
		HSSFSheet sheet = wb.getSheetAt(0);
		
		HSSFCellStyle style = wb.createCellStyle();
    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);	//居中
		for (int i = 0; i < queryByDealPoList.size(); i++)	//设置表格数据
		{
			HSSFRow row = sheet.createRow(i + 2);
			QueryByDealPo queryByDealPo = queryByDealPoList.get(i);
			HSSFCell cell0 = row.createCell(0);
			cell0.setCellStyle(style);
			cell0.setCellValue(queryByDealPo.getBusinessName());
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellStyle(style);
			cell1.setCellValue(queryByDealPo.getCount().toString());
		}
		
		return wb;
	}
	
	/**
	 * 生成按网点查询excel表
	 * @return	HSSFWorkbook对象，如果失败则为null
	 * @throws IOException
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public HSSFWorkbook getExportDataByOutlets() throws IOException, ParseException
	{
		String bussType = (String)ActionContext.getContext().getSession().get("bussType");
		String outlet = (String)ActionContext.getContext().getSession().get("outlet");
		String startDate = (String)ActionContext.getContext().getSession().get("startDate");
		String endDate = (String)ActionContext.getContext().getSession().get("endDate");
		
		if ( StringUtils.isBlank(bussType) || StringUtils.isBlank(outlet) )
		{
			logger.debug("bussType或者outlet为空");
			return null;
		}
		if ( startDate == null || endDate == null )
		{
			logger.debug("startDate或者endDate为null");
			return null;
		}
		
		String[] business = bussType.split(",");
		String[] outlets = outlet.split(",");
		
		List<QueryByOutletsPo> queryByOutletsPoList = new ArrayList<QueryByOutletsPo>();
		for ( int i = 0; i < outlets.length; i++ )
		{
			QueryByOutletsPo queryByOutletsPo = businessStatisticsDao.queryBusinessStatisticsByOutlets(business, outlets[i], startDate, endDate);
			queryByOutletsPoList.add(queryByOutletsPo);
		}
		
		for ( QueryByOutletsPo queryByOutletsPo : queryByOutletsPoList )
		{
			queryByOutletsPo.setOutletsName(OutletsService.outletsMap.get(queryByOutletsPo.getOutlets()));
		}
		
		String title = "按网点查询数据统计信息";
		String[] excelHeader = { "网点名称", "交易数量" };    
		HSSFWorkbook wb = ExcelUtil.createWorkbook(title, excelHeader);
		HSSFSheet sheet = wb.getSheetAt(0);
		
		HSSFCellStyle style = wb.createCellStyle();
    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);	//居中
		for (int i = 0; i < queryByOutletsPoList.size(); i++)	//设置表格数据
		{
			HSSFRow row = sheet.createRow(i + 2);
			QueryByOutletsPo queryByOutletsPo = queryByOutletsPoList.get(i);
			HSSFCell cell0 = row.createCell(0);
			cell0.setCellStyle(style);
			cell0.setCellValue(queryByOutletsPo.getOutletsName());
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellStyle(style);
			cell1.setCellValue(queryByOutletsPo.getCount().toString());
		}
		
		return wb;
	}
}
