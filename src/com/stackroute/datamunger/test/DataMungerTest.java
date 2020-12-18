package com.stackroute.datamunger.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;
import com.stackroute.datamunger.reader.CsvQueryProcessor;

public class DataMungerTest {

	private static CsvQueryProcessor reader;

	@BeforeClass
	public static void init() {
		System.out.println("Before class");
		try {
			reader = new CsvQueryProcessor("data/ipl.csv");
			System.out.println("After opening file");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	@AfterClass
	public static void close() throws FileNotFoundException {
		System.out.println("After class");
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
		
//		System.out.println(Arrays.toString(reader.getColumnType().getDataTypes()));
//		System.out.println(Arrays.toString(expecteds));
		
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
	public void testFileNotFound() {
		
		//reader = new CsvQueryProcessor("ipl.csv");
		
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

	private void display(String testCaseName, String result) 	{
	}

}