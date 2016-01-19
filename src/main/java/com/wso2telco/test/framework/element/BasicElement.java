package com.wso2telco.test.framework.element;

import com.wso2telco.test.framework.core.FObject;
import com.wso2telco.test.framework.util.UIType;

public class BasicElement extends FObject{
	protected UIType uiType;
	protected String uiValue;
	
	public BasicElement(UIType uiType, String value) {
		super();
		this.uiType = uiType;
		this.uiValue = value;
	}
	public UIType getUiType() {
		return uiType;
	}
	
	public String getUiValue() {
		return uiValue;
	}

}
