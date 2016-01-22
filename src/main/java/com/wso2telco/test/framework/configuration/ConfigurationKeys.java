package com.wso2telco.test.framework.configuration;

public enum ConfigurationKeys {
	BROWSER("browser"), IMPLICIT_WAIT("implicit_wait"), CHROME_DRIVER_PATH(
			"chrome.driver.path"), IE_DRIVER_PATH(
			"ie.driver.path");

	private String configKeys;

	ConfigurationKeys(String key) {
		this.configKeys = key;
		;
	}

	public String getCongfigKey() {
		return configKeys;
	}

}
