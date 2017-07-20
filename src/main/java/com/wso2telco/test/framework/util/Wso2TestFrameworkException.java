package com.wso2telco.test.framework.util;

import org.apache.log4j.Logger;

public class Wso2TestFrameworkException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2620308600094925335L;
	private static Logger logger = Logger
			.getLogger(Wso2TestFrameworkException.class);

	public Wso2TestFrameworkException(String message) {
		super(message);
		logger.error(message);
	}

}
