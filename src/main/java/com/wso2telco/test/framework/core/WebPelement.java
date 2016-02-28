package com.wso2telco.test.framework.core;

import org.openqa.selenium.WebElement;

public interface WebPelement extends WebElement {
	
	public void initialize()throws Exception;
	
	public void  sendEnter();
	public void  clearAndSendkeys(CharSequence... keysToSend);
}