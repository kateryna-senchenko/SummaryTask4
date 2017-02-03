package ua.nure.senchenko.SummaryTask4.impl.comparator;

import java.io.Serializable;
import java.util.Comparator;

import ua.nure.senchenko.SummaryTask4.entity.User;

public class AverageResultComparator implements Comparator<User>, Serializable{
	
	private static final long serialVersionUID = 8847575003658708626L;

	@Override
	public int compare(User o1, User o2) {
		return o1.getAverageResult() > o2.getAverageResult() ? -1
		         : o1.getAverageResult() < o2.getAverageResult() ? 1
		         : 0;
	}

}
