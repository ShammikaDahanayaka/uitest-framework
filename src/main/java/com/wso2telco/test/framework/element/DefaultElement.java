package com.wso2telco.test.framework.element;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.wso2telco.test.framework.core.WebPelement;
import com.wso2telco.test.framework.util.UIType;

public class DefaultElement extends BasicElement implements WebPelement{
	private WebElement element;
	
    public DefaultElement(UIType uiType, String value) {
		super(uiType, value);
		
	}

	
    public void initialize() throws Exception {
		
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			List<WebElement> found = driver.findElements(getBy(uiType,uiValue));
			if (found.size() > 0) {
				element = found.get(0);
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
		// TODO Auto-generated method stub
		try {
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
			element.sendKeys(keysToSend);
		} catch (WebDriverException e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		
	}

	@Override
	public void clear() {
		
		try {
			element.clear();
		} catch (WebDriverException e) {
			// TODO: handle exception
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
		// TODO Auto-generated method stub
		return element.getCssValue(propertyName);
	}


	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return null;
	}


	

	

}
