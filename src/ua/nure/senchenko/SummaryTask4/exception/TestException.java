package ua.nure.senchenko.SummaryTask4.exception;

/**
 * Exception that indicates failure of editing a test
 */
public class TestException extends AppException {

	private static final long serialVersionUID = 8731244760260151451L;

	public TestException(ErrorType errorType) {
		super(errorType);
	}

}
