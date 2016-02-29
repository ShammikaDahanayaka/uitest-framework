package com.wso2telco.test.framework.element.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

/**
 * The Class TableComponent.
 */
public class TableComponent {

	/** The table component element. */
	private WebElement tableComponentElement;
	
	/**
	 * Instantiates a new table component.
	 *
	 * @author SulakkhanaW
	 * @param tableComponentElementVal the table component element val
	 */
	protected TableComponent(final WebElement tableComponentElementVal) {
		this.tableComponentElement = tableComponentElementVal;
	}
	
	/**
	 * Gets the all rows.
	 *
	 * @author SulakkhanaW
	 * @return the all rows
	 */
	public List<WebElement> getAllRows() {
		List<WebElement> rows = tableComponentElement.findElements(By.xpath("child::tr"));
		return rows;
	}
	
	/**
	 * Gets the cell from row.
	 *
	 * @author SulakkhanaW
	 * @param rowFromTableComponent the row from table component
	 * @param cellIndex the cell index
	 * @return the cell from row
	 */
	public WebElement getCellFromRow(final WebElement rowFromTableComponent, final int cellIndex) {
		List<WebElement> cells = this.getCellsFromRow(rowFromTableComponent);
		if (cells.size() > cellIndex) {
			return cells.get(cellIndex);
		}
		return null;
	}
	
	/**
	 * Gets the cells from row.
	 *
	 * @author SulakkhanaW
	 * @param rowFromTableComponent the row from table component
	 * @return the cells from row
	 */
	public List<WebElement> getCellsFromRow(final WebElement rowFromTableComponent) {
		return rowFromTableComponent.findElements(By.xpath("child::td|child::th"));
	}
	
	/**
	 * Gets the cells from column.
	 *
	 * @author SulakkhanaW
	 * @param columnIndex the column index
	 * @return the cells from column
	 */
	public List<WebElement> getCellsFromColumn(final int columnIndex) {
		int col = columnIndex +1;
		return tableComponentElement.findElements(By.xpath("tr/td[" + col +"]|tr/th[" + col +"]"));
	}
	
	/**
	 * Gets the cells text from row.
	 *
	 * @author SulakkhanaW
	 * @param rowFromTableComponent the row from table component
	 * @return the cells text from row
	 */
	public List<String> getCellsTextFromRow(final WebElement rowFromTableComponent) {
		List<String> returnList = new ArrayList<String>() ;
		rowFromTableComponent.findElements(By.xpath("td[1]|th[1]"));
		List<WebElement> cellList = rowFromTableComponent.findElements(By.xpath("td|th"));
		for (WebElement element : cellList){
			/*if ("".equals(element.getText())){
			}*/
			returnList.add(element.getText());
		}
		return returnList;
	}
	
	/**
	 * Gets the number of rows.
	 *
	 * @author SulakkhanaW
	 * @return the number of rows
	 */
	public int getNumberOfRows() {		
		return getAllRows().size();
	}
	
	/**
	 * Gets the number of columns.
	 *
	 * @author SulakkhanaW
	 * @return the number of columns
	 */
	public int getNumberOfColumns(){
		return getCellsFromRow(getRow(0)).size();
	}
	
	/**
	 * Gets the row.
	 *
	 * @author SulakkhanaW
	 * @param rowIndex the row index
	 * @return the row
	 */
	public WebElement getRow(final int rowIndex) {
		List<WebElement> rows = this.getAllRows();
		if (rowIndex <= rows.size()) {
			return rows.get(rowIndex);
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the row containing text.
	 *
	 * @author SulakkhanaW
	 * @param text the text
	 * @return the row containing text
	 */
	public WebElement getRowContainingText(final String text) {
		try {
			WebElement row = tableComponentElement.findElement(By.xpath("tr[contains(., '" + text + "')]"));
			return row;
			
		} catch (StaleElementReferenceException e) {
			throw new InvalidElementStateException();
		}
	}
	
	/**
	 * Gets the row containing text in column.
	 *
	 * @author SulakkhanaW
	 * @param text the text
	 * @param columnIndex the column index
	 * @return the row containing text in column
	 */
	public WebElement getRowContainingTextInColumn(final String text, int columnIndex) {
		columnIndex++;
		String xpath = ".//tr[*[self::td|self::th][" + columnIndex + "][contains(text(),\"" + text + "\")]]";
		return tableComponentElement.findElement(By.xpath(xpath));
	}
	
	/**
	 * Gets the row index using text in column.
	 *
	 * @author SulakkhanaW
	 * @param text the text
	 * @param columnIndex the column index
	 * @return the row index using text in column
	 */
	public int getRowIndexUsingTextInColumn(final String text, int columnIndex) {
		String cellText;
		List<WebElement> elements = getCellsFromColumn(columnIndex);
		int i = -1;
		for (Iterator<WebElement> iterator = elements.iterator(); iterator.hasNext();) {
			WebElement cell = iterator.next();
			cellText = cell.getText();
			if (cellText.equals(""))
			{
			cellText = cell.getText();
			}
			if (cellText.trim().equalsIgnoreCase(text.trim())){
				i++;
				return i;
			}
			i++;
		}
		return -1;
	}
	
	/**
	 * Gets the column index using text in row.
	 *
	 * @author SulakkhanaW
	 * @param text the text
	 * @param rowIndex the row index
	 * @return the column index using text in row
	 */
	public int getColumnIndexUsingTextInRow(final String text, int rowIndex) {
		List<WebElement> elements = getCellsFromRow(getRow(rowIndex));
		int i = -1;
		for (Iterator<WebElement> iterator = elements.iterator(); iterator.hasNext();) {
			WebElement cell = iterator.next();
			String str = cell.getText().trim();
					if(str == ""){
						str = cell.getText().trim();
					}
			if (str.equalsIgnoreCase(text.trim())){
				i++;
				return i;
			}
			i++;
		}
		return -1;
	}
	
	/**
	 * Gets the column index.
	 *
	 * @author SulakkhanaW
	 * @param text the text
	 * @return the column index
	 */
	public int getColumnIndex(final String text) {
		WebElement rowWithProvidedText = getRowContainingText(text);
		rowWithProvidedText.findElement(By.xpath("td[1]|th[1]"));
		
		String rowText = rowWithProvidedText.getText();
		String lines[] = rowText.split("\\r?\\n");
		if (lines.length == rowWithProvidedText.findElements(By.xpath("td|th")).size()){
		int retValue = Arrays.asList(lines).indexOf(text);
		if (retValue > -1){
			return retValue;
		}
		}

		
		List<WebElement> cellList = rowWithProvidedText.findElements(By.xpath("td|th"));
		int i = -1;
		for (WebElement element : cellList){
			String temp = element.getText();
			if ("".equals(temp)){
				temp = element.getText();
			}
			i++;
			if (text.equalsIgnoreCase(temp)){
				return i;
			}
		}
		int colIndex = -1;
		WebElement link = null;
		List<WebElement> cols = getCellsFromRow(rowWithProvidedText);
		for (int j = 0; j < cols.size(); j++) {
			WebElement cell = cols.get(j);
			String cellText = cell.getText().trim();
			if (cellText.equals(text)) {
				colIndex = j;
				return colIndex;
			}

			if (cellText.length() == 0) {

				try {
					link = cell.findElement(By.tagName("a"));
				} catch (Exception e) {
					continue;
				}

				if ((link != null && link.getAttribute("title").equals(text))
						|| cell.getText().trim().equals(text)) {
					colIndex = i;
					return colIndex;
				}
			}

		}
		return colIndex;
	}
	
	/**
	 * Gets the row index.
	 *
	 * @author SulakkhanaW
	 * @param text the text
	 * @return the row index
	 */
	public int getRowIndex(final String text) {
		int rowIndex = -2;
		String tempRowText;
		List<WebElement> rows = getAllRows();
		WebElement row = getRowContainingText(text);
		String rowText = row.getText().trim();
		for (int i = 0; i < rows.size(); i++) {
			tempRowText = rows.get(i).getText();
			if (tempRowText.equals("")){
				tempRowText = rows.get(i).getText();
			}
			if (tempRowText.trim().equalsIgnoreCase(rowText)) {
				rowIndex = i;
				break;
			}
		}
		return rowIndex;
	}
	
	/**
	 * Gets the cell from row index column index.
	 *
	 * @author SulakkhanaW
	 * @param rowIndex the row index
	 * @param columnIndex the column index
	 * @return the cell from row index column index
	 */
	public WebElement getCellFromRowIndexColumnIndex(final int rowIndex, final int columnIndex) {
		WebElement row = getRow(rowIndex);
		int i = columnIndex + 1;
		WebElement temp = row.findElement(By.xpath("td["+ i +"] | th[" + i +"]"));
		temp.isDisplayed();
		return temp;
	}
	
	/**
	 * Gets the cell from text.
	 *
	 * @author SulakkhanaW
	 * @param text the text
	 * @return the cell from text
	 */
	public WebElement getCellFromText(final String text) {
		int x = -1;
		int y = -1;
		x = getRowIndex(text);
		y = getColumnIndex(text);
		return getCellFromRowIndexColumnIndex(x, y);
	}

}
