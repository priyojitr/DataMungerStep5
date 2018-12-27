package com.stackroute.datamunger.query.parser;

/* This class is used for storing name of field, aggregate function for 
 * each aggregate function
 * */
public class AggregateFunction {
	
	private String field;
	private String function;
	
	public AggregateFunction(String field, String function) {
		this.field = field;
		this.function = function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
	
	public String getFunction() {
		return this.function;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	public String getField() {
		return this.field;
	}
}
