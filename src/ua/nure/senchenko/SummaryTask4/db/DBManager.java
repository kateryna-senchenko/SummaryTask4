package ua.nure.senchenko.SummaryTask4.db;

import static ua.nure.senchenko.SummaryTask4.db.SQLQueries.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.nure.senchenko.SummaryTask4.entity.Answer;
import ua.nure.senchenko.SummaryTask4.entity.Complexity;
import ua.nure.senchenko.SummaryTask4.entity.Question;
import ua.nure.senchenko.SummaryTask4.entity.Result;
import ua.nure.senchenko.SummaryTask4.entity.Role;
import ua.nure.senchenko.SummaryTask4.entity.Status;
import ua.nure.senchenko.SummaryTask4.entity.Subject;
import ua.nure.senchenko.SummaryTask4.entity.Test;
import ua.nure.senchenko.SummaryTask4.entity.User;
import ua.nure.senchenko.SummaryTask4.impl.Mode;

public class DBManager {

	private static final Logger LOG = Logger.getLogger(DBManager.class);

	private static final String URL = "jdbc:mysql://localhost/";
	private static final String USER = "root";
	private static final String PASSWORD = "123";
	private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private static String dbName;

	private static DBManager instance = null;

	public static synchronized DBManager getInstance(Mode mode) {

		dbName = mode.getDbName();
		
		if (instance == null) {

			try {
				Class.forName(DRIVER_CLASS);
				instance = new DBManager();
			} catch (ClassNotFoundException e) {
				LOG.error("Driver class " + DRIVER_CLASS + " was not found");
			}

		}

		return instance;
	}

	// ******************************************************************************************//

	public Long insertUser(User user) {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Long id = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setInt(4, user.getRole().getId());
			pstmt.setInt(5, user.getStatus().getId());
			pstmt.setDouble(6, user.getAverageResult());

			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				id = (long) rs.getInt(1);
			}

		} catch (SQLException ex) {
			LOG.error("User " + user.getEmail() + " was not inserted");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}

		return id;
	}

	public Long insertResult(User user, Test test, double result) {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Long id = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_INSERT_RESULT, Statement.RETURN_GENERATED_KEYS);
			pstmt.setLong(1, user.getId());
			pstmt.setLong(2, test.getId());
			pstmt.setDouble(3, result);

			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
			}

		} catch (SQLException ex) {
			LOG.error("Result was not inserted");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}

		return id;
	}

	public Long insertTest(Test test) {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Long id = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_INSERT_TEST, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, test.getName());
			pstmt.setInt(2, test.getDuration());
			pstmt.setInt(3, test.getSubject().getId());
			pstmt.setInt(4, test.getComplexity().getId());

			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				id = (long) rs.getInt(1);
			}

		} catch (SQLException ex) {
			LOG.error("Test " + test.getName() + " was not inserted");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}

		return id;
	}

	public Long insertQuestion(Question question, long testId) {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Long id = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_INSERT_QUESTION, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, question.getQuestionText());
			pstmt.setLong(2, testId);

			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				id = (long) rs.getInt(1);
			}

		} catch (SQLException ex) {
			LOG.error("Question " + question.getQuestionText() + " was not inserted");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}

		return id;
	}

	public Long insertAnswer(Answer answer, Long questionId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Long id = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_INSERT_ANSWER, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, answer.getContent());
			pstmt.setBoolean(2, answer.isCorrect());
			pstmt.setLong(3, questionId);

			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				id = (long) rs.getInt(1);
			}

		} catch (SQLException ex) {
			LOG.error("Answer " + answer.getContent() + " was not inserted");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}

		return id;
	}

	// *****************************************************************************************//

	public List<User> getAllUsers() {

		List<User> users = new ArrayList<>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			stmt = (Statement) connection.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_FROM_USERS);

			while (rs.next()) {
				users.add(extractUser(rs));
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(stmt);
			close(rs);
			close(connection);
		}
		return users;
	}

	public List<Subject> getAllSubjects() {
		List<Subject> subjects = new ArrayList<>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			stmt = (Statement) connection.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_FROM_SUBJECTS);

			while (rs.next()) {
				subjects.add(extractSubject(rs));
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(stmt);
			close(rs);
			close(connection);
		}
		return subjects;
	}

	public List<Test> getAllTests() {

		List<Test> tests = new ArrayList<>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			stmt = (Statement) connection.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_FROM_TESTS);

			while (rs.next()) {
				tests.add(extractTest(rs));
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(stmt);
			close(rs);
			close(connection);
		}
		return tests;
	}

	public List<Complexity> getAllComplxityLevels() {

		List<Complexity> levels = new ArrayList<>();
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			stmt = (Statement) connection.createStatement();
			rs = stmt.executeQuery(SQL_SELECT_FROM_COMPLEXITY_LEVELS);

			while (rs.next()) {
				levels.add(extractComplexity(rs));
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(stmt);
			close(rs);
			close(connection);
		}
		return levels;
	}

	public List<User> getAllClients() {

		List<User> users = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_FROM_USERS_BY_ROLE_ID);
			pstmt.setInt(1, 2);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				users.add(extractUser(rs));
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}
		return users;
	}
	
	public User findUserById(long id) {

		Connection connection = null;
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_FROM_USERS_BY_ID);
			pstmt.setLong(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}

		return user;
	}

	public Test findTestById(long id) {

		Connection connection = null;
		Test test = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_FROM_TESTS_BY_ID);
			pstmt.setLong(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				test = extractTest(rs);
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}

		return test;
	}

	public Subject findSubjectById(int id) {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Subject subject = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_FROM_SUBJECTS_BY_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				subject = extractSubject(rs);
			}

		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}
		return subject;
	}

	public Role findRoleById(int id) {

		Connection connection = null;
		Role role = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_FROM_ROLES_BY_ID);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				role = extractRole(rs);
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}

		return role;
	}

	public Complexity findComplexityById(int id) {

		Connection connection = null;
		Complexity complexity = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_FROM_COMPLEXITY_LEVELS_BY_ID);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				complexity = extractComplexity(rs);
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}

		return complexity;
	}

	public Status findStatusById(int id) {

		Connection connection = null;
		Status status = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_FROM_STATUSES_BY_ID);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				status = extractStatus(rs);
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}

		return status;
	}

	
	public Question findQuestionById(Long questionId) {

		Connection connection = null;
		Question question = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_FROM_QUESTIONS_BY_ID);
			pstmt.setLong(1, questionId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				question = extractQuestion(rs);
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}

		return question;
	}

	public Answer findAnswerById(Long answerId) {

		Connection connection = null;
		Answer answer = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_FROM_ANSWERS_BY_ID);
			pstmt.setLong(1, answerId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				answer = extractAnswer(rs);
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}

		return answer;
	}
	
	public List<Test> findTestsBySubject(Subject subject) {

		List<Test> tests = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();

			pstmt = connection.prepareStatement(SQL_SELECT_FROM_TESTS_BY_SUBJECT);
			pstmt.setInt(1, subject.getId());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tests.add(extractTest(rs));
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}
		return tests;
	}

	
	public User findUserByEmail(String email) {

		Connection connection = null;
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_FROM_USERS_BY_EMAIL);
			pstmt.setString(1, email);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}

		return user;
	}
	
	public List<Test> findTestsByName(String name) {

		List<Test> tests = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();

			pstmt = connection.prepareStatement(SQL_SELECT_FROM_TESTS_BY_NAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tests.add(extractTest(rs));
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}
		return tests;
	}
	
	public Result findResultByUserIdAndTestId(long userId, long testId) {

		Result result = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_FROM_RESULTS_BY_USER_ID_AND_TEST_ID);
			pstmt.setLong(1, userId);
			pstmt.setLong(2, testId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = extractResult(rs);
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}
		return result;
	}

	//*************************************************************************************//
	
	public void updateUserStatus(User user) {

		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_UPDATE_USER_STATUS);
			pstmt.setInt(1, user.getStatus().getId());
			pstmt.setLong(2, user.getId());

			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOG.error("Error while updating a user");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(connection);
		}

	}

	public void updateAverage(User user, double average) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_UPDATE_USER_AVERAGE);
			pstmt.setDouble(1, average);
			pstmt.setLong(2, user.getId());

			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOG.error("Error while updating a user");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(connection);
		}		
	}
	
	public void updateTest(Test test) {

		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_UPDATE_TEST);
			pstmt.setString(1, test.getName());
			pstmt.setInt(2, test.getDuration());
			pstmt.setInt(3, test.getSubject().getId());
			pstmt.setInt(4, test.getComplexity().getId());
			pstmt.setLong(5, test.getId());

			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOG.error("Error while updating a test");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(connection);
		}
	}
	
	public void updateQuestion(Question question) {

		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_UPDATE_QUESTION);
			pstmt.setString(1, question.getQuestionText());
			pstmt.setLong(2, question.getId());

			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOG.error("Error while updating a question");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(connection);
		}

	}
	
	public void updateAnswer(Answer answer) {

		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_UPDATE_ANSWER);
			pstmt.setString(1, answer.getContent());
			pstmt.setBoolean(2, answer.isCorrect());
			pstmt.setLong(3, answer.getId());

			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOG.error("Error while updating an answer");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(connection);
		}

	}
	
	//***********************************************************************************//
	
	public void deleteTest(Long id) {

		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_DELETE_TEST_BY_ID);
			pstmt.setLong(1, id);

			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOG.error("Error while deleting a test");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(connection);
		}

	}

	public void deleteQuestion(Long id) {

		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_DELETE_QUESTION_BY_ID);
			pstmt.setLong(1, id);

			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOG.error("Error while deleting a question");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(connection);
		}
	}

	public void deleteAnswer(Long id) {

		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_DELETE_ANSWER_BY_ID);
			pstmt.setLong(1, id);

			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOG.error("Error while deleting an answer");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(connection);
		}
	}
	
	public void deleteAllUsers(){
		
		Connection connection = null;
		Statement stmt = null;

		try {
			connection = DBManager.getConnection();
		    stmt = (Statement) connection.createStatement();

			stmt.executeUpdate(SQL_DELETE_ALL_USERS);

		} catch (SQLException ex) {
			LOG.error("Error while deleting all users");
			ex.printStackTrace();
		} finally {
			close(stmt);
			close(connection);
		}

	}
	
	public void deleteAllTests(){
		
		Connection connection = null;
		Statement stmt = null;

		try {
			connection = DBManager.getConnection();
		    stmt = (Statement) connection.createStatement();

			stmt.executeUpdate(SQL_DELETE_ALL_TESTS);

		} catch (SQLException ex) {
			LOG.error("Error while deleting all tests");
			ex.printStackTrace();
		} finally {
			close(stmt);
			close(connection);
		}

	}

	// ***********************************************************************************//

	private Subject extractSubject(ResultSet rs) throws SQLException {
		Subject subject = new Subject();
		subject.setId(rs.getInt("id"));
		subject.setName(rs.getString("name"));
		return subject;
	}

	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setRole(findRoleById(rs.getInt("role_id")));
		user.setStatus(findStatusById(rs.getInt("status_id")));
		user.setResults(findResultsByUserId(user.getId()));
		user.setAverageResult(Double.valueOf(rs.getString("average")));
		return user;
	}

	private Role extractRole(ResultSet rs) throws SQLException {
		Role role = new Role();
		role.setId(rs.getInt("id"));
		role.setName(rs.getString("name"));
		return role;
	}

	private Test extractTest(ResultSet rs) throws SQLException {
		Test test = new Test();
		test.setId(rs.getInt("id"));
		test.setName(rs.getString("name"));
		test.setDuration(rs.getInt("duration"));
		test.setSubject(findSubjectById(rs.getInt("subject_id")));
		test.setComplexity(findComplexityById(rs.getInt("level_id")));
		test.setQuestions(findQuestionsByTestId(test.getId()));
		return test;
	}

	private Complexity extractComplexity(ResultSet rs) throws SQLException {
		Complexity complexity = new Complexity();
		complexity.setId(rs.getInt("id"));
		complexity.setName(rs.getString("name"));
		complexity.setLevel(rs.getInt("level"));
		return complexity;
	}

	private Answer extractAnswer(ResultSet rs) throws SQLException {
		Answer answer = new Answer();
		answer.setId(rs.getInt("id"));
		answer.setContent(rs.getString("content"));
		answer.setCorrect(rs.getBoolean("correct"));
		return answer;
	}

	private Question extractQuestion(ResultSet rs) throws SQLException {
		Question question = new Question();
		question.setId(rs.getInt("id"));
		question.setQuestionText(rs.getString("question_text"));
		question.setAnswers(findAnswersByQuestionId(question.getId()));
		return question;
	}

	private Status extractStatus(ResultSet rs) throws SQLException {
		Status status = new Status();
		status.setId(rs.getInt("id"));
		status.setName(rs.getString("name"));
		return status;
	}

	private Result extractResult(ResultSet rs) throws SQLException {
		Result result = new Result();
		result.setId(rs.getLong("id"));
		result.setTest(findTestById(rs.getLong("test_id")));
		result.setResult(rs.getDouble("result"));
		return result;
	}
	
	private List<Result> findResultsByUserId(long id) {

		List<Result> results = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_FROM_RESULTS_BY_USER_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				results.add(extractResult(rs));
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}
		return results;
	}
	
	private List<Answer> findAnswersByQuestionId(long id) {

		List<Answer> answers = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_FROM_ANSWERS_BY_QUESTION_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				answers.add(extractAnswer(rs));
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}
		return answers;
	}

	private List<Question> findQuestionsByTestId(long id) {

		List<Question> questions = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = DBManager.getConnection();
			pstmt = connection.prepareStatement(SQL_SELECT_FROM_QUESTIONS_BY_TEST_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				questions.add(extractQuestion(rs));
			}
		} catch (SQLException ex) {
			LOG.error("Select query was not executed");
			ex.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
			close(connection);
		}
		return questions;
	}


	// **********************************************************************************//

	private static Connection getConnection() throws SQLException {

		Connection connection = null;
		connection = DriverManager.getConnection(URL + dbName, USER, PASSWORD);
		return connection;
	}

	private static void close(Connection connection) {

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error("Exception while closing connection");
			}
		}
	}

	private static void close(Statement statement) {

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOG.error("Exception while closing statement");
			}
		}
	}

	private static void close(PreparedStatement statement) {

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOG.error("Exception while closing statement");
			}
		}
	}

	private static void close(ResultSet resultSet) {

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOG.error("Exception while closing result set");
			}
		}
	}

	

}
