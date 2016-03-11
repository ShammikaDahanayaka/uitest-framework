package com.wso2telco.test.framework.element;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.wso2telco.test.framework.core.WebPelement;
import com.wso2telco.test.framework.util.UIType;

public class CoreElement extends BasicElement implements WebPelement{
	
	Logger logger = Logger.getLogger(CoreElement.class);
	
	private WebElement element;
	
    public CoreElement(UIType uiType, String value) {
		super(uiType, value);
		
	}
    
    public CoreElement(UIType uiType, String value,String description) {
		super(uiType, value,description);
		
	}

	
    public void initialize() throws Exception {
		
		try {
			
			logger.debug("Locating element " + getUiType() + ":" + getUiValue());
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			List<WebElement> found = driver.findElements(getBy(uiType,uiValue));
			if (found.size() > 0) {
				logger.debug("Found element " + getUiType() + ":" + getUiValue());
				element = found.get(0);
				if(found.size() > 1)
					logger.debug("Found more than one element " + getUiType() + ":" + getUiValue());
			}
		} catch (Exception e) {
			logInstruction("Cannot Get Element by "+uiType+"-'getElement()" + uiValue
					+ ":" + e.getMessage());
			throw new Exception("Cannot Get Element by "+uiType+"-'getElement()'"
					+ e.getMessage());
		}
		
	}
    
    private By getBy(UIType uiType, String value) {
		
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
	public <X> X getScreenshotAs(OutputType<X> target)
			throws WebDriverException {
		
		return element.getScreenshotAs(target);
	}

	@Override
	public void click() {
		try {
			logger.debug("click on element " + getDescription());
			element.click();
		} catch (WebDriverException e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		
	}

	@Override
	public void submit() {
		
		try {
			element.submit();
		} catch (WebDriverException e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {
		
		try {
			logger.debug("sending keys " +Arrays.toString(keysToSend));
			element.sendKeys(keysToSend);
		} catch (WebDriverException e) {
			
			e.printStackTrace();
			
		}
		
	}

	@Override
	public void clear() {
		
		try {
			logger.debug("clear text");
			element.clear();
		} catch (WebDriverException e) {
			
			e.printStackTrace();
			
		}
		
	}

	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return element.getTagName();
	}

	@Override
	public String getAttribute(String name) {
		// TODO Auto-generated method stub
		return element.getAttribute(name);
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return element.isSelected();
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return element.isEnabled();
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return element.getText();
	}

	@Override
	public List<WebElement> findElements(By by) {
		// TODO Auto-generated method stub
		return element.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		// TODO Auto-generated method stub
		return element.findElement(by);
	}
	
	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return element.isDisplayed();
	}

	@Override
	public Point getLocation() {
		// TODO Auto-generated method stub
		return element.getLocation();
	}

	@Override
	public Dimension getSize() {
		// TODO Auto-generated method stub
		return element.getSize();
	}

	@Override
	public String getCssValue(String propertyName) {
		logger.debug("get css value for property "+propertyName);
		return element.getCssValue(propertyName);
	}


	@Override
	public void sendEnter() {
		try {
			logger.debug("send enter keys");
			element.sendKeys(Keys.ENTER);
		} catch (WebDriverException e) {
			logger.debug(e);
		}
		
	}


	@Override
	public void clearAndSendkeys(CharSequence... keysToSend) {
		try {
			logger.debug("clear and send keys " + keysToSend);
			element.clear();
			sendKeys(keysToSend);
		} catch (WebDriverException e) {
			logger.debug(e);
		}
		
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		
		return element.getRect();
		
	}


	

	

}
