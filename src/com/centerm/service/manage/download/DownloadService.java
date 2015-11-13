package com.centerm.service.manage.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centerm.dao.DownloadFileDao;
import com.centerm.entity.DownloadFile;
import com.centerm.listener.FtpServerListener;
import com.centerm.util.ByteUtil;
import com.opensymphony.xwork2.ActionContext;

@Service("downloadService")
public class DownloadService
{
	private static final Logger logger = LogManager.getLogger(DownloadService.class);
	
	@Resource(name = "downloadFileDao")
	private DownloadFileDao downloadFileDao;
	
	/**
	 * 获取所有下载文件列表
	 * @return	下载文件列表，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<DownloadFile> getAllDownloadFile()
	{
		List<DownloadFile> downloadFileList = downloadFileDao.findAll();
		if ( downloadFileList == null )
		{
			logger.debug("downloadFileList为null");
		}
		
		return downloadFileList;
	}
	
	/**
	 * 通过id获取下载文件
	 * @param id	下载文件id
	 * @return	下载文件对象，如果失败则为null
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public DownloadFile getDownloadFile(Integer id)
	{
		if ( id == null )
		{
			logger.debug("id为null");
			return null;
		}
		
		DownloadFile downloadFile = downloadFileDao.findById(id);
		if ( downloadFile == null )
		{
			logger.debug("downloadFile：" + id + "不存在");
		}
		
		return downloadFile;
	}
	
	/**
	 * 通过id删除下载文件
	 * @param id	下载文件id
	 * @return
	 * true - 删除成功<br>
	 * false - 删除失败
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteDownloadFile(Integer id)
	{
		if ( id == null )
		{
			logger.debug("id为null");
			return false;
		}
		
		DownloadFile downloadFile = downloadFileDao.findById(id);
		if ( downloadFile == null )
		{
			logger.debug("downloadFile：" + id + "不存在");
			return false;
		}
		
		downloadFileDao.delete(downloadFile);	//删除数据库记录
		
		File file = new File(FtpServerListener.USER_DIR + downloadFile.getFilepath());
		boolean ret = FileUtils.deleteQuietly(file);	//删除文件
		if ( ret == false )
		{
			logger.debug("删除下载文件失败");
			return false;
		}
		return true;
	}
	
	/**
	 * 解析下载文件
	 * @param downloadFileFile	文件
	 * @param downloadFileName	文件名
	 * @return	下载文件对象，如果失败则为null
	 * @throws Exception
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public DownloadFile parseDownloadFile(File downloadFileFile, String downloadFileName) throws Exception
	{
		byte[] fileData = new byte[(int)downloadFileFile.length()];
		
		RandomAccessFile raf = new RandomAccessFile(downloadFileFile, "rw");
		byte[] tmp = new byte[1024*1024*10];
		int nums = 0;
		int lastRead = 0;
		int fileSize = (int)downloadFileFile.length();
		while(nums < fileSize){
			if(nums + 1024*1024*10 > fileSize){
				lastRead = fileSize - nums;
				raf.read(tmp);
				break;
			}
			raf.read(tmp);
			System.arraycopy(tmp, 0, fileData, nums, 1024*1024*10);
			nums += 1024*1024*10;
		}
		if(lastRead > 0){
			System.arraycopy(tmp, 0, fileData, nums, lastRead);
		}
		raf.close();
		
		String tmpPath = FtpServerListener.USER_DIR + File.separator + "download" + File.separator + "tmp";
		//确保临时目录存在
		new File(tmpPath).mkdirs();
		String saveName = downloadFileFile.getName();	//采用框架自动命名作为保存文件名，防止重名
		String suffix = downloadFileName.substring(downloadFileName.lastIndexOf("."));	//获得文件后缀
		saveName = saveName.substring(0,  saveName.lastIndexOf(".")) + suffix;
		File tmpFile = new File(tmpPath + File.separator + saveName);	//先将下载文件保存在临时目录
		
		FileOutputStream output = new FileOutputStream(tmpFile);
		byte[] file_tmp = new byte[1024*1024*10];
		nums = 0;
		lastRead = 0;
		while(nums < fileSize){
			if(nums + 1024*1024*10 > fileSize){
				lastRead = fileSize - nums;
				break;
			}
			System.arraycopy(fileData, nums, file_tmp, 0, 1024*1024*10);
			output.write(file_tmp);
			nums += 1024*1024*10;
		}
		if(lastRead > 0){
			byte[] last_tmp = new byte[lastRead];
			System.arraycopy(fileData, nums, last_tmp, 0, lastRead);
			output.write(last_tmp);
		}
		output.flush();
		output.close();
		
		DownloadFile downloadFile = new DownloadFile();
		downloadFile.setName(downloadFileName);
		downloadFile.setSize((int)downloadFileFile.length());
		downloadFile.setUploadtime(new Date());
		downloadFile.setIconpath(getIconPathThroughSuffix(suffix));
		downloadFile.setFilepath(File.separator + "download" + File.separator + saveName);
		
		MessageDigest digest = MessageDigest.getInstance("md5");
		digest.update(fileData);	//计算MD5值
		String digestMd5 = ByteUtil.bytesToHexString(digest.digest());
		downloadFile.setMd5(digestMd5.toUpperCase());
		
		ActionContext.getContext().getSession().put("saveName", saveName);
		ActionContext.getContext().getSession().put("downloadFile", downloadFile);
		return downloadFile;
	}
	
	/**
	 * 保存下载文件
	 * @param downloadFileDescription	文件描述
	 * @return
	 * true - 保存成功<br>
	 * false - 保存失败
	 * @throws IOException
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean saveDownloadFile(String downloadFileDescription) throws IOException
	{
		String saveName = (String)ActionContext.getContext().getSession().get("saveName");
		DownloadFile downloadFile = (DownloadFile)ActionContext.getContext().getSession().get("downloadFile");
		downloadFileDescription = downloadFileDescription.replaceAll("\n", "\\%");	//将换行符替换为数据库可保存的符号，便于前台识别
		downloadFile.setDescription(downloadFileDescription);
		
		Integer id = (Integer)downloadFileDao.save(downloadFile);
		if ( id == null )
		{
			logger.debug("id为null");
			return false;
		}
		
		
		File tmpFile = new File(FtpServerListener.USER_DIR + File.separator + "download" + File.separator + "tmp" + File.separator + saveName);
		File file = new File(FtpServerListener.USER_DIR + File.separator + "download" + File.separator + saveName);
		if ( file.exists() )
		{
			boolean ret = FileUtils.deleteQuietly(file);	//防止文件重名异常
			if ( ret == false )
			{
				logger.debug("删除临时下载文件失败");
				return false;
			}
		}
		FileUtils.moveFile(tmpFile, file);	//将下载文件从临时目录转移到FTP下载文件目录
		
		return true;
	}
	
	/**
	 * 删除临时下载文件
	 * @return
	 * true - 下载成功<br>
	 * false - 下载失败
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean deleteTmpDownloadFile()
	{
		String saveName = (String)ActionContext.getContext().getSession().get("saveName");
		
		File tmpFile = new File(FtpServerListener.USER_DIR + File.separator + "download" + File.separator + "tmp" + File.separator + saveName);
		boolean ret = FileUtils.deleteQuietly(tmpFile);
		if ( ret == false )
		{
			logger.debug("删除临时下载文件失败");
			return false;
		}
		
		return true;
	}
	

	/**
	 * 根据扩展名获取文件图标
	 * @param suffix	文件扩展名
	 * @return	对应的图片路径
	 */
	public String getIconPathThroughSuffix(String suffix){
		String iconPath = "res" + File.separator + "images" + File.separator + "download" + File.separator;
		Map<String, String> suffixToPathMap = new HashMap<String,String>();
		suffixToPathMap.put(".png", "picture.png");
		suffixToPathMap.put(".jpg", "picture.png");
		suffixToPathMap.put(".bmp", "picture.png");
		suffixToPathMap.put(".ico", "picture.png");
		suffixToPathMap.put(".jpeg", "picture.png");
		suffixToPathMap.put(".gif", "picture.png");
		suffixToPathMap.put(".xls", "excel.png");
		suffixToPathMap.put(".xlsx", "excel.png");
		suffixToPathMap.put(".xlsm", "excel.png");
		suffixToPathMap.put(".xltm", "excel.png");
		suffixToPathMap.put(".xltx", "excel.png");
		suffixToPathMap.put(".doc", "word.png");
		suffixToPathMap.put(".docx", "word.png");
		suffixToPathMap.put(".docm", "word.png");
		suffixToPathMap.put(".dotx", "word.png");
		suffixToPathMap.put(".dotm", "word.png");
		suffixToPathMap.put(".ppt", "ppt.png");
		suffixToPathMap.put(".pptx", "ppt.png");
		suffixToPathMap.put(".pptm", "ppt.png");
		suffixToPathMap.put(".ppsm", "ppt.png");
		suffixToPathMap.put(".pot", "ppt.png");
		suffixToPathMap.put(".bat", "config.png");
		suffixToPathMap.put(".sh", "config.png");
		suffixToPathMap.put(".pdf", "pdf.png");
		suffixToPathMap.put(".jar", "java.png");
		suffixToPathMap.put(".java", "java.png");
		suffixToPathMap.put(".class", "java.png");
		suffixToPathMap.put(".msi", "msi.png");
		suffixToPathMap.put(".exe", "msi.png");
		suffixToPathMap.put(".txt", "txt.png");
		suffixToPathMap.put(".sql", "txt.png");
		suffixToPathMap.put(".xml", "txt.png");
		suffixToPathMap.put(".xmls", "txt.png");
		suffixToPathMap.put(".apk", "android.png");
		suffixToPathMap.put(".rmvb", "vedio.png");
		suffixToPathMap.put(".avi", "vedio.png");
		suffixToPathMap.put(".mp4", "vedio.png");
		suffixToPathMap.put(".wav", "vedio.png");
		suffixToPathMap.put(".3gp", "vedio.png");
		suffixToPathMap.put(".rar", "zip.png");
		suffixToPathMap.put(".tar", "zip.png");
		suffixToPathMap.put(".gz", "zip.png");
		suffixToPathMap.put(".7z", "zip.png");
		suffixToPathMap.put(".zip", "zip.png");
		suffixToPathMap.put(".iso", "iso.png");
		
		if(suffixToPathMap.get(suffix) == null) {
			iconPath = iconPath + "blank.png";
		} else {
			iconPath = iconPath + suffixToPathMap.get(suffix);
		}
		return iconPath;
	}
	
}
