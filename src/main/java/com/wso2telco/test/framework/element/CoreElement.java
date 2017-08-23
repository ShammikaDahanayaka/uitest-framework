package com.wso2telco.test.framework.element;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.wso2telco.test.framework.core.WebPelement;
import com.wso2telco.test.framework.util.UIType;
import com.google.common.base.Function; 
import org.openqa.selenium.TimeoutException; 

public class CoreElement extends BasicElement implements WebPelement{
	
	Logger logger = Logger.getLogger(CoreElement.class);
	
	private WebElement element;
	protected boolean isAvaialble;
	
    

	public CoreElement(UIType uiType, String value) {
		super(uiType, value);
		
	}
    
    public CoreElement(UIType uiType, String value,String description) {
		super(uiType, value,description);
		
	}

	
    public void initialize() throws Exception {
		
		try {
			
			logger.debug("Locating element " + getUiType() + ":" + getUiValue());
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			final WebElement  found =waitForElementToAppear(getBy(uiType,uiValue));
		/*	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			List<WebElement> found = driver.findElements(getBy(uiType,uiValue));*/
			if (found!=null) {
				logger.debug("Found element " + getUiType() + ":" + getUiValue());
				element = found;
				setAvaialble(true);
					logger.debug("Found more than one element " + getUiType() + ":" + getUiValue());
			}
		} catch (Exception e) {
			setAvaialble(false);
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

	



	private void setAvaialble(boolean isAvaialble) {
		this.isAvaialble = isAvaialble;
	}

	@Override
	public boolean isAvailable() {
		// TODO Auto-generated method stub
		return isAvaialble;
	}

	@Override
	/**
	 * check whether the element exists or not
	 */
	public boolean isExists() {
		WebElement webElement =	waitForElementToAppear(getBy(uiType,uiValue));
		return webElement!=null?true:false;
	}
	

	
	/**
	 * this will wait fluently until rendering the element .
	 * @param locator
	 * @return
	 */
	protected WebElement waitForElementToAppear(final By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
									.withTimeout(30, TimeUnit.SECONDS)
									.pollingEvery(5, TimeUnit.SECONDS)
									.ignoring(NoSuchElementException.class);

		WebElement element = null;
		try {
			element = wait.until(new Function<WebDriver, WebElement>() {

				@Override
				public WebElement apply(WebDriver driver) {
					return driver.findElement(locator);
				}
			});
		} catch (TimeoutException e) {
			try {
				// I want the error message on what element was not found
				driver.findElement(locator);
			} catch (NoSuchElementException renamedErrorOutput) {
				// print that error message
				renamedErrorOutput.addSuppressed(e);
				// throw new
				// NoSuchElementException("Timeout reached when waiting for element to be
				// found!"
				// + e.getMessage(), correctErrorOutput);
				throw renamedErrorOutput;
			}
			e.addSuppressed(e);
			throw new NoSuchElementException("Timeout reached when searching for element!", e);
		}

		return element;
	}
}
