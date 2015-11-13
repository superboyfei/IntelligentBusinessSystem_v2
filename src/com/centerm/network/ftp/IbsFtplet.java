package com.centerm.network.ftp;

import java.io.File;
import java.io.IOException;

import org.apache.ftpserver.ftplet.DefaultFtplet;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.FtpletResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("ibsFtplet")
public class IbsFtplet extends DefaultFtplet
{
	private static final Logger logger = LogManager.getLogger(IbsFtplet.class);

    public FtpletResult onConnect(FtpSession session) throws FtpException, IOException
    {
        logger.debug(session.getClientAddress().getAddress().toString() + "连接ftp");
        
        return super.onConnect(session);
    }

    public FtpletResult onDisconnect(FtpSession session) throws FtpException, IOException
    {
    	logger.debug(session.getClientAddress().getAddress().toString() + "断开ftp");
    	
        return super.onDisconnect(session);
    }

	public FtpletResult onDownloadStart(FtpSession session, FtpRequest request) throws FtpException, IOException
	{
		String path = session.getFileSystemView().getWorkingDirectory().getAbsolutePath();
		String name = request.getArgument();  
		String file = path + File.separator + name;
		logger.debug(session.getClientAddress().getAddress().toString() + "开始下载：" + file);
		
		return super.onDownloadStart(session, request);
	}
    
	public FtpletResult onDownloadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException
	{
		String path = session.getFileSystemView().getWorkingDirectory().getAbsolutePath();
		String name = request.getArgument();  
		String file = path + File.separator + name;
		logger.debug(session.getClientAddress().getAddress().toString() + "结束下载：" + file);
		
		return super.onDownloadEnd(session, request);
	}
}
