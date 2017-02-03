package ua.nure.senchenko.SummaryTask4.impl.comparator;

import java.io.Serializable;
import java.util.Comparator;

import ua.nure.senchenko.SummaryTask4.entity.Test;

/**
 * Custom comparator that compares tests based on name in alpha-numerical order,
 * and number of question in descending order
 */
public class NameQuestionsComparator implements Comparator<Test>, Serializable {

	private static final long serialVersionUID = -1747661660087707897L;

	@Override
	public int compare(Test o1, Test o2) {

		int value1 = o1.getName().compareTo(o2.getName());
		if (value1 == 0) {
			return o1.getQuestions().size() > o2.getQuestions().size() ? -1
					: o1.getQuestions().size() < o2.getQuestions().size() ? 1 : 0;
		}
		return value1;
	}

}
