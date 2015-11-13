package com.centerm.service.manage.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.util.DateUtil;

@Service("analyzeLogService")
public class AnalyzeLogService {
	private static final Logger logger = LogManager.getLogger(AnalyzeLogService.class);
	public Connection conn = null;
	public PreparedStatement statement = null;
	
	/**
	 * 初始化数据库连接
	 * @author Fly
	 * @param void
	 * @return 
	 * 	true -- 连接成功
	 * 	false -- 连接失败
	 * */
	private boolean createSqlConnect(){
		String url = "jdbc:mysql://localhost:3306/ibs?characterEncoding=UTF-8";
		String username = "root";
		String password = "centerm"; // 加载驱动程序以连接数据库 
		try { 
			Class.forName("com.mysql.jdbc.Driver" ); 
			conn = DriverManager.getConnection( url,username, password ); 
		}
		//捕获加载驱动程序异常
		catch ( ClassNotFoundException cnfex ) {
			 System.err.println("装载 JDBC/ODBC 驱动程序失败。" );
			 cnfex.printStackTrace(); 
			 return false;
		} 
		//捕获连接数据库异常
		catch ( SQLException sqlex ) {
			System.err.println( "无法连接数据库" );
			sqlex.printStackTrace(); 
			return false;
		}
		return true;
	}
	
	/**
	 * 关闭数据库连接
	 * @author Fly
	 * @param void
	 * */
	private void closeSqlConnect(){
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 将指定起止日期之间的log日志解析以excel文件形式返回
	 * @param fileDir	database log所在路径
	 * @param startDate	起始日期
	 * @param endDate	终止日期
	 * @return
	 * excel内容 -- 成功<br>
	 * null -- 失败
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public HSSFWorkbook getAnalyzeLogFile(String fileDir, String startDate, String endDate) throws ParseException, IOException
	{
		List<File> logList = new ArrayList<File>();
		Date start = DateUtils.parseDate(startDate, "yyyy-MM-dd");
		Date end = DateUtils.parseDate(endDate, "yyyy-MM-dd");
		
		Collection<File> fileList = FileUtils.listFiles(new File(fileDir), new String[]{"log"}, false);	//获取目录下扩展名为log的所有文件集合，不进行递归
		for ( File file : fileList )
		{
			String name = file.getName();
			if ( name.equals("debug.log") )	//最后一天有效日志
			{
				FileInputStream fis = new FileInputStream(file);
				byte[] bDate = new byte[10];
				int readLen = fis.read(bDate, 0, bDate.length);
				fis.close();
				if ( readLen == 10 )	//该文件有内容
				{
					logList.add(file);
				}
			}
			if ( name.length() == 20 )	//文件名符合日志文件名长度
			{
				String fileDate = name.substring(6, 16);
				Date cur = DateUtils.parseDate(fileDate, "yyyy-MM-dd");
				if ( (start.before(cur) || start.equals(cur)) && (end.after(cur) || end.equals(cur)) )
				{
					logList.add(file);
				}
			}
		}
		if ( logList.size() == 0 )	//如果不存在要分析的日志文件，则直接返回
		{
			return null;
		}
		
		return createAnalyzeLogExcelFile(logList);
	}
	
	/**
	 * 解析logList中的log文件，并将解析结果以excel文件的形式返回
	 * @author Fly
	 * @param logList List<?>--需要解析的log文件列表
	 * @return excel文件
	 * */
	private HSSFWorkbook createAnalyzeLogExcelFile(List<File> logList) throws IOException {
		if(!createSqlConnect()){
			logger.debug("数据库连接失败");
			return null;
		}
		
		String title = "LOG分析结果一览表";
		String[] excelHeader = { "时间", "SQL语句", "执行过程", "耗时(毫秒)", "涉及表行数", "平均耗时 (毫秒)" };
		HSSFWorkbook wb = new HSSFWorkbook();
		for(File file : logList) {
			HSSFSheet sheet = null;
			if(file.getName().equals("debug.log")) {
				sheet = wb.createSheet("debug-" + DateUtil.dateToString(new Date()) + ".log");
			} else {
				sheet = wb.createSheet(file.getName());
			}
			
	    	HSSFCellStyle style = wb.createCellStyle();
	    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);	//居中
	    	HSSFFont font = wb.createFont();    
	    	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	//粗体显示    
	    	font.setFontHeightInPoints((short)12);
	    	style.setFont(font);
	    	
	    	CellRangeAddress region = new CellRangeAddress(0, 0, 0, excelHeader.length-1);
	    	sheet.addMergedRegion(region);	//合并单元格
	    	sheet.setColumnWidth(0, 9000);  //表头宽度
	    	
	    	HSSFRow firstRow = sheet.createRow(0);
	    	HSSFCell titleCell = firstRow.createCell(0);
	    	titleCell.setCellValue(title);
	    	titleCell.setCellStyle(style);
	    	
	    	style = wb.createCellStyle();
	    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);	//居中
	    	font = wb.createFont();    
	    	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	//粗体显示
	    	style.setFont(font);
	    	
	    	HSSFRow row = sheet.createRow(1);
	    	for (int i = 0; i < excelHeader.length; i++)
	    	{
	    		HSSFCell cell = row.createCell(i);
	    		cell.setCellValue(excelHeader[i]);
	    		cell.setCellStyle(style);
	    		sheet.setColumnWidth(i, 4500);
	    	}
	    	
	    	List<String> logInfoList = getAnalyzeInfos(file);
			
			for(int i = 0;i < logInfoList.size() / 6;i ++){
				HSSFRow infoRow = sheet.createRow(i + 2);
				for(int j = 0;j < 6;j ++){
					HSSFCell cell = infoRow.createCell(j);
		    		cell.setCellValue(logInfoList.get(i * 6 + j));
		    		cell.setCellStyle(style);
		    		sheet.setColumnWidth(i, 4500);
				}
			}
		}
		
		closeSqlConnect();
		return wb;
	}
	
	/**
	 * 解析logFile文件，将解析结果按顺序添加进List中返回
	 * @author Fly
	 * @param logFile File--需要解析的log文件
	 * @return 包含解析信息的List
	 * */
	@SuppressWarnings("resource")
	private List<String> getAnalyzeInfos(File logFile){
		List<String> logInfoList = new ArrayList<String>();
		int countFlag = 0;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(logFile));
			StringBuffer stringBuffer = new StringBuffer();
			String str = null;
			while((str = bufferedReader.readLine()) != null) {
				if(str.startsWith("20") && countFlag == 0){
					String[] resultStrings = stringBuffer.toString().split("-centerm-");
					if(resultStrings.length == 2){
						String timeStamp = getLogTimeStamp(resultStrings[0]);
						String SQL = getLogSQL(resultStrings[0]);
						String SQLTranslate = getSQLTranslate(SQL);
						String time = getLogTime(resultStrings[1]);
						String effort = getLogEffort(resultStrings[0], resultStrings[1]);
						String average = getLogAverage(time,effort);
						logInfoList.add(timeStamp);
						logInfoList.add(SQL);
						logInfoList.add(SQLTranslate);
						logInfoList.add(time);
						logInfoList.add(effort);
						logInfoList.add(average);
						
//						System.out.println("===>timeStamp " + timeStamp);
//						System.out.println("===>SQLTranslate " + SQLTranslate);
//						System.out.println("===>SQL " + SQL);
//						System.out.println("===>time " + time);
//						System.out.println("===>effort " + effort);
//						System.out.println("===>average " + average);
//						System.out.println(resultStrings[0] + "\n" + resultStrings[1]);
//						System.out.println("===============================");
						
					}
					
					stringBuffer.delete(0, stringBuffer.length());
					countFlag = (countFlag + 1) % 2;
					stringBuffer.append(str + " ");
				} else if(str.startsWith("20") && countFlag == 1){
					stringBuffer.append("-centerm- " + str + " ");
					countFlag = (countFlag + 1) % 2;
				} else {
					stringBuffer.append(str + " ");
				}
			}
			
			String[] resultStrings = stringBuffer.toString().split("-centerm-");
			if(resultStrings.length == 2){
				String timeStamp = getLogTimeStamp(resultStrings[0]);
				String SQL = getLogSQL(resultStrings[0]);
				String SQLTranslate = getSQLTranslate(SQL);
				String time = getLogTime(resultStrings[1]);
				String effort = getLogEffort(resultStrings[0], resultStrings[1]);
				String average = getLogAverage(time,effort);
				
				logInfoList.add(timeStamp);
				logInfoList.add(SQL);
				logInfoList.add(SQLTranslate);
				logInfoList.add(time);
				logInfoList.add(effort);
				logInfoList.add(average);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logInfoList;
	}
	
	//获取执行语句时间戳
	private String getLogTimeStamp(String logString){
		return logString.split(" - ")[0].trim().split(" ")[1];
	}
	
	//获取SQL语句
	private String getLogSQL(String logString){
		return logString.split("executed.")[1].trim();
	}
	
	//根据获取的SQL语句得到操作信息
	private String getSQLTranslate(String sql){
		if(sql.trim().toLowerCase().startsWith("insert")) {
			return "增加";
		} else if(sql.trim().toLowerCase().startsWith("delete")) {
			return "删除";
		} else if(sql.trim().toLowerCase().startsWith("update")) {
			return "修改";
		} else if(sql.trim().toLowerCase().startsWith("select")) {
			return "查询";
		} else if(sql.trim().toLowerCase().startsWith("call")) {
			return "调用存储过程";
		} else {
			return "其他操作";
		}
	}
	
	//获取SQL语句执行耗时（毫秒）
	private String getLogTime(String logString){
		String[] millisStrings = logString.split("millis.");
		return millisStrings[0].trim().split(" ")[millisStrings[0].trim().split(" ").length -1];
	}
	
	//获取SQL操作涉及到的表行数
	private String getLogEffort(String firstResultStr,String secondResultStr) {
		String sql = getLogSQL(firstResultStr);
		if(sql.toLowerCase().trim().startsWith("select")){
			return "" + getFetchRow(sql);
		} else {
			String[] effortStrings = secondResultStr.split(" executed. effort ");
			return effortStrings[1].trim().split("\\.")[0];
		}
	}
	
	//获取SQL所查询的表的总行数
	private int getFetchRow(String sql){
		if(sql.toLowerCase().contains(" where ")){
			sql = sql.toLowerCase().split(" where ")[0];
		}
		ResultSet rs = null;
		int rowLenth = 0;
		try {
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery(sql);
			rs.last();
			rowLenth = rs.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowLenth;
	}
	
	//计算平均执行时间
	private String getLogAverage(String time,String effort){
		if(Integer.parseInt(effort) == 0){
			effort = "1";
		}
		return String.format("%.6f",(double)Double.parseDouble(time) / Double.parseDouble(effort));
	}
	
}
