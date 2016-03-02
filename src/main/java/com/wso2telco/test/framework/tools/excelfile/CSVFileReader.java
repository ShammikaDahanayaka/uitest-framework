package com.wso2telco.test.framework.tools.excelfile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class CSVFileReader.
 */
public class CSVFileReader {

	public int csvColumnCount(String filePath) throws IOException {
		int counter = 0;
		File file = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		for (int f = 0; f < line.length(); f++) {
			if(!(line.equals(""))){
			if (line.charAt(f) == ',') {
				counter++;
			}
			}
		}
		br.close();
		return counter;
	}

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

	public HashMap<Integer, String[]> readDataFromCSV(String filePath) throws IOException {
		
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
		}
			}
		fileReader.close();
		return map;
	}

	public String[][] getCSVValues(HashMap<Integer, String[]> map) throws IOException {
		int row = map.size();
		String apiListDetails[][] = new String[row][9];
		for (int i = 0; i < row;) {
		for (Integer key : map.keySet()) {
			String[] result = map.get(key);
			int column =  result.length;
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
