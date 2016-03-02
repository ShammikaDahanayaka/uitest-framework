package com.wso2telco.test.framework.element.table;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.wso2telco.test.framework.element.BasicElement;

/**
 * The Class Table.
 */
public class Table extends BasicElement {
	
	/** The table root. */
	private WebElement tableRoot;

	/**
	 * Instantiates a new table.
	 *
	 * @author SulakkhanaW
	 * @param uiType the ui type
	 * @param value the value
	 */
	public Table(WebElement tableRootLocator) {
		super(tableRootLocator);
		this.tableRoot = tableRootLocator;
	}
	
    /**
     * Body.
     *
     * @author SulakkhanaW
     * @return the t body
     */
    public TBody body() {
        return new TBody(getTBodyElement());
    }
    
    /**
     * Head.
     *
     * @author SulakkhanaW
     * @return the t head
     */
    public THead head() {
        return new THead(getTHeadElement());
    }
    
    /**
     * Gets the t head element.
     *
     * @author SulakkhanaW
     * @return the t head element
     */
    public WebElement getTHeadElement() {
        return tableRoot.findElement(By.xpath("child::thead"));
    }
    
    /**
     * Gets the t body element.
     *
     * @author SulakkhanaW
     * @return the t body element
     */
    public WebElement getTBodyElement() {
        return tableRoot.findElement(By.xpath("child::tbody"));
    }

}
