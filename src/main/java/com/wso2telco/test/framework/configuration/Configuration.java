package com.wso2telco.test.framework.configuration;

public interface Configuration {

	
	/**
	 * Jan 14, 2016
	 * @author Ganegoda,Mahesh 
	 * @return void
	 */
	public String getValue(String key);

	
	/**
	 * Jan 14, 2016
	 * @author Ganegoda,Mahesh 
	 * @return void
	 */
	public void setValue(String key,String value);

}
