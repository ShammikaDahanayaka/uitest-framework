package com.wso2telco.test.framework.tools.excelfile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The Class CSVFileReader.
 */
public class CSVFileReader {

	/** The fis. */
	public FileInputStream fis = null;

	/** The my input. */
	public DataInputStream myInput = null;

	/** The workbook. */
	private static XSSFWorkbook workbook = null;

	/** The sheet. */
	private static XSSFSheet sheet = null;

	/** The row. */
	private static XSSFRow row = null;

	/** The cell. */
	private static XSSFCell cell = null;

	/**
	 * Convert csv to xlsx.
	 *
	 * @author SulakkhanaW
	 * @param filePath the file path
	 * @param csvFileName the csv file name
	 * @param xlsxFileName the xlsx file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("deprecation")
	public void convertCSVToXLSX(String filePath, String csvFileName, String xlsxFileName) throws IOException {
		String xlsxOutputPath = filePath + "/" + xlsxFileName;
		String csvFilePath = filePath + "/" + csvFileName;
		ArrayList<ArrayList<String>> allRowAndColData = null;
		ArrayList<String> oneRowData = null;
		String currentLine;
		fis = new FileInputStream(csvFilePath);
		myInput = new DataInputStream(fis);
		allRowAndColData = new ArrayList<ArrayList<String>>();
		while ((currentLine = myInput.readLine()) != null) {
			oneRowData = new ArrayList<String>();
			String oneRowArray[] = currentLine.split(",");
			for (int j = 0; j < oneRowArray.length; j++) {
				oneRowData.add(oneRowArray[j]);
			}
			allRowAndColData.add(oneRowData);
		}
		try {
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("sheet1");
			for (int i = 0; i < allRowAndColData.size(); i++) {
				ArrayList<?> ardata = (ArrayList<?>) allRowAndColData.get(i);
				row = sheet.createRow((short) 0 + i);
				for (int k = 0; k < ardata.size(); k++) {
					cell = row.createCell((short) k);
					cell.setCellValue(ardata.get(k).toString());
				}
			}
			FileOutputStream fileOutputStream = new FileOutputStream(
					xlsxOutputPath);
			workbook.write(fileOutputStream);
			fileOutputStream.close();
		} catch (Exception ex) {
		}
	}


	/**
	 * Csv column count.
	 *
	 * @author SulakkhanaW
	 * @param filePath the file path
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public int csvColumnCount(String filePath) throws IOException {
		int counter = 0;
		File file = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		for (int f = 0; f < line.length(); f++) {
			if (!(line.equals(""))) {
				if (line.charAt(f) == ',') {
					counter++;
				}
			}
		}
		br.close();
		return counter;
	}

	/**
	 * Csv row count.
	 *
	 * @author SulakkhanaW
	 * @param filePath the file path
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public int csvRowCount(String filePath) throws IOException {
		File file = new File(filePath);
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		byte[] c = new byte[1024];
		int count = 0;
		int readChars = 0;
		boolean empty = true;

		while ((readChars = is.read(c)) != -1) {
			empty = false;
			for (int i = 0; i < readChars; ++i) {
				if (c[i] == '\n') {
					++count;
				}
			}
		}
		count = (count == 0 && !empty) ? 1 : count;
		is.close();
		return count;
	}

	/**
	 * Read data from csv.
	 *
	 * @author SulakkhanaW
	 * @param filePath the file path
	 * @return the hash map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public HashMap<Integer, String[]> readDataFromCSV(String filePath) throws IOException {
		int rowNumber = 0;
		HashMap<Integer, String[]> map = new HashMap<Integer, String[]>();
		final String DELIMITER = ",";
		String line = "";
		BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
		while ((line = fileReader.readLine()) != null) {
			rowNumber++;
			if (!(line.equals(""))) {
				String[] tokens = line.split(DELIMITER);
				map.put(rowNumber, tokens);
			}
		}
		fileReader.close();
		return map;
	}

	/**
	 * Gets the CSV values.
	 *
	 * @author SulakkhanaW
	 * @param map the map
	 * @return the CSV values
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String[][] getCSVValues(HashMap<Integer, String[]> map) throws IOException {
		int row = map.size();
		String apiListDetails[][] = new String[row][9];
		for (int i = 0; i < row;) {
			for (Integer key : map.keySet()) {
				String[] result = map.get(key);
				int column = result.length;
				for (int x = 0; x < column;) {
					apiListDetails[i][x] = result[x].trim();
					x++;
				}
				i++;
			}
		}
		return apiListDetails;
	}
}
