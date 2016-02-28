package com.wso2telco.test.framework.core;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public abstract class FObject extends BaseObject{

	//protected static WebDriver driver;
	protected static WebDriver driver;
	final static Logger logger = Logger.getLogger(FObject.class);
	public FObject() {
	}

	public FObject(WebDriver webDriver) {
		super();
		driver = webDriver;
	}

	
	public void logInstruction(String logMessage) {
		if(logger.isDebugEnabled()){
			logger.debug(logMessage);
	}
	}
}
