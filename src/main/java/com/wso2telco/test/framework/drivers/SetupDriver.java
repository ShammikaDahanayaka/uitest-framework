package com.wso2telco.test.framework.drivers;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.wso2telco.test.framework.configuration.Configuration;
import com.wso2telco.test.framework.configuration.ConfigurationKeys;
import com.wso2telco.test.framework.util.Browser;

public class SetupDriver {
	private static Logger logger = Logger.getLogger(SetupDriver.class);
	private Configuration conf = null;
	private FirefoxProfile firefoxProfile = null;
	private DesiredCapabilities desiredCapabilities = null;

	

	public SetupDriver(Configuration conf) {
		this.conf = conf;
	}

	public WebDriver launchWebDriver() {
		String browserFromConfig = conf.getValue(ConfigurationKeys.BROWSER.getCongfigKey());
		
		Browser browser;
		try {
			browser = Browser.valueOf(browserFromConfig.toUpperCase());
			logger.debug("browser wll be "+browser.toString());
		} catch (Exception e) {
			logger.warn("Failed to parse browser type,browser will be firefox");
			browser = Browser.FIREFOX;
		}

		return getWebDriver(browser);
	}

	public WebDriver launchWebDriver(Browser browser) {
		return getWebDriver(browser);
	}

	private WebDriver getWebDriver(Browser browser) {
		switch (browser) {
		case FIREFOX:
			if (firefoxProfile == null && desiredCapabilities == null) {
				return new FirefoxDriver();
			} else if (firefoxProfile != null && desiredCapabilities == null) {
				return new FirefoxDriver(firefoxProfile);
			} else if (firefoxProfile == null && desiredCapabilities != null) {
				return new FirefoxDriver(desiredCapabilities);
			}

		case CHROME:
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+conf.getValue(ConfigurationKeys.CHROME_DRIVER_PATH.getCongfigKey()));
			 if (desiredCapabilities != null) {
				 return new ChromeDriver(desiredCapabilities);
			}else{
				return new ChromeDriver();
			}

		case INTERNETEXPLORER:
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+conf.getValue(ConfigurationKeys.IE_DRIVER_PATH.getCongfigKey()));
			if (desiredCapabilities != null) {
				return new InternetExplorerDriver(desiredCapabilities);
			}else{
				return new InternetExplorerDriver();
			}
		default:
			return new FirefoxDriver();
		}
	}
	public FirefoxProfile getFirefoxProfile() {
		return firefoxProfile;
	}

	public void setFirefoxProfile(FirefoxProfile firefoxProfile) {
		this.firefoxProfile = firefoxProfile;
	}

	public DesiredCapabilities getDesiredCapabilities() {
		return desiredCapabilities;
	}

	public void setDesiredCapabilities(DesiredCapabilities desiredCapabilities) {
		this.desiredCapabilities = desiredCapabilities;
	}

}
