package ua.nure.senchenko.SummaryTask4.impl.comparator;

import java.io.Serializable;
import java.util.Comparator;

import ua.nure.senchenko.SummaryTask4.entity.Test;

/**
 * Custom comparator that compares tests based on name in alpha-numerical order
 */
public class NameComparator implements Comparator<Test>, Serializable {

	private static final long serialVersionUID = 7863602699344898696L;

	@Override
	public int compare(Test o1, Test o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
