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
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.wso2telco.test.framework.core.WebPelement;
import com.wso2telco.test.framework.util.UIType;

public class CoreElement extends BasicElement implements WebPelement{
	
	Logger logger = Logger.getLogger(CoreElement.class);
	
	private WebElement element;
	private List<WebElement> elementList;
	protected boolean isAvaialble;
	
    

	public CoreElement(UIType uiType, String value) {
		super(uiType, value);
		
	}
    
    public CoreElement(UIType uiType, String value,String description) {
		super(uiType, value,description);
		
	}

	
//    public void initialize() throws Exception {
//		
//		try {
//			
//			logger.debug("Locating element " + getUiType() + ":" + getUiValue());
//			//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//			List<WebElement> found = driver.findElements(getBy(uiType,uiValue));
//			if (found.size() > 0) {
//				logger.debug("Found element " + getUiType() + ":" + getUiValue());
//				element = found.get(0);
//				setAvaialble(true);
//				if(found.size() > 1)
//					logger.debug("Found more than one element " + getUiType() + ":" + getUiValue());
//			}
//		} catch (Exception e) {
//			setAvaialble(false);
//			logInstruction("Cannot Get Element by "+uiType+"-'getElement()" + uiValue
//					+ ":" + e.getMessage());
//			throw new Exception("Cannot Get Element by "+uiType+"-'getElement()'"
//					+ e.getMessage());
//		}
//		
//	}
    
    public void initialize() throws Exception{
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	FluentWait<WebDriver> wait = new FluentWait(driver)
    			 
    		    .withTimeout(100, TimeUnit.SECONDS)
    		 
    		    .pollingEvery(10, TimeUnit.SECONDS)
    		 
    		    .ignoring(NoSuchElementException.class);
    	
    	//Function<WebDriver, WebElement> function = ;

		try{
			this.element = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver arg0) {
					int i = 0;
					logger.debug("Locating element " + getUiType() + ":" + getUiValue());
					WebElement element = arg0.findElement(getBy(uiType,uiValue));
					System.out.println("Checking for the object!! "+ i++);
					if (element != null) {
						setAvaialble(true);
						logger.debug("Found element " + getUiType() + ":" + getUiValue());
					}
					return element;
				}
			});
		}catch(Exception e){
			setAvaialble(false);
			logInstruction("Cannot Get Element by "+uiType+"-'getElement()" + uiValue
					+ ":" + e.getMessage());
			throw new Exception("Cannot Get Element by "+uiType+"-'getElement()'"
					+ e.getMessage());
		}
		
		
	}
    
    public List<WebElement> initializeList() throws Exception{
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	FluentWait<WebDriver> wait = new FluentWait(driver)
    			 
    		    .withTimeout(100, TimeUnit.SECONDS)
    		 
    		    .pollingEvery(10, TimeUnit.SECONDS)
    		 
    		    .ignoring(NoSuchElementException.class);
    	
    	//Function<WebDriver, WebElement> function = ;

		try{
			this.elementList = (List<WebElement>) wait.until(new Function<WebDriver, List<WebElement>>() {
				public List<WebElement> apply(WebDriver arg0) {
					int i = 0;
					logger.debug("Locating element list " + getUiType() + ":" + getUiValue());
					List<WebElement> element = arg0.findElements(getBy(uiType,uiValue));
					System.out.println("Checking for the object LIST!! "+ i++);
					if (element != null) {
						setAvaialble(true);
						logger.debug("Found element list " + getUiType() + ":" + getUiValue());
					}
					return element;
				}
			});
		}catch(Exception e){
			setAvaialble(false);
			logInstruction("Cannot Get Element by "+uiType+"-'getElement()" + uiValue
					+ ":" + e.getMessage());
			throw new Exception("Cannot Get Element by "+uiType+"-'getElement()'"
					+ e.getMessage());
		}
		return elementList;
		
		
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
		for(int i = 0; i < 5; i++){
			try {
				logger.debug("click on element " + getDescription());
				element.click();
				break;
			} catch (WebDriverException e) {
				logger.debug("WebDriver Exception occured when clicking on the element "+element.getText() + " "+ e.getMessage());
				
			}
		}
	}

	@Override
	public void submit() {
		
		try {
			element.submit();
		} catch (WebDriverException e) {
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
		
		return element.getTagName();
	}

	@Override
	public String getAttribute(String name) {
		
		return element.getAttribute(name);
	}

	@Override
	public boolean isSelected() {
		
		return element.isSelected();
	}

	@Override
	public boolean isEnabled() {
		
		return element.isEnabled();
	}

	@Override
	public String getText() {
		boolean flag = false;
		String elementContent = "No such Element";
		int i = 0;
		while(!flag && i<10){
			logger.debug("Element is "+ element.getText() + "   "+ i + "  "+element.getTagName());
			if(element.getText() != null){
				flag = true;
				elementContent = element.getText();
			}
			i++;
		}
		return elementContent;
	}

	@Override
	public List<WebElement> findElements(By by) {
		
		return element.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		
		return element.findElement(by);
	}
	
	@Override
	public boolean isDisplayed() {
		
		return element.isDisplayed();
	}

	@Override
	public Point getLocation() {
		
		return element.getLocation();
	}

	@Override
	public Dimension getSize() {
		
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
		
		
		return element.getRect();
		
	}

	



	private void setAvaialble(boolean isAvaialble) {
		this.isAvaialble = isAvaialble;
	}

	@Override
	public boolean isAvailable() {
		
		return isAvaialble;
	}
	

}
