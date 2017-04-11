package com.wso2telco.test.framework.configuration;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.wso2telco.test.framework.util.Default;
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
		String path = Default.CONFIG_PATH.getValue();
				path=getClass().getResource(path).getFile();
		FileOutputStream out;
		ConfigurationReader reader = new ConfigurationReader();
		reader.readConfig();
		try {
			out = new FileOutputStream(path);
			configDataFile.setProperty(key, value);
	    	configDataFile.store(out,new Date().toString());
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
