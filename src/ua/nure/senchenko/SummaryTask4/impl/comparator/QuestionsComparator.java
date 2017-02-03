package ua.nure.senchenko.SummaryTask4.impl.comparator;

import java.io.Serializable;
import java.util.Comparator;

import ua.nure.senchenko.SummaryTask4.entity.Test;

/**
 * Custom comparator that compares tests based on number of question in descending order
 */
public class QuestionsComparator implements Comparator<Test>, Serializable {

	private static final long serialVersionUID = 2211228217827964764L;

	@Override
	public int compare(Test o1, Test o2) {
		return o1.getQuestions().size() > o2.getQuestions().size() ? -1
				: o1.getQuestions().size() < o2.getQuestions().size() ? 1 : 0;
	}

}
