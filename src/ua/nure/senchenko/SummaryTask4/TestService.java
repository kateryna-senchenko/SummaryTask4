package ua.nure.senchenko.SummaryTask4;

import java.util.List;

import ua.nure.senchenko.SummaryTask4.entity.*;
import ua.nure.senchenko.SummaryTask4.exception.AnswerException;
import ua.nure.senchenko.SummaryTask4.exception.GradingException;
import ua.nure.senchenko.SummaryTask4.exception.QuestionException;
import ua.nure.senchenko.SummaryTask4.exception.TestException;

/**
 * TestService public API
 */
public interface TestService {
	
	/**
	 * creates new test
	 * @param subject subject of the test
	 * @param name name of the new test
	 * @param duration duration of the test in minutes
	 * @param level complexity level
	 * @return Test entity
	 * @throws TestException in case of creation failure
	 */
	Test createTest(Subject subject, String name, int duration, Complexity level) throws TestException;

	/**
	 * creates new question
	 * @param testId id of the test to which question needs to be added
	 * @param questionText text of the question
	 * @return Question entity
	 * @throws QuestionException in case of creation failure
	 */
	Question createQuestion(Long testId, String questionText) throws QuestionException;
	
	/**
	 * creates new answer
	 * @param questionId id of the question to which answer needs to be added
	 * @param content text of the answer
	 * @param correct true if answer is correct
	 * @return Answer entity
	 * @throws AnswerException in case of creation failure
	 */
	Answer createAnswer(Long questionId, String content, Boolean correct) throws AnswerException;
	
//******************************************************************************************************//
	
	
	/**
	 * provides access to all subjects
	 * @return list of Subject entities
	 */
	List<Subject> getAllSubjects();
	
	/**
	 * provides access to all tests
	 * @return list of Test entities
	 */
	List<Test> getAllTests();
	
	/**
	 * provides access to all complexity levels
	 * @return list of Complexity entities
	 */
	List<Complexity> getAllComplexityLevels();
	
	/**
	 * finds subject by id
	 * @param id subject id
	 * @return Subject entity
	 */
	Subject findSubjectById(Integer id);
	
	/**
	 * finds test by id
	 * @param id test id
	 * @return Test entity
	 */
	Test findTestById(Long id);
	
	/**
	 * finds complexity level by id
	 * @param id complexity id
	 * @return Complexity entity
	 */
	Complexity findComplexityById(Integer id);

	/**
	 * finds question by id
	 * @param id question id
	 * @return Question entity
	 */
	Question findQuestionById(Long id);
	
	/**
	 * finds answer by id
	 * @param id answer id
	 * @return Answer entity
	 */
	Answer findAnswerById(Long id);
	
	/**
	 * finds tests by subject
	 * @param subject specified subject
	 * @return list of Test entities
	 */
	List<Test> findTestsBySubject(Subject subject);
	
	/**
	 * sorts list of tests by specified criteria
	 * @param tests list of tests that needs to be sorted 
	 * @param criteria list of criteria by which tests should be sorted
	 * @return sorted list of tests
	 */
	List<Test> sortBySpecifiedCriteria(List<Test> tests, List<String> criteria);
	
	/**
	 * finds out if user has already taken specified test
	 * @param userId user id
	 * @param testId test id
	 * @return true if test has been taken by user
	 */
	boolean isAlreadyTakenByUser(Long userId, Long testId);
	
	/**
	 * finds out if test can be taken
	 * @param testId test id
	 * @return true if test is valid
	 */
	boolean canBeTaken(Long testId);
	
//**********************************************************************************************************//
	
	/**
	 * grades taken test
	 * @param user user that has taken the test
	 * @param test taken test
	 * @param userAnswers list of chosen answers
	 * @return Result entity
	 * @throws GradingException in case of grading failure
	 */
	Result gradeTest(User user, Test test, List<String> userAnswers) throws GradingException;

	/**
	 * updates test parameters
	 * @param testId id of the test that needs to be updated
	 * @param subject subject of the test
	 * @param name name of the new test
	 * @param duration duration of the test in minutes
	 * @param level complexity level
	 * @throws TestException in case of failure
	 */
	void updateTest(Long testId, Subject subject, String name, int duration, Complexity level) throws TestException;

	/**
	 * updates question parameters
	 * @param testId test id
	 * @param questionId id of the question that needs to be updated
	 * @param questionText text of the question
	 * @throws QuestionException in case of failure
	 */
	void updateQuestion(Long testId, Long questionId, String questionText) throws QuestionException;

	/**
	 * updates answer parameters
	 * @param questionId question id
	 * @param answerId id of the answer that needs to be updated
	 * @param content text of the answer
	 * @param correct true if answer is correct
	 * @throws AnswerException in case of failure
	 */
	void updateAnswer(Long questionId, Long answerId, String content, Boolean correct) throws AnswerException;

	
//*************************************************************************************************************//	
	
	/**
	 * deletes test
	 * @param id test id
	 */
	void deleteTest(Long id);
	
	/**
	 * deletes question
	 * @param id question id
	 */
	void deleteQuestion(Long id);
	
	/**
	 * deletes answer 
	 * @param id answer id
	 */
	void deleteAnswer(Long id);


}
