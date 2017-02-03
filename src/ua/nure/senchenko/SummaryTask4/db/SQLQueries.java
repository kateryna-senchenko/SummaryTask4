package ua.nure.senchenko.SummaryTask4.db;

public class SQLQueries {
	
	
	public static final String SQL_INSERT_USER = "insert into users(name, email, password, role_id, status_id, average) values (?,?,?,?,?,?)";
	public static final String SQL_INSERT_RESULT = "insert into results(user_id, test_id, result) values (?,?,?)";
	public static final String SQL_INSERT_TEST = "insert into tests(name, duration, subject_id, level_id) values (?,?,?,?)";
	public static final String SQL_INSERT_QUESTION = "insert into questions(question_text, test_id) values (?,?)";
	public static final String SQL_INSERT_ANSWER = "insert into answers(content, correct, question_id) values (?,?,?)";
	
//***********************************************************************************************************************//
	
	public static final String SQL_SELECT_FROM_USERS = "select * from users";
	public static final String SQL_SELECT_FROM_TESTS = "select * from tests";
	public static final String SQL_SELECT_FROM_SUBJECTS = "select * from subjects";
	public static final String SQL_SELECT_FROM_COMPLEXITY_LEVELS = "select * from complexity_levels";

	public static final String SQL_SELECT_FROM_USERS_BY_ID = "select * from users where id = ?";
	public static final String SQL_SELECT_FROM_SUBJECTS_BY_ID = "select * from subjects where id = ?";
	public static final String SQL_SELECT_FROM_TESTS_BY_ID = "select * from tests where id = ?";
	public static final String SQL_SELECT_FROM_ROLES_BY_ID = "select * from roles where id = ?";
	public static final String SQL_SELECT_FROM_QUESTIONS_BY_ID = "select * from questions where id = ?";
	public static final String SQL_SELECT_FROM_ANSWERS_BY_ID = "select * from answers where id = ?";
	public static final String SQL_SELECT_FROM_COMPLEXITY_LEVELS_BY_ID = "select * from complexity_levels where id = ?";
	public static final String SQL_SELECT_FROM_STATUSES_BY_ID = "select * from statuses where id = ?";
	
	public static final String SQL_SELECT_FROM_USERS_BY_EMAIL = "select * from users where email = ?";
	public static final String SQL_SELECT_FROM_TESTS_BY_SUBJECT = "select * from tests where subject_id = ?";
	public static final String SQL_SELECT_FROM_TESTS_BY_NAME = "select * from tests where name = ?";

	public static final String SQL_SELECT_FROM_ANSWERS_BY_QUESTION_ID = "select * from answers where question_id = ?";
	public static final String SQL_SELECT_FROM_QUESTIONS_BY_TEST_ID = "select * from questions where test_id = ?";
	public static final String SQL_SELECT_FROM_RESULTS_BY_USER_ID = "select * from results where user_id = ?";
	public static final String SQL_SELECT_FROM_USERS_BY_ROLE_ID = "select * from users where role_id = ?";
	
	public static final String SQL_SELECT_FROM_RESULTS_BY_USER_ID_AND_TEST_ID = "select * from results where user_id = ? and test_id = ?";


//*********************************************************************************************************************//
	
	public static final String SQL_UPDATE_USER_STATUS = "update users set status_id = ? where id = ? ";
	public static final String SQL_UPDATE_ANSWER = "update answers set content = ?, correct = ? where id = ? ";
	public static final String SQL_UPDATE_QUESTION = "update questions set question_text = ? where id = ? ";
	public static final String SQL_UPDATE_TEST = "update tests set name = ?, duration = ?, subject_id = ?, level_id = ? where id = ? ";
	public static final String SQL_UPDATE_USER_AVERAGE = "update users set average = ? where id = ? ";

//*********************************************************************************************************************//	
	
	public static final String SQL_DELETE_TEST_BY_ID = "delete from tests where id = ?";
	public static final String SQL_DELETE_QUESTION_BY_ID = "delete from questions where id = ?";
	public static final String SQL_DELETE_ANSWER_BY_ID = "delete from answers where id = ?";
	
	public static final String SQL_DELETE_ALL_USERS = "delete from users";
	public static final String SQL_DELETE_ALL_TESTS = "delete from tests";

}
