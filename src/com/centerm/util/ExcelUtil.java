package com.centerm.util;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil
{   
    /**
     * 创建HSSFWorkbook对象，设置Excel表格的样式
     * @param title	第一行标题
     * @param excelHeader	第二行标题
     * @return	HSSFWorkbook对象
     * @throws IOException
     */
    public static HSSFWorkbook createWorkbook(String title, String[] excelHeader) throws IOException 
    {
    	HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		
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
    	
    	return wb;
    }
}