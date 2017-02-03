package ua.nure.senchenko.SummaryTask4.exception;

/**
 * Exception that indicates registration failure
 */
public class RegistrationException extends AppException {

	private static final long serialVersionUID = -6582957236206848295L;

	public RegistrationException(ErrorType errorType) {
        super(errorType);
    }
}
