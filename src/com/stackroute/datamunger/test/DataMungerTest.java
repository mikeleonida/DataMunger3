package com.stackroute.datamunger.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.stackroute.datamunger.reader.CsvQueryProcessor;
import com.stackroute.datamunger.sqlprocessor.SqlProcessor;

public class DataMungerTest {

	private static CsvQueryProcessor reader;
	public static final String DEFAULT_DATA_FILE = "data/ipl2.csv"; //"C:/Users/Mike/Desktop/data/ipl.csv";
	
	@BeforeClass
	public static void init() {
		try {
			reader = new CsvQueryProcessor(DEFAULT_DATA_FILE);
		} catch (FileNotFoundException e) {
			
		}
	}

	@AfterClass
	public static void close() throws FileNotFoundException {

	}

	@Test
	public void testRetrieveHeader() throws IOException {
		String expectedsString = "id, season, city, date, team1, team2, toss_winner, toss_decision, "
				+ "result, dl_applied, winner, win_by_runs, win_by_wickets, player_of_match, venue, "
				+ "umpire1, umpire2, umpire3";
		String[] expecteds = expectedsString.split(", ");
		assertArrayEquals(expecteds, reader.getHeader().getHeaders());
	}

	
	@Test
	public void testRetrieveHeaderFailure() throws IOException {
		// missing umpire3 field
		String expectedsString = "id, season, city, date, team1, team2, toss_winner, toss_decision, "
				+ "result, dl_applied, winner, win_by_runs, win_by_wickets, player_of_match, venue, "
				+ "umpire1, umpire2";
		String[] expecteds = expectedsString.split(", ");
		
		assertNotEquals(((long)expecteds.length), ((long)reader.getHeader().getHeaders().length));
	}

	@Test
	public void testRetrieveDataTypes() throws IOException {
		String expectedsString = "java.lang.Integer, java.lang.Integer, String, String, String, String, String, String, "
				+ "String, java.lang.Integer, String, java.lang.Integer, java.lang.Integer, String, String, "
				+ "String, String, String";
		String[] expecteds = expectedsString.split(", ");

		assertArrayEquals(expecteds, reader.getColumnType().getDataTypes());
	}

	@Test
	public void testRetrieveDataTypesFailure(){
		// no umpire3 value
		String expectedsString = "java.lang.Integer, java.lang.Integer, String, String, String, String, String, String, "
				+ "String, java.lang.Integer, String, java.lang.Integer, java.lang.Integer, String, String, "
				+ "String, String";
		String[] expecteds = expectedsString.split(", ");
		
		try {
			long numValues = reader.getColumnType().getDataTypes().length;
			assertNotEquals((long)expecteds.length, numValues);
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		}
		
		
	}

	@Test(expected = FileNotFoundException.class)
	public void testFileNotFound() throws FileNotFoundException {
		reader = new CsvQueryProcessor("iplipl.csv");
	}

	@Test
	public void testNotNullHeader() {
		try {
			assertNotNull(reader.getHeader().getHeaders()[0]);
		} catch (IOException ioe) {
			assertTrue(ioe.getMessage(), false);
		}
	}

	@Test
	public void testNotNullDataTypes() {
		try {
			assertNotNull(reader.getColumnType().getDataTypes()[0]);
		} catch (IOException ioe) {
			assertTrue(ioe.getMessage(), false);
		}
	}

	@Test
	public void testSqlProcessor() {
		String query = "Select id, team1, team2, venue from data/ipl3.csv "
				+ "where win_by_runs < 40 and win_by_wickets < 6";
		SqlProcessor sp = new SqlProcessor(query);
		
		String expectedsString = "2, 4, 5, 8, 9, 14, 15, 17, 21, 22, 23, 28, 29, 32, 33, 34, 36, 39";
		String[] expecteds = expectedsString.split(", ");
		Integer[] expectedIntegers = new Integer[expecteds.length];
		for (int i=0; i<expecteds.length; i++) {
			expectedIntegers[i] = Integer.parseInt(expecteds[i]);
		}
		
		assertArrayEquals(expectedIntegers, sp.getQueryResults().get("id"));
	}
	
//	private void display(String testCaseName, String result) 	{
//	}

}