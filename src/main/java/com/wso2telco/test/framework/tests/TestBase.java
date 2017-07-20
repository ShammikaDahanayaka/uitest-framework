package com.wso2telco.test.framework.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.wso2telco.test.framework.core.FObject;
import com.wso2telco.test.framework.drivers.CreateWebDriver;
import com.wso2telco.test.framework.util.Browser;

public class TestBase extends FObject {

	public TestBase(){
	}
	public TestBase(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	public WebDriver launchBrowser(){
		driver =  new CreateWebDriver();
		return driver;
	}
	public WebDriver launchBrowser(Browser browser){
		driver =  new CreateWebDriver(browser);
		return driver;
	}
	public WebDriver launchBrowser(FirefoxProfile firefoxProfile){
		driver =  new CreateWebDriver(firefoxProfile);
		return driver;
	}
	public WebDriver launchBrowser(DesiredCapabilities desiredCapabilities){
		driver =  new CreateWebDriver(desiredCapabilities);
		return driver;
	}

}
