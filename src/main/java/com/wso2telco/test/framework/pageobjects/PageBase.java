package com.wso2telco.test.framework.pageobjects;

import com.wso2telco.test.framework.core.WebPelement;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.wso2telco.test.framework.core.PageActions;
import com.wso2telco.test.framework.core.PrePageBase;

public class PageBase extends PrePageBase implements PageActions {

	
	Logger logger = Logger.getLogger(PageBase.class);
	
	public boolean flag;

	public PageBase(WebDriver driver) {
		super(driver);
		//pageActions = new PrePageBase(driver);

	}

   public void switchtoFrame(String frameName){
	   logger.debug("moving to default content");
		driver.switchTo().defaultContent();
	   logger.info("moving to frame " + frameName);
	   driver.switchTo().frame(frameName);
   }


	public void switchtoDefaultContent(){

		logger.debug("moving to default content");
		driver.switchTo().defaultContent();
	}

}
