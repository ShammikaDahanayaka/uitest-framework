package com.wso2telco.test.framework.tools.excelfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The Class ExcelFileWriter.
 */
public class ExcelFileWriter {
	
	/** The workbook. */
	private static XSSFWorkbook workbook = null;

	/** The sheet. */
	private static XSSFSheet sheet = null;
	
	/** The row. */
	private static XSSFRow row = null;

	/** The cell. */
	private static XSSFCell cell = null;
	
	/**
	 * Write file using array.
	 *
	 * @param fileName the file name
	 * @param array the array
	 */
	public void writeFileUsingArray(String fileName, List<List<String>> array){
		try {
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("Sheet1");
			int rowCount = array.size();
			int columnCount = array.get(0).size();
			for (int i = 0; i < rowCount; i++) {
				row = sheet.createRow((short) 0 + i);
				for (int k = 0; k < columnCount; k++) {
					cell = row.createCell((short) k);
					cell.setCellValue(array.get(i).get(k));
				}
			}
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			workbook.write(fileOutputStream);
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write existing file.
	 *
	 * @param filename the filename
	 * @param sheetName the sheet name
	 * @param array the array
	 */
	public void writeExistingFile(String filename, String sheetName, List<List<String>> array){
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(filename));
			workbook = new XSSFWorkbook(fileInputStream);
			List<String> sheetNameList = new ArrayList<String>();
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				sheetNameList.add(workbook.getSheetName(i));
			}
			if (sheetNameList.contains(sheetName)) {
				sheet = workbook.getSheet(sheetName);
			} else {
				sheet = workbook.createSheet(sheetName);
			}
			int columnCount = array.get(0).size();
			row = sheet.getRow(0);
			row = sheet.createRow(0);
			for (int y = 0; y < columnCount; y++){
				cell = row.createCell((short) y);
				cell.setCellValue(array.get(0).get(y));
			}
			row = sheet.createRow(sheet.getLastRowNum() + 1);
			for (int x = 0; x < columnCount; x++) {
				cell = row.createCell((short) x);
				cell.setCellValue(array.get(1).get(x));
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filename);
			workbook.write(fileOutputStream);
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
