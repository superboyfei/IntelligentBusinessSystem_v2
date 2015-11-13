package com.centerm.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FormatUtil
{
	private static final Logger logger = LogManager.getLogger(FormatUtil.class);
	
	/**
	 * 将String[]转换为Integer[]
	 * @param strings	String数组
	 * @return	Integer数组
	 */
	public static Integer[] StringArrayToIntegerArray(String[] strings)
	{
		if ( strings == null )
		{
			logger.debug("strings为null");
			return null;
		}
		
		try
		{
			Integer[] integers = new Integer[strings.length];
			for ( int i = 0; i < integers.length; i++ )
			{
				integers[i] = Integer.parseInt(strings[i]);
			}
			
			return integers;
		}
		catch(Exception e)
		{
			logger.error("String[]转换为Integer[]出现异常", e);
			return null;
		}
	}
}
