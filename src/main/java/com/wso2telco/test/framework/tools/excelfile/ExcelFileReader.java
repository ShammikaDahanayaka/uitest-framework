package com.wso2telco.test.framework.tools.excelfile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

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
	private static HSSFWorkbook workbook = null;

	/** The sheet. */
	private static HSSFSheet sheet = null;
	
	/**
	 * Read excel file.
	 *
	 * @author SulakkhanaW
	 * @param path the path
	 * @param givenSheetName the given sheet name
	 * @return the list
	 */
	public List<List<String>> readExcelFile(String path, String givenSheetName){
		this.path = path;
		List<List<String>> sheetdata = new ArrayList<List<String>>();
		DataFormatter formatter = new DataFormatter();
		try {
			fis = new FileInputStream(new File(path));
			workbook = new HSSFWorkbook(fis);
			int sheetNumbers = workbook.getNumberOfSheets();
			for (int i = 0; i < sheetNumbers; i++) {
				if (givenSheetName.equals(workbook.getSheetName(i))) {
					workbook.setActiveSheet(i);
					sheet = workbook.getSheetAt(i);
					Iterator<Row> rows = sheet.rowIterator();
		            while (rows.hasNext()) {
		                HSSFRow row = (HSSFRow) rows.next();
		                Iterator<Cell> cells = row.cellIterator();
		                List<String> data = new ArrayList<String>();
		                while (cells.hasNext()) {
		                    HSSFCell cell = (HSSFCell) cells.next();
		                    data.add(formatter.formatCellValue(cell));
		                }
		                sheetdata.add(data);
		            }
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return sheetdata;
	}

}
