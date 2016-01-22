package com.wso2telco.test.framework.util;

public enum Default {
	CONFIG_PATH("/config/config.cfg"),
	DRIVER_BROWSER("firefox");
	
	
	private String value;

    private Default(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
