package ua.nure.senchenko.SummaryTask4.impl.comparator;

import java.io.Serializable;
import java.util.Comparator;

import ua.nure.senchenko.SummaryTask4.entity.Test;

/**
 * Custom comparator that compares tests based on name in alpha-numerical order
 * and complexity level in descending order
 */
public class NameComplexityComparator implements Comparator<Test>, Serializable {

	private static final long serialVersionUID = -2508826037690231512L;

	@Override
	public int compare(Test o1, Test o2) {

		int value1 = o1.getName().compareTo(o2.getName());
		if (value1 == 0) {
			return o1.getComplexity().getLevel() > o2.getComplexity().getLevel() ? -1
					: o1.getComplexity().getLevel() < o2.getComplexity().getLevel() ? 1 : 0;
		}
		return value1;
	}

}
