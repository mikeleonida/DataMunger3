package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;

public class CsvQueryProcessor extends QueryProcessingEngine {
	
	private String csvFile = "";
	
	// Parameterized constructor to initialize filename
	public CsvQueryProcessor(String fileName) throws FileNotFoundException {
		FileReader fr = new FileReader(fileName);
		csvFile = fileName;
		try {
			fr.close();
		} catch (IOException e) {
			
		}
	}

	public CsvQueryProcessor() {

	}
	
	/*
	 * Implementation of getHeader() method. We will have to extract the headers
	 * from the first line of the file.
	 * Note: Return type of the method will be Header
	 */
	
	@Override
	public Header getHeader() throws IOException {
		FileReader fr = null;
		BufferedReader br = null;
		
		fr = new FileReader(csvFile);
		br = new BufferedReader(fr); 
		
		// read the first line
		String firstLine = br.readLine();
		if (firstLine == null) {
			br.close();
			fr.close();
			return null;
		}
		
		// populate the header object with the String array containing the header names
		String[] headers = firstLine.trim().replaceAll("\\s+", "").split(",");
		
		fr.close();
		br.close();
		
		return new Header(headers);
	}

	/**
	 * getDataRow() method will be used in the upcoming assignments
	 */
	
	@Override
	public void getDataRow() {
		
	}

	/*
	 * Implementation of getColumnType() method. To find out the data types, we will
	 * read the first line from the file and extract the field values from it. If a
	 * specific field value can be converted to Integer, the data type of that field
	 * will contain "java.lang.Integer", otherwise if it can be converted to Double,
	 * then the data type of that field will contain "java.lang.Double", otherwise,
	 * the field is to be treated as String. 
	 * Note: Return Type of the method will be DataTypeDefinitions
	 */
	
	@Override
	public DataTypeDefinitions getColumnType() throws IOException {
		
		FileReader fr = null;
		BufferedReader br = null;
		
		fr = new FileReader(csvFile);
		br = new BufferedReader(fr); 
		
		// read the first line
		String firstLine = br.readLine();
		if (firstLine == null) {
			br.close();
			return null;
		}
		String secondLine = br.readLine();
		if (secondLine == null) {
			br.close();
			return null;
		}
		
		// populate the header object with the String array containing the header names
		String[] values = (secondLine+" ").split(","); //add empty space in case last cell is empty
		int len = values.length;
		String[] types = new String[len];
		
		for (int i = 0; i<len; i++) {
			String value = values[i].trim();
			try {
				Integer.parseInt(value);
				types[i] = "java.lang.Integer";
			} catch (NumberFormatException e) {
				try {
					Double.parseDouble(value);
					types[i] = "java.lang.Double";
				} catch (NumberFormatException ex) {
					types[i] = "String";
				}
			}
		}
		
		fr.close();
		br.close();
		
		return new DataTypeDefinitions(types);
	}
}
