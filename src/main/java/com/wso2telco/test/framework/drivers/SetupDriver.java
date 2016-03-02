package com.wso2telco.test.framework.drivers;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
		String downloadPath = conf.getValue("downloadPath");
		switch (browser) {
		case FIREFOX:
			if (firefoxProfile == null && desiredCapabilities == null) {
				FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference("browser.download.folderList",2);
				profile.setPreference("browser.download.manager.showWhenStarting",false);
				profile.setPreference("browser.download.dir",downloadPath);
				profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/xls,text/csv,application/vnd.ms-excel");
				return new FirefoxDriver(profile);
			} else if (firefoxProfile != null && desiredCapabilities == null) {
				return new FirefoxDriver(firefoxProfile);
			} else if (firefoxProfile == null && desiredCapabilities != null) {
				return new FirefoxDriver(desiredCapabilities);
			}

		case CHROME:
			String chromeDriverPath = conf.getValue(String.valueOf(ConfigurationKeys.CHROME_DRIVER_PATH));
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			desiredCapabilities = DesiredCapabilities.chrome();
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("test-type");
			desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			return new ChromeDriver(desiredCapabilities);
		case INTERNETEXPLORER:
			String ieDriverPath = conf.getValue(String.valueOf(ConfigurationKeys.IE_DRIVER_PATH));
			System.setProperty("webdriver.ie.driver", ieDriverPath);
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, false);
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			capabilities.setCapability("ignoreZoomSetting", true);
			capabilities.setCapability("ie.ensureCleanSession", true);
			//capabilities.setCapability("nativeEvents",false);
			capabilities.setCapability("requireWindowFocus",true);
			capabilities.setCapability("IntroduceInstabilityByIgnoringProtectedModeSettings",true);
			return new InternetExplorerDriver(capabilities);
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
