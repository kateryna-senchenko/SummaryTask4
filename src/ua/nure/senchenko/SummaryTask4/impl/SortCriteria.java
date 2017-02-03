package ua.nure.senchenko.SummaryTask4.impl;

/**
 * enum that contains sorting criteria
 */
public enum SortCriteria {
	
	NAME("name"), COMPLEXITY("complexity"), NUMBER_OF_QUESTIONS("number of questions");
	
	private final String criteria;
	
	SortCriteria(String criteria){
		this.criteria = criteria;
	}
	
	public String getCriteria(){
		return criteria;
	}

}
