package com.wso2telco.test.framework.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.wso2telco.test.framework.core.FObject;
import com.wso2telco.test.framework.element.DefaultElement;
import com.wso2telco.test.framework.util.UIType;

public class PrePageBase extends FObject implements PageActions {

	private WebElement element;
	private WebPelement pelement;
	
	public PrePageBase(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	@Override
	public WebElement getElement(UIType uiType, String value) throws Exception {
		// TODO Auto-generated method stub
		//WebElement element = null;
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return driver.getTitle();		
	}

	@Override
	public void selectItem(UIType uiType, String value, String itemName) throws Exception {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		ArrayList<String> actFirstSecQstList = new ArrayList<String>();
		//WebElement select = getElementByXpath(xpath, driver);
		WebElement select = getElement(uiType,xpath);
		List<WebElement> options = select
				.findElements(By.tagName("option"));
		for (WebElement option : options) {
			actFirstSecQstList.add(option.getText());
			}
		return actFirstSecQstList;
	}

	@Override
	public boolean hasElement(By by) {
		// TODO Auto-generated method stub
		 return !driver.findElements(by).isEmpty();
	}
	@Override
	public WebDriver getDriver() {
		// TODO Auto-generated method stub
		return driver;
	}
	
	@Override
	public WebPelement getElement(WebPelement pelement) {
		// TODO Auto-generated method stub
		try {
			pelement.initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pelement;
	}
	@Override
	public WebPelement defineEelement(UIType uiType, String value) {
		// TODO Auto-generated method stub
		pelement = new DefaultElement(uiType, value);
		return pelement;
	}
	@Override
	public void selectItem(WebPelement pelement, String tagName,
			String textToContain) throws Exception {
		// TODO Auto-generated method stub
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
	/*@Override
	public WebPelement locate(WebPelement telement) {
		// TODO Auto-generated method stub
		return null;
	}*/

}
