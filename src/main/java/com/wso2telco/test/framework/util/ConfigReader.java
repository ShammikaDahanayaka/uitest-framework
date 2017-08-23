package com.wso2telco.test.framework.util;

import com.wso2telco.test.framework.configuration.Configuration;
import com.wso2telco.test.framework.configuration.CoreConfigurations;

public class ConfigReader {
	protected static Configuration config = CoreConfigurations.getCoreConfig();
	
	public static String getAdminUser() {
		return config.getValue("ADMIN_user");
		
	}
	public static String getAdminPwd() {
		return config.getValue("ADMIN_pwd");
		
	}
	public static String getUser(String userId) {
		return config.getValue(userId.trim()+"_user");
		
	}
	public static String getPwd(String userId) {
		return config.getValue(userId+"_pwd");
		
	}
}
