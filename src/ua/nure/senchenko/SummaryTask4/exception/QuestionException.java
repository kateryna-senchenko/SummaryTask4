package ua.nure.senchenko.SummaryTask4.exception;

/**
 * Exception that indicates failure of editing a question
 */
public class QuestionException extends AppException {

	private static final long serialVersionUID = -5577860033574568612L;

	public QuestionException(ErrorType errorType) {
		super(errorType);
	}

}
