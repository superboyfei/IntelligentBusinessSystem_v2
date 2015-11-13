package com.centerm.util.apkutil.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipFile;

/**
 * 通过ApkInfo 里的applicationIcon从APK里解压出icon图片并存放到磁盘上
 */
public class IconUtil {
	
	/**
	 * 从指定的apk文件里获取指定file的流，写入到指定的路径文件中
	 * @param apkpath apk文件路径
	 * @param fileName apk文件名
	 * @param outputPath 输出文件路径
	 * @return
	 */
	public static void extractFileFromApk(String apkpath, String fileName, String outputPath) throws Exception {
		File file = new File(outputPath);
		if(!file.exists())
		{
			file.getParentFile().mkdirs();
		}
		try(ZipFile zFile = new ZipFile(apkpath);
			InputStream is = zFile.getInputStream(zFile.getEntry(fileName));
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file), 1024);
			BufferedInputStream bis = new BufferedInputStream(is, 1024);)
		{
			byte[] b = new byte[1024];
			while(bis.read(b) != -1)
			{
				bos.write(b);
			}
			bos.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
