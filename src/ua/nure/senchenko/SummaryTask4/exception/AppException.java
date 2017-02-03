package ua.nure.senchenko.SummaryTask4.exception;

/**
 * Abstract class for app exceptions
 */
public abstract class AppException extends Exception {

	private static final long serialVersionUID = -235925750825203006L;
	
	private final ErrorType errorType;

	AppException(ErrorType errorType) {
		super(errorType.getMessage());
		this.errorType = errorType;
	}

	public ErrorType getErrorType() {
		return errorType;
	}
}
