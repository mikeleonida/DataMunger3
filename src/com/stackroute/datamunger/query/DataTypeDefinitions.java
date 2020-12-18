package com.stackroute.datamunger.query;

public class DataTypeDefinitions {

	private String[] dataTypes = null;

	/*
	 * This class should contain a member variable which is a String array, to hold
	 * the data type for all columns for all data types
	 */
	
	public DataTypeDefinitions() {
		
	}

	public DataTypeDefinitions(String[] dataTypes) {
		super();
		this.dataTypes = dataTypes;
	}
	
	public String[] getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(String[] columnHeadersTypes) {
		dataTypes = new String[columnHeadersTypes.length];
		for (int i = 0; i < columnHeadersTypes.length; i++) {
			dataTypes[i] = columnHeadersTypes[i];
		}
	}
	
	
}
