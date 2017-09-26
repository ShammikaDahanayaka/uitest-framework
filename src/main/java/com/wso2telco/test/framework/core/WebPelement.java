package com.wso2telco.test.framework.core;

import java.util.List;

import org.openqa.selenium.WebElement;

public interface WebPelement extends WebElement {
	
	public void initialize()throws Exception;
	
	public boolean  isAvailable();
	public void  sendEnter();
	public void  sendKeys(CharSequence... keysToSend);
	public void  clearAndSendkeys(CharSequence... keysToSend);

	public List<WebElement> initializeList() throws Exception;
	
}
