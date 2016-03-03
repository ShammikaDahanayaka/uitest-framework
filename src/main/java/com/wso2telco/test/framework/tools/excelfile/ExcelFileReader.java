package com.wso2telco.test.framework.tools.excelfile;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The Class ExcelFileReader.
 */
public class ExcelFileReader {
	
	/** The path. */
	public String path;

	/** The fis. */
	public FileInputStream fis = null;

	/** The file out. */
	public FileOutputStream fileOut = null;

	/** The workbook. */
	private static XSSFWorkbook workbook = null;

	/** The sheet. */
	private static XSSFSheet sheet = null;
	
	/** The active sheet name. */
	private static String activeSheetName;
	
	/** The row. */
	private static XSSFRow row = null;
	
	/** The cell. */
	private static XSSFCell cell = null;
	
	/**
	 * Instantiates a new excel file reader.
	 *
	 * @author SulakkhanaW
	 * @param path the path
	 * @param givenSheetName the given sheet name
	 * @throws Exception the exception
	 */
	public ExcelFileReader(String path, String givenSheetName) throws Exception {
		activeSheetName = givenSheetName;
		this.path = path;
		try {
			fis = new FileInputStream(new File(path));
			workbook = new XSSFWorkbook(fis);

			int sheetNumbers = workbook.getNumberOfSheets();
			for (int i = 0; i < sheetNumbers; i++) {
				if (givenSheetName.equals(workbook.getSheetName(i))) {
					workbook.setActiveSheet(i);
					sheet = workbook.getSheetAt(i);
					return;
				}
			}
			throw new Exception("File " + path + " does not contains the sheet" + givenSheetName);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	/**
	 * Read excel file.
	 *
	 * @author SulakkhanaW
	 * @param givenSheetName the given sheet name
	 * @return the list
	 */
	public List<List<String>> readExcelFile(String givenSheetName) {
		removeEmptyRow(givenSheetName);
		List<List<String>> sheetdata = new ArrayList<List<String>>();
		DataFormatter formatter = new DataFormatter();
		try {
			fis = new FileInputStream(new File(path));
			workbook = new XSSFWorkbook(fis);
			int sheetNumbers = workbook.getNumberOfSheets();
			for (int i = 0; i < sheetNumbers; i++) {
				if (givenSheetName.equals(workbook.getSheetName(i))) {
					workbook.setActiveSheet(i);
					sheet = workbook.getSheetAt(i);
					Iterator<Row> rows = sheet.rowIterator();
					while (rows.hasNext()) {
						XSSFRow row = (XSSFRow) rows.next();
						Iterator<Cell> cells = row.cellIterator();
						List<String> data = new ArrayList<String>();
						while (cells.hasNext()) {
							XSSFCell cell = (XSSFCell) cells.next();
							data.add(formatter.formatCellValue(cell));
						}
						sheetdata.add(data);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sheetdata;
	}
	
	/**
	 * Removes the empty row.
	 *
	 * @author SulakkhanaW
	 * @param givenSheetName the given sheet name
	 */
	public void removeEmptyRow(String givenSheetName){
		boolean isRowEmpty = false;
		try {
			fis = new FileInputStream(new File(path));
			workbook = new XSSFWorkbook(fis);
			int sheetNumbers = workbook.getNumberOfSheets();
			for (int i = 0; i < sheetNumbers; i++) {
				if (givenSheetName.equals(workbook.getSheetName(i))) {
					workbook.setActiveSheet(i);
					sheet = workbook.getSheetAt(i);

					for (int x = 0; x < sheet.getLastRowNum(); x++) {
						if (sheet.getRow(x) == null) {
							sheet.shiftRows(x + 1, sheet.getLastRowNum(), -1);
							i--;
							continue;
						}
						for (int j = 0; j < sheet.getRow(x).getLastCellNum(); j++) {
							if (sheet.getRow(x).getCell(j).toString().trim()
									.equals("")) {
								isRowEmpty = true;
							} else {
								isRowEmpty = false;
								break;
							}
						}
						if (isRowEmpty == true) {
							sheet.shiftRows(x + 1, sheet.getLastRowNum(), -1);
							x--;
						}
					}
					fileOut = new FileOutputStream(path);
					workbook.write(fileOut);
					fileOut.close();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Change active sheet.
	 *
	 * @author SulakkhanaW
	 * @param sheetName the sheet name
	 */
	public static void changeActiveSheet(String sheetName){
		int sheetIndex = workbook.getSheetIndex(sheetName);
		workbook.setActiveSheet(sheetIndex);
		sheet = workbook.getSheetAt(sheetIndex);
		activeSheetName = workbook.getSheetName(sheetIndex);
		return;
	}
	
	/**
	 * Gets the row count active sheet.
	 *
	 * @author SulakkhanaW
	 * @return the row count active sheet
	 */
	public int getRowCountActiveSheet() {
		int number = sheet.getLastRowNum() + 1;
		return number;
	}
	
	/**
	 * Gets the header row values.
	 *
	 * @author SulakkhanaW
	 * @param sheetName the sheet name
	 * @return the header row values
	 */
	public String [] getHeaderRowValues(String sheetName){
		changeActiveSheet(sheetName);
		int columnCount = getColumnCount(sheetName);
		String header[] = new String[columnCount];
		for (int i=0; i< columnCount; i++){
			header[i] = getCellData(i, 1);
		}
		return header;
	}
	
	/**
	 * Gets the column count.
	 *
	 * @author SulakkhanaW
	 * @param sheetName the sheet name
	 * @return the column count
	 */
	public int getColumnCount(String sheetName) {
		if (!isSheetExist(sheetName))
			return -1;
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		if (row == null)
			return -1;
		return row.getLastCellNum();
	}
	
	/**
	 * Gets the cell data.
	 *
	 * @author SulakkhanaW
	 * @param colNum the col num
	 * @param rowNum the row num
	 * @return the cell data
	 */
	private String getCellData(int colNum, int rowNum) {

		try {
			if (rowNum <= 0)
				return "";

			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return cell.getStringCellValue().trim();
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}

	}
	
	/**
	 * Checks if is sheet exist.
	 *
	 * @author SulakkhanaW
	 * @param sheetName the sheet name
	 * @return true, if is sheet exist
	 */
	public boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}
	
	/**
	 * Gets the row count.
	 *
	 * @author SulakkhanaW
	 * @param sheetName the sheet name
	 * @return the row count
	 */
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}

	}
	
	/**
	 * Removes the column.
	 *
	 * @author SulakkhanaW
	 * @param sheetName the sheet name
	 * @param colNum the col num
	 * @return true, if successful
	 */
	public boolean removeColumn(String sheetName, int colNum) {
		try {
			if (!isSheetExist(sheetName))
				return false;
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook();
			sheet = workbook.getSheet(sheetName);
			XSSFCellStyle style = workbook.createCellStyle();
			XSSFColor myColor = new XSSFColor(Color.GRAY);
			style.setFillForegroundColor(myColor);
			style.setFillPattern(XSSFCellStyle.NO_FILL);

			for (int i = 0; i < getRowCount(sheetName); i++) {
				row = sheet.getRow(i);
				if (row != null) {
					cell = row.getCell(colNum);
					if (cell != null) {
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
	/**
	 * Find row.
	 *
	 * @author SulakkhanaW
	 * @param sheetName the sheet name
	 * @param cellContent the cell content
	 * @return the int
	 */
	public static int findRow(String sheetName, String cellContent) {
		int sheetIndex = workbook.getSheetIndex(sheetName);
		workbook.setActiveSheet(sheetIndex);
		sheet = workbook.getSheetAt(sheetIndex);
	    for (Row row : sheet) {
	        for (Cell cell : row) {
	            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
	                if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
	                    return row.getRowNum();  
	                }
	            }
	        }
	    }               
	    return 0;
	}
	
	/**
	 * Gets the cell data.
	 *
	 * @author SulakkhanaW
	 * @param sheetName the sheet name
	 * @param colName the col name
	 * @param rowNum the row num
	 * @return the cell data
	 */
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";
			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
					|| cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/"
							+ cellText;
				}

				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}
	
	/**
	 * Gets the celldata from active sheet.
	 *
	 * @author SulakkhanaW
	 * @param colName the col name
	 * @param rowNum the row num
	 * @return the celldata from active sheet
	 */
	public String getCelldataFromActiveSheet(String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(activeSheetName);
			int col_Num = -1;
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";

			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";
			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
					|| cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/"
							+ cellText;
				}

				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}
	
	/**
	 * Sets the cell data.
	 *
	 * @author SulakkhanaW
	 * @param sheetName the sheet name
	 * @param colName the col name
	 * @param rowNum the row num
	 * @param data the data
	 * @return true, if successful
	 */
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook();

			if (rowNum <= 0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			// cell style
			cell.setCellValue(data);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Sets the cell data.
	 *
	 * @author SulakkhanaW
	 * @param sheetName the sheet name
	 * @param colName the col name
	 * @param rowNum the row num
	 * @param data the data
	 * @param url the url
	 * @return true, if successful
	 */
	public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook();

			if (rowNum <= 0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
					colNum = i;
			}

			if (colNum == -1)
				return false;
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			cell.setCellValue(data);
			XSSFCreationHelper createHelper = workbook.getCreationHelper();

			// cell style for hyperlinks
			// by default hypelrinks are blue and underlined
			CellStyle hlink_style = workbook.createCellStyle();
			XSSFFont hlink_font = workbook.createFont();
			hlink_font.setUnderline(XSSFFont.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);

			XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
			link.setAddress(url);
			cell.setHyperlink(link);
			cell.setCellStyle(hlink_style);

			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Adds the column.
	 *
	 * @author SulakkhanaW
	 * @param sheetName the sheet name
	 * @param colName the col name
	 * @return true, if successful
	 */
	public boolean addColumn(String sheetName, String colName) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook();
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return false;

			XSSFCellStyle style = workbook.createCellStyle();
			XSSFColor myColor = new XSSFColor(Color.GRAY);
			style.setFillForegroundColor(myColor);
			style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);

			if (row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());

			cell.setCellValue(colName);
			cell.setCellStyle(style);

			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Gets the desired value.
	 *
	 * @author IsuruM
	 * @param capitalList the capital list
	 * @param selectField the select field
	 * @param fromField the from field
	 * @param matchingValue the matching value
	 * @return the desired value
	 */
	public String getDesiredValue(List<List<String>> capitalList, String selectField, String fromField, String matchingValue) {
		String value = null;
		int count = 0;
		int rowCount = capitalList.size();
		List<String> selectRowList = capitalList.get(0);
		try {
			if (selectRowList.contains(fromField)) {
				for (int i = 1; i < rowCount; i++) {
					selectRowList = capitalList.get(i);
					if (selectRowList.contains(matchingValue)) {
						count = count + 1;
					}
				}
				selectRowList = capitalList.get(0);
				int index = selectRowList.indexOf(selectField);
				selectRowList = capitalList.get(count);
				value = selectRowList.get(index);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

}
