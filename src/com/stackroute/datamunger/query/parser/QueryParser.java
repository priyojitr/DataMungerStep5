package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {

	private QueryParameter queryParam = new QueryParameter();
	
	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {
		
		queryParam.setFilName(getFileName(queryString));
		queryParam.setOrderByFields(getOrderByFields(queryString));
		queryParam.setGroupByFields(getGroupByFields(queryString));
		queryParam.setFields(getFields(queryString));
		queryParam.setLogicalOperators(getLogicalOperators(queryString));
		queryParam.setAggregateFunctions(getAggregateFunctions(queryString));
		queryParam.setRestrictions(getConditions(queryString));
		queryParam.setBaseQuery(getBaseQuery(queryString));
		
		return queryParam;
	}
	
	/*
	 * extract the name of the file from the query. File name can be found after the
	 * "from" clause.
	 */
	public String getFileName(String queryString) {
		String fileName = queryString.split("from")[1].trim().split(" ")[0].trim();
		return fileName;
	}
	
	/*
	 * extract the order by fields from the query string. Please note that we will
	 * need to extract the field(s) after "order by" clause in the query, if at all
	 * the order by clause exists. For eg: select city,winner,team1,team2 from
	 * data/ipl.csv order by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one order by fields.
	 */
	public List<String> getOrderByFields(String queryString){
		List<String> orderList = null;
		if (queryString.contains("order by")) {
			 String orderbyString = queryString.split("order by")[1].trim();
			 String[] orderByFields = orderbyString.split(",");
			 orderList = new ArrayList<String>();
			 for(int i = 0; i < orderByFields.length; i++) {
				 orderList.add(orderByFields[i]);
			 }
		}
		return orderList;
	}
	
	/*
	 * extract the group by fields from the query string. Please note that we will
	 * need to extract the field(s) after "group by" clause in the query, if at all
	 * the group by clause exists. For eg: select city,max(win_by_runs) from
	 * data/ipl.csv group by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one group by fields.
	 */
	public List<String> getGroupByFields(String queryString) {
		List<String> groupList = null;
		if (queryString.contains("group by")){
			 String groupbyString = queryString.split("group by")[1].split("order by")[0].trim();
			 String[] groupByFields = groupbyString.split(",");
			 groupList = new ArrayList<String>();
			 for(int i=0;i<groupByFields.length; i++) {
				 groupList.add(groupByFields[i]);
			 }
		}
		return groupList;
	}
	
	/*
	 * extract the selected fields from the query string. Please note that we will
	 * need to extract the field(s) after "select" clause followed by a space from
	 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
	 * query mentioned above, we need to extract "city" and "win_by_runs". Please
	 * note that we might have a field containing name "from_date" or "from_hrs".
	 * Hence, consider this while parsing.
	 */
	public List<String> getFields(String queryString) {
		String[] outputFields = null;
		outputFields = queryString.split("select")[1].trim().split("from")[0].split("[\\s,]+");
		List<String> fieldList = new ArrayList<String>();
			for(int i=0;i<outputFields.length;i++){
				fieldList.add(outputFields[i]);
			}
		return fieldList;
	}
	
	
	
	/*
	 * extract the conditions from the query string(if exists). for each condition,
	 * we need to capture the following: 
	 * 1. Name of field 
	 * 2. condition 
	 * 3. value
	 * 
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 * 
	 * here, for the first condition, "season>=2008" we need to capture: 
	 * 1. Name of field: season 
	 * 2. condition: >= 
	 * 3. value: 2008
	 * 
	 * the query might contain multiple conditions separated by OR/AND operators.
	 * Please consider this while parsing the conditions.
	 * 
	 */
	public List<Restriction> getConditions(String queryString) {
		String propertyName;
		String propertyValue;
		String conditionalOperator;
		String[] nameAndValue = null;
		List<Restriction> finalList = null;
		if(queryString.contains("where")) {
			String whereCondition =  queryString.split("order by")[0].trim()
					.split("group by")[0].trim()
					.split("where")[1].trim();
		    String[] conditions = whereCondition.split("\\s+and\\s+|\\s+or\\s+");
		    finalList = new ArrayList<Restriction>();
		    for(String condition : conditions){
		    	nameAndValue = condition.split("<=|>=|!=|=|<|>");
				propertyName = nameAndValue[0].trim();
				propertyValue = nameAndValue[1].replace("'", "").trim();
				conditionalOperator = condition.split(propertyName)[1].trim()
						.split(propertyValue)[0].replace("'", "").trim();
				Restriction restriction = new Restriction(propertyName,propertyValue,conditionalOperator);
				finalList.add(restriction);
			}
		}
		return finalList;
	}
	
	/*
	 * extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 * 
	 * the query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */
	public List<String> getLogicalOperators(String queryString) {
		List<String> tempList = null;
		queryString=queryString.toLowerCase();
		if(queryString.contains("where")) {
			String whereCondition = queryString.split("order by")[0].trim()
					.split("group by")[0].trim()
					.split("where")[1].trim();
			String[] conditions = whereCondition.split(" ");
			tempList = new ArrayList<String>();
			for(int i = 0; i < conditions.length; i++) {
				if("and".equals(conditions[i]) || "or".equals(conditions[i])) {
					tempList.add(conditions[i]);
				}
			}
		}
		return tempList;
	}
	
	/*
	 * extract the aggregate functions from the query. The presence of the aggregate
	 * functions can determined if we have either "min" or "max" or "sum" or "count"
	 * or "avg" followed by opening braces"(" after "select" clause in the query
	 * string. in case it is present, then we will have to extract the same. For
	 * each aggregate functions, we need to know the following: 
	 * 1. type of aggregate function(min/max/count/sum/avg) 
	 * 2. field on which the aggregate function is being applied
	 * 
	 * Please note that more than one aggregate function can be present in a query
	 * 
	 * 
	 */
	public List<AggregateFunction> getAggregateFunctions(String queryString) {
		String aggFunction;
		String aggField;
		List<AggregateFunction> finalList = null;
		if(queryString.contains("count(") 
				|| queryString.contains("sum(")
				|| queryString.contains("min(")
				|| queryString.contains("max(")
				|| queryString.contains("avg(")) {
			String[] aggregateFunctions = queryString.split("select")[1].trim()
					.split("from")[0].trim().split(",");
			finalList = new ArrayList<AggregateFunction>();
			for(int i = 0; i < aggregateFunctions.length; i++) {
				if(aggregateFunctions[i].contains("count(")
						|| aggregateFunctions[i].contains("sum(")
						|| aggregateFunctions[i].contains("min(")
						|| aggregateFunctions[i].contains("max(")
						|| aggregateFunctions[i].contains("avg(")) {
					aggFunction = aggregateFunctions[i].split("\\(")[0].trim();
					aggField = aggregateFunctions[i].split("\\(")[1].trim().split("\\)")[0].trim();
					AggregateFunction aggFunc = new AggregateFunction(aggFunction, aggField);
					finalList.add(aggFunc);
				}
			}
		}
		return finalList;
	}
	
	public String getBaseQuery(String queryString) {
		String baseQuery = null;
		if(queryString.toLowerCase().contains("where")) {
			baseQuery = queryString.toLowerCase().split("where")[0].trim();
		}else if(queryString.toLowerCase().contains("group by")){
			baseQuery = queryString.toLowerCase().split("group by")[0].trim();
		}else if(queryString.toLowerCase().contains("order by")){
			baseQuery = queryString.toLowerCase().split("order by")[0].trim();
		}else {
			baseQuery = queryString;
		}
		
		return baseQuery;
	}
}
