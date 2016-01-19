package com.wso2telco.test.framework.pageobjects;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.wso2telco.test.framework.core.PageActions;
import com.wso2telco.test.framework.core.PrePageBase;
import com.wso2telco.test.framework.core.WebPelement;
import com.wso2telco.test.framework.util.UIType;

public class PageBase implements PageActions {

	private PageActions pageActions;
	public WebDriver driver;
	public static Properties OR;
	public static Logger APP_LOGS = null;
	public boolean flag;

	public PageBase(WebDriver driver) {
		pageActions = new PrePageBase(driver);
		this.driver = pageActions.getDriver();
	}

	public void initialize() throws IOException {
		OR = new Properties();
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\test\\resources\\config\\OR.properties");
		OR.load(ip);
		System.out.println(OR.getProperty("link"));
	}

	@Override
	public WebDriver getDriver() {
		// TODO Auto-generated method stub
		return pageActions.getDriver();
	}

	@Override
	public WebElement getElement(UIType uiType, String value) throws Exception {
		// TODO Auto-generated method stub
		return pageActions.getElement(uiType, value);
	}

	@Override
	public By getBy(UIType uiType, String value) {
		// TODO Auto-generated method stub
		return pageActions.getBy(uiType, value);
	}

	@Override
	public String compareTitle(String expectedValue) {
		// TODO Auto-generated method stub
		return pageActions.compareTitle(expectedValue);
	}

	@Override
	public void selectItem(UIType uiType, String value, String itemName)
			throws Exception {
		// TODO Auto-generated method stub
		pageActions.selectItem(uiType, value, itemName);
	}

	@Override
	public ArrayList<String> verifyListContent(UIType uiType, String xpath)
			throws Exception {
		// TODO Auto-generated method stub
		return pageActions.verifyListContent(uiType, xpath);
	}

	@Override
	public boolean hasElement(By by) {
		// TODO Auto-generated method stub
		return pageActions.hasElement(by);
	}

	@Override
	public void logInstruction(String logMessage) {
		// TODO Auto-generated method stub
		pageActions.logInstruction(logMessage);
	}

	/*@Override
	public WebPelement locate(WebPelement webPelement) {
		// TODO Auto-generated method stub
		
		return pageActions.locate(webPelement);
	}*/

	@Override
	public WebPelement defineEelement(UIType uiType, String value) {
		// TODO Auto-generated method stub
		return pageActions.defineEelement(uiType, value);
	}

	@Override
	public void selectItem(WebPelement rootelement, String tagName,
			String textToContain) throws Exception {
		// TODO Auto-generated method stub
		pageActions.selectItem(rootelement, tagName, textToContain);
	}

	@Override
	public WebElement getElement(WebPelement telement) {
		// TODO Auto-generated method stub
		return pageActions.getElement(telement);
	}

}
