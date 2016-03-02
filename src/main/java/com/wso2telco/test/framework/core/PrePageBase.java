package com.wso2telco.test.framework.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.wso2telco.test.framework.core.FObject;
import com.wso2telco.test.framework.element.CoreElement;
import com.wso2telco.test.framework.util.UIType;

public class PrePageBase extends FObject implements PageActions {

	private WebElement element;
	private WebPelement pelement;
	
	public PrePageBase(WebDriver driver) {
		super(driver);
		
	}
	public PrePageBase() {
		
	}
	@Override
	public WebElement getElement(UIType uiType, String value) throws Exception {
		
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			List<WebElement> found = driver.findElements(getBy(uiType,value));
			if (found.size() > 0) {
				element = found.get(0);
			}
		} catch (Exception e) {
			logInstruction("Cannot Get Element by "+uiType+"-'getElement()" + value
					+ ":" + e.getMessage());
			throw new Exception("Cannot Get Element by "+uiType+"-'getElement()'"
					+ e.getMessage());
		}
		return element;
	}
	
	

	@Override
	public By getBy(UIType uiType, String value) {
		
		switch (uiType) {
		case ID:
			return By.id(value);
		case Name:
			return By.name(value);
		case ClassName:
			return By.className(value);
		case LinkText:
			return By.linkText(value);
		case PartialLinkText:
			return By.partialLinkText(value);
		case TagName:
			return By.tagName(value);
		case Css:
			return By.cssSelector(value);
		default:
			return By.xpath(value);
		}
	}

	@Override
	public String compareTitle(String expectedValue) {
		
		return driver.getTitle();		
	}

	@Override
	public void selectItem(UIType uiType, String value, String itemName) throws Exception {
		
		try {
			WebElement select = getElement(uiType,value);
			List<WebElement> options = select
					.findElements(By.tagName("option"));
			for (WebElement option : options) {
				if (itemName.equals(option.getText())) {
					option.click();
				}
			}
		} 
		catch (Exception e) {
			logInstruction("Exception selecting item from dropdown 'selectItem'()"+value+":"+ e.getMessage());
			throw new Exception("Exception selecting item from dropdown  'selectItem'()'"
					+ e.getMessage());
		}

	}

	@Override
	public ArrayList<String> verifyListContent(UIType uiType, String xpath) throws Exception {
		
		ArrayList<String> actFirstSecQstList = new ArrayList<String>();
		//WebElement select = getElementByXpath(xpath, driver);
		WebElement select = getElement(uiType,xpath);
		List<WebElement> options = select.findElements(By.tagName("option"));
		for (WebElement option : options) {
			actFirstSecQstList.add(option.getText());
			}
		return actFirstSecQstList;
	}
	
	public ArrayList<String> verifyContent(UIType uiType, String xpath) throws Exception{
		ArrayList<String> actFirstSecQstList = new ArrayList<String>();
		WebElement select = getElement(uiType,xpath);
		List<WebElement> options = select.findElements(By.xpath(xpath));
		for (WebElement option : options) {
			actFirstSecQstList.add(option.getText());
			}
		return actFirstSecQstList;
		// need to verify this method
	}

	@Override
	public boolean hasElement(By by) {
		
		 return !driver.findElements(by).isEmpty();
	}
	@Override
	public WebDriver getDriver() {
		
		return driver;
	}
	
	@Override
	public WebPelement getElement(WebPelement pelement) {
		
		try {
			pelement.initialize();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return pelement;
	}
	@Override
	public WebPelement defineEelement(UIType uiType, String value) {
		
		pelement = new CoreElement(uiType, value);
		return pelement;
	}
	
	
	
	@Override
	public void selectItem(WebPelement pelement, String tagName,
			String textToContain) throws Exception {
		
		try {
			WebElement select = getElement(pelement);
			List<WebElement> options = select
					.findElements(By.tagName(tagName));
			for (WebElement option : options) {
				if (textToContain.equals(option.getText())) {
					option.click();
				}
			}
		} 
		catch (Exception e) {
			logInstruction("Exception selecting item from dropdown 'selectItem'()"+textToContain+":"+ e.getMessage());
			throw new Exception("Exception selecting item from dropdown  'selectItem'()'"
					+ e.getMessage());
		}
		
	}
	
	@Override
	public WebPelement defineEelement(UIType uiType, String value,
			String description) {
		pelement = new CoreElement(uiType, value,description);
		return pelement;
	}

}
