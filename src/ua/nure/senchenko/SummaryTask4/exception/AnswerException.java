package ua.nure.senchenko.SummaryTask4.exception;

/**
 * Exception that indicates failure of editing an answer
 */
public class AnswerException extends AppException {

	private static final long serialVersionUID = -6459536509655485977L;

	public AnswerException(ErrorType errorType) {
		super(errorType);
	}

}
