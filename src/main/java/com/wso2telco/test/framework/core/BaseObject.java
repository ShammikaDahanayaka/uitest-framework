package com.wso2telco.test.framework.core;


import org.apache.log4j.Logger;

import com.wso2telco.test.framework.configuration.Configuration;
import com.wso2telco.test.framework.configuration.CoreConfigurations;

public abstract class BaseObject {
	private static Logger logger = Logger.getLogger(BaseObject.class);
	protected static Configuration config = CoreConfigurations.getCoreConfig();
	
	public BaseObject() {
		initializeConfig();
		
	}
	
	 private void initializeConfig() {
	        try {
	            logger.trace("Cofiguration initiaize");
	            if (config == null)
	                config = CoreConfigurations.getCoreConfig();
	        } catch (Exception ex) {
	            logger.debug(ex);
	        }
	    }

}
