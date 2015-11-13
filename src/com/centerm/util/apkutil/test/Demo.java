package com.centerm.util.apkutil.test;
import com.centerm.util.apkutil.entity.ApkInfo;
import com.centerm.util.apkutil.utils.ApkUtil;
import com.centerm.util.apkutil.utils.IconUtil;

public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String apkpath = "D:\\yingyong.apk";
			if (args.length > 0) {
				apkpath = args[0];
			}
			ApkInfo apkInfo = new ApkUtil().getApkInfo("WebRoot/tool/aapt",apkpath);
			// 打印获取到的信息
			System.out.println(apkInfo);
			// 获取Icon并保存到指定位置
			IconUtil.extractFileFromApk(apkpath, apkInfo.getApplicationIcon(), "D:\\yingyong.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
