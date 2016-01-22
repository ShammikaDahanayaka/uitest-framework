package com.wso2telco.test.framework.configuration;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.wso2telco.test.framework.util.Wso2TestFrameworkException;

public class CoreConfigurations implements Configuration {

	private static Logger logger = Logger.getLogger(CoreConfigurations.class);
	private Properties configDataFile;

	private static Configuration coreConfig = null;
	
	
	public CoreConfigurations(){
		ConfigurationReader reader = new ConfigurationReader();
        try {
        	configDataFile = reader.readConfig();
        } catch (Wso2TestFrameworkException ex) {
            logger.debug(ex);
        }
	}
	
	 public CoreConfigurations(String path) {
		 ConfigurationReader reader = new ConfigurationReader(path);
	        try {
	        	configDataFile = reader.readConfig();
	        } catch (Wso2TestFrameworkException ex) {
	            logger.debug(ex);
	            throw ex;
	        }

	    }

	@Override
	public String getValue(String key) {
		String val =configDataFile.getProperty(key);
		val = (val==null)? "":val;
		return val;
		
	}

	@Override
	public void setValue(String key,String value) {
		configDataFile.setProperty(key, value);
	}

	public static Configuration getCoreConfig() {
		 if (coreConfig == null)
			 coreConfig = new CoreConfigurations();
		return coreConfig;
	}

	public static Configuration getCoreConfig(String path) {
		 if (coreConfig == null)
			 coreConfig = new CoreConfigurations(path);
		return coreConfig;
	}
	

}
