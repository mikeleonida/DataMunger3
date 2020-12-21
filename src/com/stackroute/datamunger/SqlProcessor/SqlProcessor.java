package com.stackroute.datamunger.sqlprocessor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.Restriction;
import com.stackroute.datamunger.reader.CsvQueryProcessor;

public class SqlProcessor {
	
	// private CsvQueryProcessor reader;
	private String query;
	
	public SqlProcessor() {
		
	}
	
	public SqlProcessor(String query) {
		super();
		this.query = query;
	}
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}


	public Map<String, Object[]> getQueryResults() {
		CsvQueryProcessor reader = null;
		Map<String, Object[]> queryData = new HashMap<>();
		QueryParameter qp = new QueryParameter(query);
		
		try {
			reader = new CsvQueryProcessor(qp.getFileName());
			String[] headers = reader.getHeader().getHeaders();
			
			boolean queryFieldsInFile = true;
			Map<String, Integer> fieldIndices = new HashMap<>();
			
			List<String> queryFields = qp.getFields();
			
			for (String field : queryFields) {
				boolean queryFieldFound = false;
				for (int i = 0; i<headers.length; i++) {
					if (headers[i].equalsIgnoreCase(field)) {
						queryFieldFound = true;
						fieldIndices.put(field, i);
						break;
					}
				}
				if (!queryFieldFound) {
					queryFieldsInFile = false;
					break;
				}
			}
			if (!queryFieldsInFile) {
				return null;
			}
			
			DataTypeDefinitions d = reader.getColumnType();
			int numFields = queryFields.size();
			LinkedList<Object>[] values = new LinkedList[numFields];
			
			for (int j=0; j<numFields; j++) {
				values[j] = new LinkedList<Object>();
			}
			
			String[] nextRow = reader.getDataRow();
			while (nextRow!=null) {	
				if (!checkConditions(reader, nextRow, qp.getRestrictions(), qp.getLogicalOperators())) {
					nextRow = reader.getDataRow();
					continue;
				}
				
				for (int j=0; j<numFields; j++) {
					int index = fieldIndices.get(queryFields.get(j));
					String value = nextRow[index];
					
					Object o;
					if (d.getDataTypes()[index].equalsIgnoreCase("java.lang.Integer")) {
						o = Integer.parseInt(value);
					} else if (d.getDataTypes()[index].equalsIgnoreCase("java.lang.Double")) {
						o = Double.parseDouble(value);
					} else {
						o = value;
					}	
					values[j].add(o);
				}
				nextRow = reader.getDataRow();
			}
			
			for (int i=0; i<numFields; i++) {
				queryData.put(queryFields.get(i), values[i].toArray());
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return queryData;
	}

	public boolean checkConditions(CsvQueryProcessor reader, String[] dataRow, List<Restriction> r, 
			List<String> logicalOps) throws IOException {
		if (r.isEmpty()) {
			return true;
		}
		
		String[] headers = reader.getHeader().getHeaders();
		//String field = null;
		String value = null;
		int index = -1;
		for (int i=0; i<dataRow.length; i++) {
			if (r.get(0).getName().equalsIgnoreCase(headers[i])) {
				//field = headers[i];
				value = dataRow[i];
				index = i;
				break;
			}
		}
		
		boolean currentCondition = checkCondition(value, r.get(0).getCondition(), r.get(0).getValue(), 
				reader.getColumnType().getDataTypes()[index]);
		
		if (currentCondition && (logicalOps.isEmpty() || logicalOps.get(0).equalsIgnoreCase("or"))) {
			return true;
		} else if (!currentCondition && (logicalOps.isEmpty() || logicalOps.get(0).equalsIgnoreCase("and"))) {
			return false;
		}
		
		r.remove(0);
		logicalOps.remove(0);
		return checkConditions(reader, dataRow, r, logicalOps);
	}
	
	public boolean checkCondition(String value, String condition, String restrictionValue, String type) {
		if (condition.equalsIgnoreCase("<=") || condition.equalsIgnoreCase("=<")) {
			if (type.equals("java.lang.Integer")) {
				return (Integer.parseInt(value) <= Integer.parseInt(restrictionValue));
			} else if (type.equals("java.lang.Double")) {
				return (Double.parseDouble(value) <= Double.parseDouble(restrictionValue));
			} 
			return (value.compareTo(restrictionValue) <= 0);
		} else if (condition.equalsIgnoreCase(">=") || condition.equalsIgnoreCase("=>")) {
			if (type.equals("java.lang.Integer")) {
				return (Integer.parseInt(value) >= Integer.parseInt(restrictionValue));
			} else if (type.equals("java.lang.Double")) {
				return (Double.parseDouble(value) >= Double.parseDouble(restrictionValue));
			} 
			return (value.compareTo(restrictionValue) >= 0);
		} else if (condition.equalsIgnoreCase("!=")) {
			if (type.equals("java.lang.Integer")) {
				return (Integer.parseInt(value) != Integer.parseInt(restrictionValue));
			} else if (type.equals("java.lang.Double")) {
				return (Double.parseDouble(value) != Double.parseDouble(restrictionValue));
			} 
			return (value.compareTo(restrictionValue) != 0);
		} else if (condition.equalsIgnoreCase("<")) {
			if (type.equals("java.lang.Integer")) {
				//System.out.println("value = " + value + ", restrictionValue = " + restrictionValue);
				return (Integer.parseInt(value) < Integer.parseInt(restrictionValue));
			} else if (type.equals("java.lang.Double")) {
				return (Double.parseDouble(value) < Double.parseDouble(restrictionValue));
			} 
			return (value.compareTo(restrictionValue) < 0);
		} else if (condition.equalsIgnoreCase(">")) {
			if (type.equals("java.lang.Integer")) {
				return (Integer.parseInt(value) > Integer.parseInt(restrictionValue));
			} else if (type.equals("java.lang.Double")) {
				return (Double.parseDouble(value) > Double.parseDouble(restrictionValue));
			} 
			return (value.compareTo(restrictionValue) > 0);
		} else if (condition.equalsIgnoreCase("=") || condition.equalsIgnoreCase("==")) {
			if (type.equals("java.lang.Integer")) {
				return (Integer.parseInt(value) == Integer.parseInt(restrictionValue));
			} else if (type.equals("java.lang.Double")) {
				return (Double.parseDouble(value) == Double.parseDouble(restrictionValue));
			} 
			return (value.compareToIgnoreCase(restrictionValue) == 0);
		}
		//default, probably should not happen
		return true;
	}
	
}