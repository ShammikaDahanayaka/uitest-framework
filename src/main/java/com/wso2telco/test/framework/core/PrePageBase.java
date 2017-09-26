package com.wso2telco.test.framework.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;
import com.wso2telco.test.framework.element.CoreElement;
import com.wso2telco.test.framework.element.table.Table;
import com.wso2telco.test.framework.util.UIType;

public class PrePageBase extends FObject implements PageActions {

	private WebElement element;
	private WebPelement pelement;
	private List<WebElement> elementList;
	
	public PrePageBase(WebDriver driver) {
		super(driver);
		
	}
	public PrePageBase() {
		
	}
	
	Wait wait = new FluentWait(driver)
			 
		    .withTimeout(30, TimeUnit.SECONDS)
		 
		    .pollingEvery(5, TimeUnit.SECONDS)
		    
		    .ignoring(NoSuchElementException.class);
	
	@Override
	public WebElement getElement(UIType uiType, String value) throws Exception {
		
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Function<WebDriver, List<WebElement>> function = new Function<WebDriver, List<WebElement>>() {
				public List<WebElement> apply(WebDriver driver, UIType uiType, String value) {
					List<WebElement> element = driver.findElements(getBy(uiType,value));
					if (element != null) {
						logInstruction("Element is empty");
					}
					return element;
				}

				@Override
				public List<WebElement> apply(WebDriver arg0) {
					// TODO Auto-generated method stub
					return null;
				}
			};
			List<WebElement> found = (List<WebElement>) wait.until(function);		//driver.findElements(getBy(uiType,value));
			if (found.size() > 0) {
				element = found.get(0);
			}
		} catch (Exception e) {
			logInstruction("Cannot Get Element by "+uiType+"-'getElement()" + value
					+ ":" + e.getMessage());
			throw new Exception("Cannot Get Element by "+uiType+"-'getElemnt()'"
					+ e.getMessage());
		}
		return element;
	}
	
	public List<WebElement> apply(WebDriver driver,UIType uiType, String value){
		return driver.findElements(getBy(uiType,value));
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
			logger.debug("Call WebPelement "+ pelement.getText());
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return pelement;
	}
	
	public List<WebElement> getElementList(WebPelement pelement) {
		
		try {
			elementList = pelement.initializeList();
			logger.debug("Call WebPelement List ");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return elementList;
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
			select.click();
			List<WebElement> options = select
					.findElements(By.tagName(tagName));
			for (WebElement option : options) {
				if (textToContain.equals(option.getText().trim())) {
					option.click();
					break;
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
	@Override
	public Table getTable(UIType uiType, String value) {
		// TODO Auto-generated method stub
		Table table =null;
		try {
			WebElement ele=getElement(uiType, value);
			table = new Table(ele);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return table;
		
	}

}
