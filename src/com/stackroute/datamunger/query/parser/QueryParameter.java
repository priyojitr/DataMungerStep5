package com.stackroute.datamunger.query.parser;

import java.util.List;
/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */
public class QueryParameter {
	
	private String fileName;
	private List<String> fields;
	private List<Restriction> restrictions;
	private String baseQuery;
	private List<AggregateFunction> aggregateFunctions;
	private List<String> logicalOperators;
	private List<String> groupByFields;
	private List<String> orderByFields;
	

	public void setFilName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return this.fileName;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	
	public List<String> getFields() {
		return this.fields;
	}

	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}
	
	public List<Restriction> getRestrictions() {
		return this.restrictions;
	}

	public void setBaseQuery(String baseQuery) {
		this.baseQuery = baseQuery;
	}
	public String getBaseQuery() {
		return this.baseQuery;
	}

	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctions) {
		this.aggregateFunctions = aggregateFunctions;
	}
	public List<AggregateFunction> getAggregateFunctions() {
		return this.aggregateFunctions;
	}

	public void setLogicalOperators(List<String> logicalOperators) {
		this.logicalOperators = logicalOperators;
	}
	
	public List<String> getLogicalOperators() {
		return this.logicalOperators;
	}

	public void setGroupByFields(List<String> groupByFields) {
		this.groupByFields = groupByFields;
	}
	
	public List<String> getGroupByFields() {
		return this.groupByFields;
	}

	public void setOrderByFields(List<String> orderByFields) {
		this.orderByFields = orderByFields;
	}
	
	public List<String> getOrderByFields() {
		return this.orderByFields;
	}

	public String getQUERY_TYPE() {
		// TODO Auto-generated method stub
		return null;
	}
}
