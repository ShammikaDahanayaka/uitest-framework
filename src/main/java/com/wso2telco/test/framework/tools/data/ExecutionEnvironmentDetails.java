package com.wso2telco.test.framework.tools.data;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ExecutionEnvironmentDetails {
	Capabilities caps;

	public String getBrouserName(WebDriver driver) {
		caps = ((RemoteWebDriver) driver).getCapabilities();
		return caps.getBrowserName();
	}

	public String getBrouserVersion(WebDriver driver) {
		caps = ((RemoteWebDriver) driver).getCapabilities();
		return caps.getVersion();
	}

	public String getOperetingSystem(WebDriver driver) {
		return System.getProperty("os.name").toLowerCase();
	}
	
	public String getPlatform(WebDriver driver) {
		caps = ((RemoteWebDriver) driver).getCapabilities();
		return caps.getPlatform().toString();
	}

}
