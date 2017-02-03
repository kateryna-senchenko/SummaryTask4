package ua.nure.senchenko.SummaryTask4.exception;

/**
 * Enum of error types and corresponding messages
 */
public enum ErrorType {

	NULL_PARAMETERS("All fields are required"),
	
	NAME_IS_EMPTY("Name should not be empty"),
	DUPLICATE_EMAIL("Specified email is not available"), 
	INVALID_EMAIL("Specified email is not valid"),
	PASSWORD_IS_EMPTY("Password should not be empty"),
	PASSWORDS_DO_NOT_MATCH("Passwords do not match"),
	REGISTRATION_FAILED("User was not registered"),
	
	AUTHENTICATION_FAILED("Invalid email or password"),
	
	DUPLICATE_TEST_NAME("Specified test name is not available"),
	TEST_NAME_IS_EMPTY("Name should not be empty"),
	INVALID_DURATION("Enter duration in minutes"),
	TEST_CREATION_FAILED("Test was not created"),
	
	QUESTION_TEXT_IS_EMPTY("Question text should not be empty"),
	TEST_NOT_FOUND("Specified test was not found"),
	DUPLICATE_QUESTION_TEXT("Specified question text already exists"),
	QUESTION_CREATION_FAILED("Question was not created"),
	
	ANSWER_TEXT_IS_EMPTY("Answer content should not be empty"),
	QUESTION_NOT_FOUND("Specified question was not found"),
	DUPLICATE_ANSWER_TEXT("Answer with specified content already exists"),
	ANSWER_CREATION_FAILED("Answer was not created"),
	
	NO_QUESTIONS("Cannot grade test that has no questions"),
	NO_ANSWERS("Cannot grade test that contains questions with no answers"),
	FAILED_TO_GRADE("Test was not graded"),
	ANSWER_NOT_FOUND("Specified answer was not found");

	private final String message;

	ErrorType(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
