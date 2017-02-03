package ua.nure.senchenko.SummaryTask4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ua.nure.senchenko.SummaryTask4.exception.ErrorType.*;

import org.junit.After;
import org.junit.Before;

import ua.nure.senchenko.SummaryTask4.db.DBManager;
import ua.nure.senchenko.SummaryTask4.entity.*;
import ua.nure.senchenko.SummaryTask4.exception.AnswerException;
import ua.nure.senchenko.SummaryTask4.exception.QuestionException;
import ua.nure.senchenko.SummaryTask4.exception.TestException;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;

public class TestServiceIntegrationTest {
	
	private final TestService testService = TestServiceImpl.getInstance(Mode.TEST);
	private final DBManager db_manager = DBManager.getInstance(Mode.TEST);
	
	private final Subject subject = new Subject();
	private final String name = "Introduction";
	private final int duration = 23;
	private final Complexity level = new Complexity();
	private final String questionText = "What is it?";
	private final String content = "Chocolate";
	private final boolean correct = true;

	
	@Before
	public void setParameters(){
		subject.setId(1);
		level.setId(1);
	}
	
	@After
	public void deleteAllTests() {
		db_manager.deleteAllTests();
	}
	
	@org.junit.Test
	public void createNewTest(){
		
		Test test = null;
		
		try {
			test = testService.createTest(subject, name, duration, level);
		} catch (TestException e) {
			fail("Failed to create new test");
		}

		assertEquals("New test has different name", name, test.getName());
		assertEquals("New test has different subject", subject, test.getSubject());
		assertEquals("New test has different duration", duration, test.getDuration());
		assertEquals("New test has different complexity", level, test.getComplexity());

	}
	
	@org.junit.Test
	public void failCreateTestWithDuplicateName(){
		
		try {
			testService.createTest(subject, name, duration, level);
		} catch (TestException e) {
			fail("Failed to create new test");
		}
		
		try {
			testService.createTest(subject, name, duration, level);
			fail("Expected TestException was not thrown");
		} catch (TestException e) {
			assertEquals("Wrong error type", DUPLICATE_TEST_NAME, e.getErrorType());
		}

	}
	
	@org.junit.Test
	public void failCreateTestWithNullParameters(){
		
		try {
			testService.createTest(null, name, duration, level);
			fail("Expected TestException was not thrown");
		} catch (TestException e) {
			assertEquals("Wrong error type", NULL_PARAMETERS, e.getErrorType());
		}

	}
	
	@org.junit.Test
	public void failCreateTestWithEmptyName(){
		
		final String emptyName = " ";
		
		try {
			testService.createTest(subject, emptyName, duration, level);
			fail("Expected TestException was not thrown");
		} catch (TestException e) {
			assertEquals("Wrong error type", TEST_NAME_IS_EMPTY, e.getErrorType());
		}

	}
	
	@org.junit.Test
	public void failCreateTestWithInvalidDuration(){
		
		final int invalidDuration = -2;
		
		try {
			testService.createTest(subject, name, invalidDuration, level);
			fail("Expected TestException was not thrown");
		} catch (TestException e) {
			assertEquals("Wrong error type", INVALID_DURATION, e.getErrorType());
		}

	}
	
	@org.junit.Test
	public void createNewQuestion() throws TestException{
		
		Test test = testService.createTest(subject, name, duration, level);
		
		Question question = null;
		try {
			question = testService.createQuestion(test.getId(), questionText);
		} catch (QuestionException e) {
			fail("Failed to create new question");
		}

		assertEquals("New question has different text", questionText, question.getQuestionText());

	}
	
	@org.junit.Test
	public void failCreateQuestionWithNullParameters() throws TestException{

		Test test = testService.createTest(subject, name, duration, level);
		
		try {
			testService.createQuestion(test.getId(), null);
			fail("Expected QuestionException was not thrown");
		} catch (QuestionException e) {
			assertEquals("Wrong error type", NULL_PARAMETERS, e.getErrorType());
		}

	}
	
	@org.junit.Test
	public void failCreateQuestionWithEmptyText() throws TestException{

		Test test = testService.createTest(subject, name, duration, level);
		
		final String emptyText = " ";
		try {
			testService.createQuestion(test.getId(), emptyText);
			fail("Expected QuestionException was not thrown");
		} catch (QuestionException e) {
			assertEquals("Wrong error type", QUESTION_TEXT_IS_EMPTY, e.getErrorType());
		}

	}
	
	@org.junit.Test
	public void failCreateQuestionIfNoTestFound(){

		final Long someNumber = 1l;
		try {
			testService.createQuestion(someNumber, questionText);
			fail("Expected QuestionException was not thrown");
		} catch (QuestionException e) {
			assertEquals("Wrong error type", TEST_NOT_FOUND, e.getErrorType());
		}

	}
	
	@org.junit.Test
	public void failCreateQuestionWithDuplicateText() throws TestException{

		Test test = testService.createTest(subject, name, duration, level);
		
		try {
			testService.createQuestion(test.getId(), questionText);
		} catch (QuestionException e) {
			fail("Failed to create new question");
		}
		
		try {
			testService.createQuestion(test.getId(), questionText);
			fail("Expected QuestionException was not thrown");
		} catch (QuestionException e) {
			assertEquals("Wrong error type", DUPLICATE_QUESTION_TEXT, e.getErrorType());
		}

	}
	
	@org.junit.Test
	public void createNewAnswer() throws TestException, QuestionException{
		
		Test test = testService.createTest(subject, name, duration, level);
		Question question = testService.createQuestion(test.getId(), questionText);
		
		Answer answer = null;
		try {
			answer = testService.createAnswer(question.getId(), content, correct);
		} catch (AnswerException e) {
			fail("Failed to create new answer");
		}

		assertEquals("New answer has different content", content, answer.getContent());
		assertEquals("New answer has different correct value", correct, answer.isCorrect());

	}
	
	@org.junit.Test
	public void failCreateAnswerWithNullParameters() throws QuestionException, TestException{

		Test test = testService.createTest(subject, name, duration, level);
		Question question = testService.createQuestion(test.getId(), questionText);
		
		try {
			testService.createAnswer(question.getId(), null, correct);
			fail("Expected AnswerException was not thrown");
		} catch (AnswerException e) {
			assertEquals("Wrong error type", NULL_PARAMETERS, e.getErrorType());
		}

	}
	
	@org.junit.Test
	public void failCreateAnswerWithEmptyContent() throws TestException, QuestionException{

		Test test = testService.createTest(subject, name, duration, level);
		Question question = testService.createQuestion(test.getId(), questionText);
		
		final String emptyContent = "";
		try {
			testService.createAnswer(question.getId(), emptyContent, correct);
			fail("Expected AnswerException was not thrown");
		} catch (AnswerException e) {
			assertEquals("Wrong error type", ANSWER_TEXT_IS_EMPTY, e.getErrorType());
		}

	}
	
	@org.junit.Test
	public void failCreateAnswerIfNoQuestionFound(){

		final Long someNumber = 1l;
		try {
			testService.createAnswer(someNumber, content, correct);
			fail("Expected AnswerException was not thrown");
		} catch (AnswerException e) {
			assertEquals("Wrong error type", QUESTION_NOT_FOUND, e.getErrorType());
		}

	}
	
	@org.junit.Test
	public void failCreateAnswerWithDuplicateContent() throws TestException, QuestionException{

		Test test = testService.createTest(subject, name, duration, level);
		Question question = testService.createQuestion(test.getId(), questionText);
		
		try {
			testService.createAnswer(question.getId(), content, correct);
		} catch (AnswerException e) {
			fail("Failed to create new answer");
		}
		
		try {
			testService.createAnswer(question.getId(), content, correct);
			fail("Expected AnswerException was not thrown");
		} catch (AnswerException e) {
			assertEquals("Wrong error type", DUPLICATE_ANSWER_TEXT, e.getErrorType());
		}

	}
	
}
