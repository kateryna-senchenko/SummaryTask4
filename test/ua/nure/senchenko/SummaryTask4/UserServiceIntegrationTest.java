package ua.nure.senchenko.SummaryTask4;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import ua.nure.senchenko.SummaryTask4.db.DBManager;
import ua.nure.senchenko.SummaryTask4.entity.User;
import ua.nure.senchenko.SummaryTask4.exception.AuthenticationException;
import ua.nure.senchenko.SummaryTask4.exception.RegistrationException;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.UserServiceImpl;

import static ua.nure.senchenko.SummaryTask4.exception.ErrorType.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class UserServiceIntegrationTest {

	private final UserService userService = UserServiceImpl.getInstance(Mode.TEST);
	private final DBManager db_manager = DBManager.getInstance(Mode.TEST);
	
	private final String name = "Jem Finch";
	private final String email = "jem.finch@gmail.com";
	private final String password = "freeparrots";
	
	@After
	public void deleteAllUsers() {
		db_manager.deleteAllUsers();
	}

	@Test
	public void registerNewUser() {

		User user = null;
		try {
			user = userService.register(name, email, password, password);
		} catch (RegistrationException e) {
			fail("Failed to register new user");
		}

		assertEquals("New user has different name", name, user.getName());
		assertEquals("New user has different email", email, user.getEmail());

	}

	@Test
	public void failRegisterUserWithDuplicateEmail() {

		try {
			userService.register(name, email, password, password);
		} catch (RegistrationException e) {
			fail("Failed to register new user");
		}

		try {
			userService.register(name, email, password, password);
			fail("Expected RegistrationException was not thrown");
		} catch (RegistrationException e) {
			assertEquals("Wrong error type", DUPLICATE_EMAIL, e.getErrorType());
		}

	}

	@Test
	public void failRegisterIfEmailWithNoAt() {

		final String wrongEmail = "wrong.gmail.com";

		try {
			userService.register(name, wrongEmail, password, password);
			fail("Expected RegistrationException was not thrown");
		} catch (RegistrationException e) {
			assertEquals("Wrong error type", INVALID_EMAIL, e.getErrorType());
		}
	}

	@Test
	public void failRegisterUserWithNotMatchingPasswords() {

		final String confirmPassword = password + "123";

		try {
			userService.register(name, email, password, confirmPassword);
			fail("Expected RegistrationException was not thrown");
		} catch (RegistrationException e) {
			assertEquals("Wrong error type", PASSWORDS_DO_NOT_MATCH, e.getErrorType());
		}
	}

	@Test
	public void failRegisterUserWithEmptyPassword() {

		final String emptyPassword = "";

		try {
			userService.register(name, email, emptyPassword, password);
			fail("Expected RegistrationException was not thrown");
		} catch (RegistrationException e) {
			assertEquals("Wrong error type", PASSWORD_IS_EMPTY, e.getErrorType());
		}

	}

	@Test
	public void failRegisterUserWithEmptyName() {

		final String emptyName = " ";

		try {
			userService.register(emptyName, email, password, password);
			fail("Expected RegistrationException was not thrown");
		} catch (RegistrationException e) {
			assertEquals("Wrong error type", NAME_IS_EMPTY, e.getErrorType());
		}

	}

	@Test
	public void failRegisterUserWithNullParameters() {

		try {
			userService.register(null, email, null, password);
			fail("Expected RegistrationException was not thrown");
		} catch (RegistrationException e) {
			assertEquals("Wrong error type", NULL_PARAMETERS, e.getErrorType());
		}

	}

	@Test
	public void loginUser() {


		try {
			userService.register(name, email, password, password);
		} catch (RegistrationException e) {
			fail("Failed to register new user");
		}

		User user = null;
		try {
			user = userService.login(email, password);
		} catch (AuthenticationException e) {
			fail("Failed to login registered user");
		}

		assertEquals("Logged in user has different email", email, user.getEmail());

	}

	@Test
	public void failLoginUnregisteredUser() {

		try {
			userService.login(email, password);
			fail("Expected AuthenticationException was not thrown");
		} catch (AuthenticationException e) {
			assertEquals("Wrong error type", AUTHENTICATION_FAILED, e.getErrorType());
		}

	}

	@Test
	public void failLoginUserWithWrongPassword() {


		try {
			userService.register(name, email, password, password);
		} catch (RegistrationException e) {
			fail("Failed to register new user");
		}

		final String wrongPassword = password + "123";

		try {
			userService.login(email, wrongPassword);
			fail("Expected AuthenticationException was not thrown");
		} catch (AuthenticationException e) {
			assertEquals("Wrong error type", AUTHENTICATION_FAILED, e.getErrorType());
		}

	}

	@Test
	public void failLoginWithNullParameters() {

		try {
			userService.register(name, email, password, password);
		} catch (RegistrationException e) {
			fail("Failed to register new user");
		}

		try {
			userService.login(email, null);
			fail("Expected AuthenticationException was not thrown");
		} catch (AuthenticationException e) {
			assertEquals("Wrong error type", NULL_PARAMETERS, e.getErrorType());
		}

	}

	@Test
	public void testSafetyInMultithreading() throws ExecutionException, InterruptedException {

		final int count = 30;
		final ExecutorService executor = Executors.newFixedThreadPool(count);
		final CountDownLatch startLatch = new CountDownLatch(count);
		final List<Future<User>> results = new ArrayList<>();
		AtomicInteger someDifferenceInEmail = new AtomicInteger(0);

		Callable<User> callable = () -> {

			startLatch.countDown();
			startLatch.await();

			final String name = "name";
			final String email = "email" + someDifferenceInEmail.getAndIncrement() + "@gmail.com";
			final String password = "password";

			final User registered = userService.register(name, email, password, password);
			final User loggedIn = userService.login(email, password);

			assertEquals("User ids after registration and login are not the same", registered.getId(), loggedIn.getId());

			return loggedIn;
		};

		for (int i = 0; i < count; i++) {

			Future<User> future = executor.submit(callable);
			results.add(future);
		}

		final Set<Long> userIds = new HashSet<>();

		for (Future<User> future : results) {
			userIds.add(future.get().getId());

		}

		if (userIds.size() != count) {
			fail("Generated user ids are not unique");
		}

	}
}
