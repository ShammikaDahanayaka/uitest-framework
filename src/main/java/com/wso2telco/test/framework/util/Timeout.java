package com.wso2telco.test.framework.util;

public enum Timeout {
	IMPLICIT_WAIT(1000);
	
	
	 long value;

	    private Timeout(long timeout) {
	        this.value = timeout;
	    }

	    public long getValue() {
	        return this.value;
	    }

}
