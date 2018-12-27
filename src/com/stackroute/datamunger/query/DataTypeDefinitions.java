package com.stackroute.datamunger.query;

/*
 * Implementation of DataTypeDefinitions class. This class contains a method getDataTypes() 
 * which will contain the logic for getting the datatype for a given field value. This
 * method will be called from QueryProcessors.   
 * In this assignment, we are going to use Regular Expression to find the 
 * appropriate data type of a field. 
 * Integers: should contain only digits without decimal point 
 * Double: should contain digits as well as decimal point 
 * Date: Dates can be written in many formats in the CSV file. 
 * However, in this assignment,we will test for the following date formats('dd/mm/yyyy',
 * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm-dd')
 */
public class DataTypeDefinitions {

	//method stub
	public static Object getDataType(String input) {
		
		String value = null;
		
		if(input.matches("[0-9]+")) {
			value = "java.lang.Integer";
		} else if(input.matches("[0-9]+.[0-9]+")) {
			value = "java.lang.Double";
		} else if(input.matches("^[0-9]{2}/[0-9]{2}/[0-9]{4}$")
				|| input.matches("^[0-9]{2}-[a-z]{3}-[0-9]{2}$")
				|| input.matches("^[0-9]{2}-[a-z]{3}-[0-9]{4}$")
				|| input.matches("^[0-9]{2}-[a-z]{3,9}-[0-9]{2}$")
				|| input.matches("^[0-9]{2}-[a-z]{3,9}-[0-9]{4}$")
				|| input.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {
			value = "java.util.Date";
		} else if(input.isEmpty()) {
			value = "java.lang.Object";
		} else {
			value = "java.lang.String";
		}
		return value;
	}
}
