package com.wso2telco.test.framework.drivers;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.wso2telco.test.framework.configuration.ConfigurationKeys;
import com.wso2telco.test.framework.core.BaseObject;
import com.wso2telco.test.framework.util.Browser;
import com.wso2telco.test.framework.util.Timeout;

public class CreateWebDriver extends BaseObject implements WebDriver,TakesScreenshot{

	protected WebDriver driver;
	private static Logger logger = Logger.getLogger(CreateWebDriver.class);

	public CreateWebDriver() {
		SetupDriver setupDriver = new SetupDriver(config);
		driver = setupDriver.launchWebDriver();
		setDriverTimeout();
	}

	public CreateWebDriver(Browser browser) {
		SetupDriver setupDriver = new SetupDriver(config);
		driver = setupDriver.launchWebDriver(browser);
		setDriverTimeout();
	}

	public CreateWebDriver(FirefoxProfile firefoxProfile) {
		SetupDriver setupDriver = new SetupDriver(config);
		setupDriver.setFirefoxProfile(firefoxProfile);
		driver = setupDriver.launchWebDriver();
		setDriverTimeout();
	}

	public CreateWebDriver(DesiredCapabilities desiredCapabilities) {
		SetupDriver setupDriver = new SetupDriver(config);
		setupDriver.setDesiredCapabilities(desiredCapabilities);
		driver = setupDriver.launchWebDriver();
		setDriverTimeout();
	}

	public CreateWebDriver(WebDriver webDriver) {
		driver = webDriver;
	}

	private void setDriverTimeout() {
		String waitTime = config.getValue(String
				.valueOf(ConfigurationKeys.IMPLICIT_WAIT));
		if(waitTime=="")
			waitTime=String
					.valueOf(Timeout.IMPLICIT_WAIT.getValue());
		driver.manage().timeouts()
				.implicitlyWait(Long.parseLong(waitTime), TimeUnit.SECONDS);
	}

	@Override
	public void get(String url) {
		logger.info("Webdriver load with url: " + url);
		driver.get(url);
	}

	@Override
	public String getCurrentUrl() {
		logger.info("Webdriver get current url");
		return driver.getCurrentUrl();
	}

	@Override
	public String getTitle() {
		logger.info("Webdriver get title");
		return driver.getTitle();
	}

	@Override
	public List<WebElement> findElements(By by) {

		return driver.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		// TODO Auto-generated method stub
		return driver.findElement(by);
	}

	@Override
	public String getPageSource() {
		// TODO Auto-generated method stub
		return driver.getPageSource();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		driver.close();
	}

	@Override
	public void quit() {
		// TODO Auto-generated method stub
		driver.quit();
	}

	@Override
	public Set<String> getWindowHandles() {
		// TODO Auto-generated method stub
		return driver.getWindowHandles();
	}

	@Override
	public String getWindowHandle() {
		// TODO Auto-generated method stub
		return driver.getWindowHandle();
	}

	@Override
	public TargetLocator switchTo() {
		// TODO Auto-generated method stub
		return driver.switchTo();
	}

	@Override
	public Navigation navigate() {
		// TODO Auto-generated method stub
		return driver.navigate();
	}

	@Override
	public Options manage() {
		// TODO Auto-generated method stub
		return driver.manage();
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target)
			throws WebDriverException {
		// TODO Auto-generated method stub
		return null;
	}

}
