package com.stackroute.datamunger.query;

import java.util.Arrays;

public class Header {

	private String[] headers = null;
	
	/*
	 * This class should contain a member variable which is a String array, to hold
	 * the headers.
	 */
	
	public Header () {
		
	}
	
	public Header(String[] headers) {
		super();
		this.headers = headers;
	}
	
	public String[] getHeaders() {
		return headers;
	}

	public void setDataTypes(String[] headerStrings) {
		headers = new String[headerStrings.length];
		for (int i = 0; i < headerStrings.length; i++) {
			headers[i] = headerStrings[i];
		}
	}

	@Override
	public String toString() {
		return "Header [headers=" + Arrays.toString(headers) + "]";
	}

}
