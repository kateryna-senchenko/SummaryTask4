package ua.nure.senchenko.SummaryTask4.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.db.DBManager;
import ua.nure.senchenko.SummaryTask4.entity.*;
import static ua.nure.senchenko.SummaryTask4.exception.ErrorType.*;

import ua.nure.senchenko.SummaryTask4.exception.AnswerException;
import ua.nure.senchenko.SummaryTask4.exception.GradingException;
import ua.nure.senchenko.SummaryTask4.exception.QuestionException;
import ua.nure.senchenko.SummaryTask4.exception.TestException;
import ua.nure.senchenko.SummaryTask4.impl.comparator.*;

public class TestServiceImpl implements TestService {

	private static final Logger LOG = Logger.getLogger(TestServiceImpl.class);

	private static TestServiceImpl testService;
	private static DBManager db_manager;

	public TestServiceImpl(Mode mode) {
		db_manager = DBManager.getInstance(mode);
	}

	public static synchronized TestService getInstance(Mode mode) {
		if (testService == null) {
			testService = new TestServiceImpl(mode);
		}
		return testService;
	}

	@Override
	public Test createTest(Subject subject, String name, int duration, Complexity level) throws TestException {

		validateTestCreationParameters(subject, name, duration, level);

		final Test newTest = new Test();

		newTest.setName(name.trim());
		newTest.setDuration(duration);
		newTest.setSubject(subject);
		newTest.setComplexity(level);
		newTest.setQuestions(new ArrayList<>());

		final Long newTestId = db_manager.insertTest(newTest);

		if (newTestId == null) {
			LOG.error("Test " + name + " was not inserted into the db");
			throw new TestException(TEST_CREATION_FAILED);
		}

		newTest.setId(newTestId);

		if (LOG.isInfoEnabled()) {
			LOG.info("Created test " + name);
		}

		return newTest;

	}

	@Override
	public Question createQuestion(Long testId, String questionText) throws QuestionException {

		validateQuestionCreationParameters(testId, questionText);

		final Question question = new Question();

		question.setQuestionText(questionText.trim());
		question.setAnswers(new ArrayList<>());

		final Long questionId = db_manager.insertQuestion(question, testId);

		if (questionId == null) {
			LOG.error("Question was not inserted into the db");
			throw new QuestionException(QUESTION_CREATION_FAILED);
		}

		question.setId(questionId);

		if (LOG.isInfoEnabled()) {
			LOG.info("Created question " + questionText);
		}

		return question;
	}

	@Override
	public Answer createAnswer(Long questionId, String content, Boolean correct) throws AnswerException {

		validateAnswerCreationParameters(questionId, content, correct);
		
		final Answer answer = new Answer();

		answer.setContent(content.trim());
		answer.setCorrect(correct);

		final Long answerId = db_manager.insertAnswer(answer, questionId);

		if (answerId == null) {
			LOG.error("Answer was not inserted into the db");
			throw new AnswerException(ANSWER_CREATION_FAILED);
		}

		answer.setId(answerId);

		if (LOG.isInfoEnabled()) {
			LOG.info("Created answer " + content);
		}

		return answer;

	}

	// *********************************************************************************************************//

	@Override
	public List<Subject> getAllSubjects() {
		return db_manager.getAllSubjects();
	}

	@Override
	public List<Test> getAllTests() {
		return db_manager.getAllTests();
	}

	@Override
	public List<Complexity> getAllComplexityLevels() {
		return db_manager.getAllComplxityLevels();
	}

	@Override
	public Subject findSubjectById(Integer id) {

		if (id == null) {
			LOG.error("Null parameters");
			throw new IllegalArgumentException("Parameters should not be null");
		}

		return db_manager.findSubjectById(id);
	}

	@Override
	public Test findTestById(Long id) {

		if (id == null) {
			LOG.error("Null parameters");
			throw new IllegalArgumentException("Parameters should not be null");
		}

		return db_manager.findTestById(id);
	}

	@Override
	public Complexity findComplexityById(Integer id) {

		if (id == null) {
			LOG.error("Null parameters");
			throw new IllegalArgumentException("Parameters should not be null");
		}

		return db_manager.findComplexityById(id);
	}

	@Override
	public Question findQuestionById(Long id) {

		if (id == null) {
			LOG.error("Null parameters");
			throw new IllegalArgumentException("Parameters should not be null");
		}

		return db_manager.findQuestionById(id);
	}

	@Override
	public Answer findAnswerById(Long id) {

		if (id == null) {
			LOG.error("Null parameters");
			throw new IllegalArgumentException("Parameters should not be null");
		}

		return db_manager.findAnswerById(id);
	}

	@Override
	public List<Test> findTestsBySubject(Subject subject) {

		if (subject == null) {
			LOG.error("Null parameters");
			throw new IllegalArgumentException("Parameters should not be null");
		}

		return db_manager.findTestsBySubject(subject);
	}

	@Override
	public List<Test> sortBySpecifiedCriteria(List<Test> tests, List<String> criteria) {

		if (tests == null) {
			LOG.error("Null parameters");
			throw new IllegalArgumentException("Parameters should not be null");
		}

		if (criteria == null) {
			LOG.debug("No sorting criteria were chosen");
			return tests;
		}

		final List<SortCriteria> sortCriteria = new ArrayList<>();

		for (String checked : criteria) {
			sortCriteria.add(SortCriteria.valueOf(checked));
		}

		if (sortCriteria.contains(SortCriteria.NAME)) {
			if (sortCriteria.contains(SortCriteria.COMPLEXITY)) {
				if (sortCriteria.contains(SortCriteria.NUMBER_OF_QUESTIONS)) {
					Collections.sort(tests, new NameComplexityQuestionsComparator());
					LOG.debug("Sorted by name, complexity and number of questions");
				} else {
					Collections.sort(tests, new NameQuestionsComparator());
					LOG.debug("Sorted by name and number of questions");
				}
			} else if (sortCriteria.contains(SortCriteria.NUMBER_OF_QUESTIONS)) {
				Collections.sort(tests, new NameQuestionsComparator());
				LOG.debug("Sorted by name and number of questions");
			} else {
				Collections.sort(tests, new NameComparator());
				LOG.debug("Sorted by name");
			}
		} else if (sortCriteria.contains(SortCriteria.COMPLEXITY)) {
			if (sortCriteria.contains(SortCriteria.NUMBER_OF_QUESTIONS)) {
				Collections.sort(tests, new ComplexityQuestionsComparator());
				LOG.debug("Sorted by complexity and number of questions");
			} else {
				Collections.sort(tests, new ComplexityComparator());
				LOG.debug("Sorted by complexity");
			}
		} else if (sortCriteria.contains(SortCriteria.NUMBER_OF_QUESTIONS)) {
			Collections.sort(tests, new QuestionsComparator());
			LOG.debug("Sorted by number of questions");
		}

		return tests;
	}

	@Override
	public boolean isAlreadyTakenByUser(Long userId, Long testId) {

		if (userId == null || testId == null) {
			LOG.error("Null parameters");
			throw new IllegalArgumentException("Parameters should not be null");
		}

		final Result result = db_manager.findResultByUserIdAndTestId(userId, testId);

		if (result != null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canBeTaken(Long testId) {

		if (testId == null) {
			LOG.error("Null parameters");
			throw new IllegalArgumentException("Parameters should not be null");
		}

		final Test test = db_manager.findTestById(testId);

		if (test == null) {
			LOG.error("Test was not found");
			return false;
		}

		final List<Question> questions = test.getQuestions();

		if (questions == null || questions.size() == 0) {
			LOG.error("Test " + test.getId() + " has no questions");
			return false;
		}

		for (Question question : questions) {

			final List<Answer> answers = question.getAnswers();

			if (answers == null || answers.size() == 0) {
				LOG.error("Question " + question.getId() + " from test " + test.getId() + "has no answers");
				return false;
			}
		}

		return true;
	}

	// *************************************************************************************************************//

	@Override
	public Result gradeTest(User user, Test test, List<String> userAnswers) throws GradingException {

		if (test == null || user == null) {
			LOG.error("Null parameters");
			throw new IllegalArgumentException("Parameters should not be null");
		}

		if (userAnswers == null) {
			LOG.debug("No answers were chosen");
			userAnswers = new ArrayList<>();
		}

		final List<Question> questions = test.getQuestions();

		if (questions == null || questions.size() == 0) {
			LOG.error("Test " + test.getId() + " has no questions");
			throw new GradingException(NO_QUESTIONS);
		}

		final int[] results = new int[questions.size()];

		boolean correct = false;

		for (int i = 0; i < questions.size(); i++) {

			final List<Answer> answers = questions.get(i).getAnswers();

			if (answers == null || answers.size() == 0) {
				LOG.error("Question " + questions.get(i).getId() + " from test " + test.getId() + "has no answers");
				throw new GradingException(NO_ANSWERS);
			}

			for (Answer answer : answers) {
				String answerId = String.valueOf(answer.getId());

				if ((answer.isCorrect() && !userAnswers.contains(answerId))
						|| (!answer.isCorrect() && userAnswers.contains(answerId))) {
					correct = false;
					break;
				} else {
					correct = true;
				}
			}

			if (correct) {
				results[i] = 1;
			}
		}

		double sum = 0;

		for (int i = 0; i < results.length; i++) {
			sum += results[i];
		}

		final double outcome = Math.round(((sum / results.length) * 100.0) * 100.0) / 100.0;

		final Long resultId = db_manager.insertResult(user, test, outcome);

		if (resultId == null) {
			LOG.error("Result was not inserted into the db");
			throw new GradingException(FAILED_TO_GRADE);
		}

		final Result result = new Result();
		result.setId(resultId);
		result.setTest(test);
		result.setResult(outcome);

		user.getResults().add(result);
		
		double average = calculateAverage(user.getResults());
		user.setAverageResult(average);
		db_manager.updateAverage(user, average);

		if (LOG.isInfoEnabled()) {
			LOG.info("User " + user.getEmail() + " has completed the test " + test.getId() + " with result " + outcome);
		}

		return result;
	}

	

	@Override
	public void updateTest(Long testId, Subject subject, String name, int duration, Complexity level)
			throws TestException {


		validateTestEditingParameters(testId, subject, name, duration, level);

		final Test test = db_manager.findTestById(testId);

		if (test == null) {
			LOG.error("Test with id " + testId + "was not found");
			throw new TestException(TEST_NOT_FOUND);
		}

		test.setSubject(subject);
		test.setName(name.trim());
		test.setDuration(duration);
		test.setComplexity(level);

		db_manager.updateTest(test);

		if (LOG.isInfoEnabled()) {
			LOG.info("Updated test " + testId);
		}
	}

	@Override
	public void updateQuestion(Long testId, Long questionId, String questionText) throws QuestionException {


		validateQuestionEditingParameters(testId, questionId, questionText);
		
		final Question question = db_manager.findQuestionById(questionId);

		if (question == null) {
			LOG.error("Question with id " + questionId + " was not found");
			throw new QuestionException(QUESTION_NOT_FOUND);

		}
		
		question.setQuestionText(questionText.trim());

		db_manager.updateQuestion(question);

		if (LOG.isInfoEnabled()) {
			LOG.info("Updated question " + questionId);
		}
	}

	@Override
	public void updateAnswer(Long questionId, Long answerId, String content, Boolean correct) throws AnswerException {

		validateAnswerEditingParameters(questionId, answerId, content, correct);

		final Answer answer = db_manager.findAnswerById(answerId);

		if (answer == null) {
			LOG.error("Answer with id " + answerId + " was not found");
			throw new AnswerException(ANSWER_NOT_FOUND);

		}

		answer.setContent(content.trim());
		answer.setCorrect(correct);

		db_manager.updateAnswer(answer);

		if (LOG.isInfoEnabled()) {
			LOG.info("Updated answer " + answerId);
		}
	}

	// *************************************************************************************************************//

	@Override
	public void deleteTest(Long id) {

		if (id == null) {
			LOG.error("Null parameters");
			throw new IllegalArgumentException("Parameters should not be null");
		}

		db_manager.deleteTest(id);

		if (LOG.isInfoEnabled()) {
			LOG.info("Deleted test " + id);
		}

	}

	@Override
	public void deleteQuestion(Long id) {

		if (id == null) {
			LOG.error("Null parametersl");
			throw new IllegalArgumentException("Parameters should not be null");
		}

		db_manager.deleteQuestion(id);

		if (LOG.isInfoEnabled()) {
			LOG.info("Deleted question " + id);
		}

	}

	@Override
	public void deleteAnswer(Long id) {

		if (id == null) {
			LOG.error("Null parameters");
			throw new IllegalArgumentException("Parameters should not be null");
		}

		db_manager.deleteAnswer(id);

		if (LOG.isInfoEnabled()) {
			LOG.info("Deleted answer " + id);
		}
	}

	// *****************************************************************************************************************//

	private void validateTestCreationParameters(Subject subject, String name, int duration, Complexity level)
			throws TestException {

		if (subject == null || name == null || duration == 0 || level == null) {
			LOG.error("Null parameters");
			throw new TestException(NULL_PARAMETERS);
		}

		final String testName = name.trim();

		if (testName.isEmpty()) {
			LOG.error("Empty name input");
			throw new TestException(TEST_NAME_IS_EMPTY);
		}

		final List<Test> tests = db_manager.findTestsByName(testName);

		for (Test test : tests) {
			if (test.getSubject().equals(subject)) {
				LOG.error("Test " + testName + " already exists");
				throw new TestException(DUPLICATE_TEST_NAME);
			}
		}

		if (duration < 1) {
			LOG.error("Invalid test duration input");
			throw new TestException(INVALID_DURATION);
		}

	}
	
	private void validateTestEditingParameters(Long testId, Subject subject, String name, int duration, Complexity level)
			throws TestException {

		if (testId == null || subject == null || name == null || duration == 0 || level == null) {
			LOG.error("Null parameters");
			throw new TestException(NULL_PARAMETERS);
		}

		final String testName = name.trim();

		if (testName.isEmpty()) {
			LOG.error("Empty name input");
			throw new TestException(TEST_NAME_IS_EMPTY);
		}

		final List<Test> tests = db_manager.findTestsByName(testName);

		for (Test test : tests) {
			if (test.getSubject().equals(subject) && test.getId() != testId) {
				LOG.error("Test " + testName + " already exists");
				throw new TestException(DUPLICATE_TEST_NAME);
			}
		}

		if (duration < 1) {
			LOG.error("Invalid test duration input");
			throw new TestException(INVALID_DURATION);
		}

	}
	
	private void validateQuestionCreationParameters(Long testId, String questionText) throws QuestionException {
		
		if (testId == null || questionText == null) {
			LOG.error("Null parameters");
			throw new QuestionException(NULL_PARAMETERS);
		}

		final String text = questionText.trim();

		if (text.isEmpty()) {
			LOG.error("Empty question text input");
			throw new QuestionException(QUESTION_TEXT_IS_EMPTY);
		}

		final Test test = db_manager.findTestById(testId);

		if (test == null) {
			LOG.error("Test with id " + testId + " was not found");
			throw new QuestionException(TEST_NOT_FOUND);

		}

		for (Question question : test.getQuestions()) {
			if (text.equals(question.getQuestionText())) {
				LOG.error("Question " + text + " already exists");
				throw new QuestionException(DUPLICATE_QUESTION_TEXT);
			}
		}
		
	}
	
	private void validateQuestionEditingParameters(Long testId, Long questionId, String questionText) throws QuestionException {
		
		if (testId == null || questionId == null || questionText == null) {
			LOG.error("Null parameters");
			throw new QuestionException(NULL_PARAMETERS);
		}

		final String text = questionText.trim();

		if (text.isEmpty()) {
			LOG.error("Empty question text input");
			throw new QuestionException(QUESTION_TEXT_IS_EMPTY);
		}

		final Test test = db_manager.findTestById(testId);

		if (test == null) {
			LOG.error("Test with id " + testId + " was not found");
			throw new QuestionException(TEST_NOT_FOUND);

		}

		for (Question question : test.getQuestions()) {
			if (text.equals(question.getQuestionText()) && question.getId() != questionId) {
				LOG.error("Question " + text + " already exists");
				throw new QuestionException(DUPLICATE_QUESTION_TEXT);
			}
		}
		
	}
	
	private void validateAnswerCreationParameters(Long questionId, String content, Boolean correct) throws AnswerException {

		if (questionId == null || content == null || correct == null) {
			LOG.error("Failed answer creation: null parameters");
			throw new AnswerException(NULL_PARAMETERS);
		}

		final String contentText = content.trim();

		if (contentText.isEmpty()) {
			LOG.error("Failed to create answer: empty answer content input");
			throw new AnswerException(ANSWER_TEXT_IS_EMPTY);
		}

		final Question question = db_manager.findQuestionById(questionId);

		if (question == null) {
			LOG.error("Answer creation failed: question with id " + questionId + " was not found");
			throw new AnswerException(QUESTION_NOT_FOUND);

		}

		for (Answer answer : question.getAnswers()) {
			if (contentText.equals(answer.getContent())) {
				LOG.error("Answer creation failed: answer " + contentText + " already exists");
				throw new AnswerException(DUPLICATE_ANSWER_TEXT);
			}
		}
	}
	
	private void validateAnswerEditingParameters (Long questionId, Long answerId, String content, Boolean correct) throws AnswerException {

		if (questionId == null || answerId == null || content == null || correct == null) {
			LOG.error("Null parameters");
			throw new AnswerException(NULL_PARAMETERS);
		}

		final String contentText = content.trim();

		if (contentText.isEmpty()) {
			LOG.error("Empty answer content input");
			throw new AnswerException(ANSWER_TEXT_IS_EMPTY);
		}
		
		final Question question = db_manager.findQuestionById(questionId);

		if (question == null) {
			LOG.error("Question with id " + questionId + " was not found");
			throw new AnswerException(QUESTION_NOT_FOUND);

		}

		for (Answer answer : question.getAnswers()) {
			if (contentText.equals(answer.getContent()) && answer.getId() != answerId) {
				LOG.error("Answer " + contentText + " already exists");
				throw new AnswerException(DUPLICATE_ANSWER_TEXT);
			}
		}
		
	}
	
	private double calculateAverage(List<Result> results) {

		if(results == null || results.isEmpty()){
			return 0;
		}
		double sum = 0;
		
		for(Result result: results){
			sum += result.getResult();
		}
		
		return sum/results.size();
		
	}

}
