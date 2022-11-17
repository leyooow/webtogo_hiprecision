package com.ivant.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ivant.cms.helper.ClassHelper;

public class PropertiesUtil 
{
	private static final Logger logger = Logger.getLogger(PropertiesUtil.class);
	
	/**
	 * Get the properties file defined in the properties file path.
	 * 
	 * @param propertiesFilePath the properties file path
	 * 
	 * @return the properties
	 */
	public static final Properties getProperties(String propertiesFilePath)
	{				
		final InputStream inputStream = ClassHelper.getResourceAsStream(propertiesFilePath);
		
		Properties properties = new Properties();
		if (inputStream == null) 
		{
			logger.info("The properties file \"" + propertiesFilePath + "\" cannot be found!");
			properties = null;
		}
		else
		{
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				logger.info("Cannot read the properties file \"" + propertiesFilePath + "\"!");
				properties = null;
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
		
		return properties;
	}
}