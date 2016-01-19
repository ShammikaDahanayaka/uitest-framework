package com.wso2telco.test.framework.core;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.wso2telco.test.framework.util.UIType;

public interface PageActions {

	/**
	 * Jan 8, 2016
	 * 
	 * @author Ganegoda,Mahesh
	 * @return WebDriver
	 */
	public WebDriver getDriver();
	
	
	
	public WebPelement defineEelement(UIType uiType,String value);
	//public WebPelement locate(WebPelement telement);
	public void selectItem(WebPelement pelement, String tagName, String textToContain)
			throws Exception;
	public WebElement getElement(WebPelement telement);

	/**
	 * Jan 8, 2016
	 * 
	 * @author Ganegoda,Mahesh
	 * @param uiType
	 * @param value
	 * @return
	 * @throws Exception
	 *             WebElement
	 */
	public WebElement getElement(UIType uiType, String value) throws Exception;
	

	/**
	 * Jan 8, 2016
	 * 
	 * @author Ganegoda,Mahesh
	 * @param uiType
	 * @param value
	 * @return By
	 */
	public By getBy(UIType uiType, String value);

	/**
	 * Jan 8, 2016
	 * 
	 * @author Ganegoda,Mahesh
	 * @param expectedValue
	 * @return String
	 */
	public String compareTitle(String expectedValue);

	/**
	 * Jan 8, 2016
	 * 
	 * @author Ganegoda,Mahesh
	 * @param uiType
	 * @param value
	 * @param itemName
	 * @throws Exception
	 *             void
	 */
	public void selectItem(UIType uiType, String value, String itemName)
			throws Exception;

	/**
	 * Jan 8, 2016
	 * 
	 * @author Ganegoda,Mahesh
	 * @param uiType
	 * @param xpath
	 * @return
	 * @throws Exception
	 *             ArrayList<String>
	 */
	public ArrayList<String> verifyListContent(UIType uiType, String xpath)
			throws Exception;

	/**
	 * Jan 8, 2016
	 * 
	 * @author Ganegoda,Mahesh
	 * @param by
	 * @return boolean
	 */
	public boolean hasElement(By by);

	/**
	 * Jan 8, 2016
	 * 
	 * @author Ganegoda,Mahesh
	 * @param logMessage
	 *            void
	 */
	public void logInstruction(String logMessage);

}
