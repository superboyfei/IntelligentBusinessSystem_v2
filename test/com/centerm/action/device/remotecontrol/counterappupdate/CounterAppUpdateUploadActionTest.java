package com.centerm.action.device.remotecontrol.counterappupdate;

import java.io.File;
import javax.annotation.Resource;
import org.junit.Test;
import com.centerm.BaseTest;

public class CounterAppUpdateUploadActionTest extends BaseTest {
	@Resource
	private CounterAppUpdateUploadAction counterAppUpdateUploadAction;
	
	@Test
	public void uploadCounterAppFileTest(){
		File telnetFile = new File("D:" + File.separator + "234.dat");
		String fileNameString = telnetFile.getName();
		
		counterAppUpdateUploadAction.setCounterAppFile(telnetFile);
		counterAppUpdateUploadAction.setCounterAppFileFileName(fileNameString);
		
		counterAppUpdateUploadAction.uploadCounterAppFile();
	}

}
