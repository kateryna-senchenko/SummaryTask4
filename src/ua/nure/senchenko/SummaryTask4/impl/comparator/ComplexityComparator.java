package ua.nure.senchenko.SummaryTask4.impl.comparator;

import java.io.Serializable;
import java.util.Comparator;

import ua.nure.senchenko.SummaryTask4.entity.Test;

/**
 * Custom comparator that compares tests based on complexity level in descending order
 */
public class ComplexityComparator implements Comparator<Test>, Serializable{

	private static final long serialVersionUID = 7819947973376208129L;

	@Override
	public int compare(Test o1, Test o2) {
		return o1.getComplexity().getLevel() > o2.getComplexity().getLevel() ? -1
		         : o1.getComplexity().getLevel() < o2.getComplexity().getLevel() ? 1
		         : 0;
	}

}
