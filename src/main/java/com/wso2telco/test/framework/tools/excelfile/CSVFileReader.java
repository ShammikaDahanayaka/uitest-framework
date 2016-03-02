package com.wso2telco.test.framework.tools.excelfile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

public class CSVFileReader {

	/**
	 * Csv column count.
	 *
	 * @param filePath
	 *            the file path
	 * @return the int
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public int csvColumnCount(String filePath) throws IOException {
		int counter = 0;
		File file = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		for (int f = 0; f < line.length(); f++) {
			if (line.charAt(f) == ',') {
				counter++;
			}
		}
		br.close();
		return counter;
	}

	/**
	 * Csv row count.
	 *
	 * @param filePath
	 *            the file path
	 * @return the int
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
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
	 * Read data.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void readDataFromCSV(String filePath) throws IOException {
		
		int rowNumber = 0;
		HashMap<Integer, String[]> map = new HashMap<Integer, String[]>();

		final String DELIMITER = ",";

		String line = "";

		BufferedReader fileReader = new BufferedReader(new FileReader(filePath));

		while ((line = fileReader.readLine()) != null) {
			rowNumber++;
			if(!(line.equals(""))){

			// Get all tokens available in line
			String[] tokens = line.split(DELIMITER);

			map.put(rowNumber, tokens);
		}}

		for (Integer key : map.keySet()) {
			System.out
					.println("------------------------------------------------");

			System.out.println("row number: " + key);

			String[] result = map.get(key);
			System.out.println(" Row values" + Arrays.toString(result));
		}

		fileReader.close();

	}

}
