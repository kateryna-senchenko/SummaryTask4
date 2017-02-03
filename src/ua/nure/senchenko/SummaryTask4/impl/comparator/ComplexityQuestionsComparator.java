package ua.nure.senchenko.SummaryTask4.impl.comparator;

import java.io.Serializable;
import java.util.Comparator;

import ua.nure.senchenko.SummaryTask4.entity.Test;

/**
 * Custom comparator that compares tests based on complexity level and number of questions
 * in descending order
 */
public class ComplexityQuestionsComparator implements Comparator<Test>, Serializable {

	private static final long serialVersionUID = 4971251827277140384L;

	@Override
	public int compare(Test o1, Test o2) {
		
		int value1 = o1.getComplexity().getLevel() > o2.getComplexity().getLevel() ? -1
		         : o1.getComplexity().getLevel() < o2.getComplexity().getLevel() ? 1
		         : 0;
		if (value1 == 0) {
			return o1.getQuestions().size() > o2.getQuestions().size() ? -1 
				: o1.getQuestions().size() < o2.getQuestions().size() ? 1 
				: 0;
		}
		return value1;
	}

}
