package com.centerm.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FileUtils;
import org.apache.tools.zip.*;


public class FileUtil {

	//删除指定路径的文件
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		return file.delete();
	}
	
	/**
	 * 获取给定日期内的日志文件路径
	 * @param baseFilePath:String 待检查目录
	 * @param startDate:String 开始日期
	 * @param endDate:String 结束日期
	 * */
	public static List<String> getFilePathsBetweenDates(String baseFilePath,String startDate,String endDate){
		List<String> filePaths = new ArrayList<String>();
		List<String> fileNames = getFileNames(baseFilePath,false);
		
		for(String fileName:fileNames){
			String[] strs = fileName.split("\\.");
			if(strs.length > 2 && DateUtil.checkDateBetweenDates(strs[1], startDate, endDate)){
				filePaths.add(baseFilePath + File.separator + fileName);
			}
		}
		if(endDate.equals(DateUtil.dateToString(new Date()))){
			filePaths.add(baseFilePath + File.separator + "debugDay.log");
		}
		return filePaths;
	}
	
	/**
	 * 获取指定目录下的所有文件夹和文件的名字
	 * @param baseFilePath:String 待查询的根目录路径
	 * @param isDirectoryIncluded:boolean 是否包含目录
	 * @return 指定目录的包含文件文件夹的名字
	 * @author Fly
	 * */
	public static List<String> getFileNames(String baseFilePath,boolean isDirectoryIncluded){
		List<String> fileNames = new ArrayList<String>();
		File baseFile = new File(baseFilePath);
		File[] files = baseFile.listFiles();
		for(File file:files){
			if(file.isDirectory()){
				if(isDirectoryIncluded == true){
					fileNames.add(file.getName());
				}
				fileNames.addAll(getFileNames(file.getAbsolutePath(),false));
			} else {
				fileNames.add(file.getName());
			}
		}
		return fileNames;
	}
	
	/**
	 * 获取指定目录下的所有文件夹和文件的全路径 
	 * @param baseFilePath:String 待查询的根目录路径
	 * @param isDirectoryIncluded:boolean 是否包含目录路径
	 * @return 指定目录的包含文件文件夹的路径
	 * @author Fly
	 * */
	public static List<String> getFilePaths(String baseFilePath,boolean isDirectoryIncluded){
		List<String> filePaths = new ArrayList<String>();
		File baseFile = new File(baseFilePath);
		File[] files = baseFile.listFiles();
		for(File file:files){
			if(file.isDirectory()){
				if(isDirectoryIncluded == true){
					filePaths.add(file.getAbsolutePath());
				}
				filePaths.addAll(getFilePaths(file.getAbsolutePath(),isDirectoryIncluded));
			} else {
				filePaths.add(file.getAbsolutePath());
			}
		}
		return filePaths;
	}
	
	/**
	 * 获取文件的名字
	 * @param filePath:String 文件全路径
	 * @return 获取文件的名字
	 * @author Fly
	 * */
	public static String getFileName(String filePath){
		String[] fileDirs = filePath.split("\\" + File.separator);
		String fileName = fileDirs[fileDirs.length - 1];
		
		return fileName;
	}
	/**
	 * 获取文件的名字(正斜杠/)
	 * @param filePath:String 文件全路径
	 * @return 获取文件的名字
	 * @author Fly
	 * */
	public static String getReverseFileName(String filePath){
		String[] fileDirs = filePath.split("/");
		String fileName = fileDirs[fileDirs.length - 1];
		
		return fileName;
	}
	
	/**
	 * 获取文件的大小
	 * @param filePath:String 文件全路径
	 * @return 获取文件的大小
	 * @author Fly
	 * */
	public static long getFileSize(String filePath){
		File file = new File(filePath);
		return file.length();
	}
	
	/**
	 * 获取文件的最后修改时间
	 * @param filePath:String 文件全路径
	 * @return 获取文件的最后修改时间
	 * @author Fly
	 * */
	public static String getFileTime(String filePath){
		File file = new File(filePath);
		return DateUtil.getDateStringByMillis(file.lastModified());
	}
	
	/**
	 * 删除指定目录下的所有文件夹和文件
	 * @param file:File 文件对象
	 * @return 删除是否成功
	 * @author Fly
	 * */
	/*
	 private static boolean deleteFile(File file) {
		File[] files = file.listFiles();
		for (File deleteFile : files) {
			if (deleteFile.isDirectory()) {
				// 如果是文件夹，则递归删除下面的文件后再删除该文件夹
				if (!deleteFile(deleteFile)) {
					// 如果失败则返回
					return false;
				}
			} else {
				if (!deleteFile.delete()) {
					// 如果失败则返回
					return false;
				}
			}
		}
		return true;
	}
	*/

	public static boolean zipFiles(List<String> filePaths, String zipFilePath) {
		byte[] buf = new byte[1024];
		int readLength = 0;
		try {
			FileOutputStream f = new FileOutputStream(zipFilePath);
			// 创建压缩输出流
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(
					f));

			// 设置Zip文件注释
			zos.setEncoding("gbk");
			zos.setComment("a downloading file from Centerm system by Fly");
			for (int i = 0; i < filePaths.size(); i++) {
				// 针对单个文件建立读取流
				BufferedInputStream bin = new BufferedInputStream(
						new FileInputStream(filePaths.get(i)));
				// ZipEntry ZIP 文件条目
				// putNextEntry 写入新条目，并定位到新条目开始处
				String fileName = filePaths.get(i).split("\\\\")[5];
				zos.putNextEntry(new ZipEntry(filePaths.get(i).substring(filePaths.get(i).length()) + File.separator + fileName));
				
				while ((readLength = bin.read(buf, 0, 1024)) != -1) {
					zos.write(buf,0,readLength);
				}
				bin.close();
				zos.flush();
			}
			zos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//获取指定文件名的文件后缀
	public static String getSuffixByFileName(String fileName){
		String[] fileParts = fileName.split("\\.");
		return fileParts[fileParts.length - 1];
	}
	
	//删除指定目录下的指定后缀名的文件
	public static void deleteCertainSuffix(String zipDir,String suffix){
		File file = new File(zipDir);
		File[] fileList = file.listFiles();
		for(File f:fileList) {
			if(suffix.equals(getSuffixByFileName(f.getName()))){
				FileUtils.deleteQuietly(f);
			}
		}
	}
	
	//获取应用图标存放到指定目录下
	public static void getFileImage(File tmpFile,String tmpIconPath){
		ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(tmpFile);
		BufferedImage savedImage = new BufferedImage(icon.getIconWidth(),
				icon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		savedImage.createGraphics().drawImage(icon.getImage(), 0, 0, null);
		File tmpIconFile = new File(tmpIconPath);
		try {
			if(!tmpIconFile.exists()){
				tmpIconFile.mkdirs();
				tmpIconFile.createNewFile();
			}
			ImageIO.write(savedImage, "png", tmpIconFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//获取应用图标存放到指定目录下
	public static void getFileBigImage(File tmpFile,String tmpIconPath){
		ImageIcon icon = new ImageIcon();
		 try {
			   sun.awt.shell.ShellFolder shellFolder = sun.awt.shell.ShellFolder.getShellFolder(tmpFile);
		       icon.setImage(shellFolder.getIcon(true));
		} catch (FileNotFoundException e) {
		      e.printStackTrace();
		}
		
		BufferedImage savedImage = new BufferedImage(icon.getIconWidth(),
				icon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		savedImage.createGraphics().drawImage(icon.getImage(), 0, 0, null);
		File tmpIconFile = new File(tmpIconPath);
		try {
			if(!tmpIconFile.exists()){
				tmpIconFile.mkdirs();
				tmpIconFile.createNewFile();
			}
			ImageIO.write(savedImage, "png", tmpIconFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
