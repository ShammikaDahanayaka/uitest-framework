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

	
	public void launchBrowser(){
		driver =  new CreateWebDriver();
	}
	public void launchBrowser(Browser browser){
		driver =  new CreateWebDriver(browser);
	}
	public void launchBrowser(FirefoxProfile firefoxProfile){
		driver =  new CreateWebDriver(firefoxProfile);
	}
	public void launchBrowser(DesiredCapabilities desiredCapabilities){
		driver =  new CreateWebDriver(desiredCapabilities);
	}

}
