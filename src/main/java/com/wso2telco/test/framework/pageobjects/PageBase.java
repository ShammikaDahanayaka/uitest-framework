package com.wso2telco.test.framework.pageobjects;

import org.openqa.selenium.WebDriver;
import com.wso2telco.test.framework.core.PageActions;
import com.wso2telco.test.framework.core.PrePageBase;

public class PageBase extends PrePageBase implements PageActions {

	
	
	
	public boolean flag;

	public PageBase(WebDriver driver) {
		super(driver);
		//pageActions = new PrePageBase(driver);
		
	}

}
