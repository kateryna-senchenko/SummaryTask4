package ua.nure.senchenko.SummaryTask4.exception;

/**
 * Exception that indicates failure of grading a test
 */
public class GradingException extends AppException {

	private static final long serialVersionUID = -2455200889106791202L;

	public GradingException(ErrorType errorType) {
		super(errorType);
	}

}
