package ua.nure.senchenko.SummaryTask4.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import ua.nure.senchenko.SummaryTask4.UserService;
import ua.nure.senchenko.SummaryTask4.db.DBManager;
import ua.nure.senchenko.SummaryTask4.entity.*;
import ua.nure.senchenko.SummaryTask4.exception.AuthenticationException;
import ua.nure.senchenko.SummaryTask4.exception.RegistrationException;
import ua.nure.senchenko.SummaryTask4.impl.comparator.AverageResultComparator;

import static ua.nure.senchenko.SummaryTask4.exception.ErrorType.*;

public class UserServiceImpl implements UserService {

	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

	private static UserServiceImpl userService;
	private static DBManager db_manager;

	private UserServiceImpl(Mode mode) {
		db_manager = DBManager.getInstance(mode);
	}

	public static synchronized UserService getInstance(Mode mode) {

		if (userService == null) {
			userService = new UserServiceImpl(mode);
		}
		return userService;
	}

	@Override
	public User register(String name, String email, String password, String confirmPassword)
			throws RegistrationException {

		if (name == null || email == null || password == null || confirmPassword == null) {
			LOG.error("Failed to register user: null parameters");
			throw new RegistrationException(NULL_PARAMETERS);
		}
		
		name = name.trim();
		email = email.trim();
		password = password.trim();
		confirmPassword = confirmPassword.trim();

		if (name.isEmpty()) {
			LOG.error("Failed to register user " + email + ": empty name input");
			throw new RegistrationException(NAME_IS_EMPTY);
		}

		if (!isValid(email)) {
			LOG.error("Failed to register user: invalid email " + email);
			throw new RegistrationException(INVALID_EMAIL);
		}

		if (db_manager.findUserByEmail(email) != null) {
			LOG.error("Registration failed: user with email " + email + " already exists");
			throw new RegistrationException(DUPLICATE_EMAIL);
		}

		if (password.isEmpty()) {
			LOG.error("Failed to register user " + email + ": empty password input");
			throw new RegistrationException(PASSWORD_IS_EMPTY);
		}

		if (!password.equals(confirmPassword)) {
			LOG.error("Failed to register user " + email + ": passwords do not match");
			throw new RegistrationException(PASSWORDS_DO_NOT_MATCH);
		}

		final User newUser = new User();
		
		newUser.setName(name);
		newUser.setEmail(email);
		newUser.setPassword(password);
		newUser.setRole(db_manager.findRoleById(2));
		newUser.setStatus(db_manager.findStatusById(1));
		newUser.setResults(new ArrayList<Result>());
		newUser.setAverageResult(0.0);

		final Long newUserId = db_manager.insertUser(newUser);

		if (newUserId == null) {
			LOG.error("User " + email + " was not inserted into the db");
			throw new RegistrationException(REGISTRATION_FAILED);
		}

		newUser.setId(newUserId);

		if (LOG.isInfoEnabled()) {
			LOG.info("Registered user " + email);
		}

		return newUser;

	}

	@Override
	public User login(String email, String password) throws AuthenticationException {

		if (email == null || password == null) {
			LOG.error("Failed to login user: null parameters");
			throw new AuthenticationException(NULL_PARAMETERS);
		}

		email = email.trim();
		password = password.trim();
		
		final User user = db_manager.findUserByEmail(email);

		if(user == null || !password.equals(user.getPassword())){
			LOG.error("Failed to login user " + email);
			throw new AuthenticationException(AUTHENTICATION_FAILED);
		}
		
		if (LOG.isInfoEnabled()) {
			LOG.info("Logged in user " + email);
		}

		return user;

	}

	@Override
	public List<User> getAllClients() {
		return db_manager.getAllClients();
	}

	@Override
	public void changeStatus(Long userId) {

		if (userId == null) {
			LOG.error("Null parameters");
			throw new IllegalArgumentException("Parameters should not be null");
		}

		final User user = db_manager.findUserById(userId);

		if (user.getStatus().getId() == 1) {
			user.setStatus(db_manager.findStatusById(2));
		} else {
			user.setStatus(db_manager.findStatusById(1));
		}

		db_manager.updateUserStatus(user);
		
		if (LOG.isInfoEnabled()) {
			LOG.info(user.getStatus().getName() + " user " + user.getEmail());
		}
	}

	
	@Override
	public User findUserByLogin(String login) {
		
		if(login == null){
			LOG.error("Null parameters");
			throw new IllegalArgumentException("Parameters should not be null");
		}
		return db_manager.findUserByEmail(login);
	}
	
	@Override
	public List<User> sortUsersByAverageResult(List<User> users) {
		
		Collections.sort(users, new AverageResultComparator());
		
		if (LOG.isInfoEnabled()) {
			LOG.info("List of users was sorted");
		}
		return users;
	}
	
	
	private boolean isValid(String email) {

		final String emailPattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";
		
		final Pattern pattern = Pattern.compile(emailPattern);
		final Matcher matcher = pattern.matcher(email);

		return matcher.matches();

	}

	

	
}
