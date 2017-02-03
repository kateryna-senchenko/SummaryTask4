package ua.nure.senchenko.SummaryTask4.exception;

/**
 * Exception that indicates authentication failure
 */
public class AuthenticationException extends AppException {

	private static final long serialVersionUID = -3010667148780733191L;

	public AuthenticationException(ErrorType errorType) {
        super(errorType);
    }
}
