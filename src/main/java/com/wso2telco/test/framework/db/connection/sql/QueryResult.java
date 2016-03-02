package com.wso2telco.test.framework.db.connection.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

/**
 * The Class QueryResult.
 */
public class QueryResult {
	
	/** The result. */
	private List<Map<String, String>> result = new ArrayList<>();
	
	/**
	 * Gets the value.
	 *
	 * @author SulakkhanaW
	 * @param label the label
	 * @param index the index
	 * @return the value
	 */
	public String getValue(String label, int index) {
		if (result.size() <= index) {
			return null;
		}
		Map<String, String> row = result.get(index);
		return row.get(label);
	}
	
	/**
	 * Gets the all values.
	 *
	 * @author SulakkhanaW
	 * @param label the label
	 * @return the all values
	 */
	public List<String> getAllValues(String label) {
		label = label.toUpperCase();
		List<String> list = new ArrayList<>();
		if (result.isEmpty()) {
			return list;
		}
		for (Iterator<Map<String, String>> iterator = result.iterator(); iterator.hasNext();) {
			Map<String, String> map = (Map<String, String>) iterator.next();
			list.add(map.get(label));
		}

		return list;
	}
	
	/**
	 * Gets the value from condition.
	 *
	 * @author SulakkhanaW
	 * @param selectField the select field
	 * @param fromField the from field
	 * @param matchCondition the match condition
	 * @return the value from condition
	 */
	public String getValueFromCondition(String selectField, String fromField, String matchCondition) {
		for (Iterator<Map<String, String>> iterator = result.iterator(); iterator.hasNext();) {
			Map<String, String> map = (Map<String, String>) iterator.next();
			if (matchCondition.equals(map.get(fromField))) {
				return map.get(selectField);
			}
		}
		throw new ElementNotFoundException(selectField, "", "");
	}
	
	/**
	 * Gets the value from first row.
	 *
	 * @author SulakkhanaW
	 * @param label the label
	 * @return the value from first row
	 */
	public String getValueFromFirstRow(String label) {
		if (result.isEmpty()) {
			return null;
		}
		Map<String, String> row = result.get(0);
		if (row.get(label) == null) {
			return "";
		}
		return row.get(label);
	}
	
	/**
	 * Gets the row.
	 *
	 * @author SulakkhanaW
	 * @param index the index
	 * @return the row
	 */
	public Map<String, String> getRow(int index) {
		if (result.size() <= index) {
			return null;
		}
		Map<String, String> row = result.get(index);
		return row;
	}
	
	/**
	 * Num of result.
	 *
	 * @author SulakkhanaW
	 * @return the int
	 */
	public int numOfResult() {
		return result.size();
	}
	
	/**
	 * Enque row.
	 *
	 * @author SulakkhanaW
	 * @param row the row
	 */
	public void enqueRow(Map<String, String> row) {
		result.add(row);
	}
	
	/**
	 * Checks if is expected result.
	 *
	 * @author SulakkhanaW
	 * @param columnLabel the column label
	 * @param expectedResult the expected result
	 * @return true, if is expected result
	 */
	public boolean isExpectedResult(String columnLabel, String expectedResult) {
		if (numOfResult() != 1) {
			return false;
		}
		String actualRes = getValueFromFirstRow(columnLabel);
		if (actualRes == null) {
			return false;
		}

		return actualRes.equals(expectedResult);
	}
	
	/**
	 * Checks if is only one record.
	 *
	 * @author SulakkhanaW
	 * @return true, if is only one record
	 */
	public boolean isOnlyOneRecord() {
		return (numOfResult() == 1);
	}
	
	/**
	 * Gets the result size.
	 *
	 * @author SulakkhanaW
	 * @return the result size
	 */
	public int getResultSize() {
		return result.size();
	}
	
	/**
	 * Gets the column size.
	 *
	 * @author SulakkhanaW
	 * @return the column size
	 */
	public int getColumnSize(){
		return result.get(0).size();
	}

}
