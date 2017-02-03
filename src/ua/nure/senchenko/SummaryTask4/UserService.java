package ua.nure.senchenko.SummaryTask4;

import java.util.List;

import ua.nure.senchenko.SummaryTask4.entity.User;
import ua.nure.senchenko.SummaryTask4.exception.AuthenticationException;
import ua.nure.senchenko.SummaryTask4.exception.RegistrationException;

/**
 * UserService public API
 */
public interface UserService {

	/**
	 * registers new user
	 * @param name user's name
	 * @param email user's email
	 * @param password user's password
	 * @param confirmPassword confirmation password
	 * @return new User entity
	 * @throws RegistrationException in case of registration failure
	 */
	User register(String name, String email, String password, String confirmPassword) throws RegistrationException;

	/**
	 * logs in a user
	 * @param email user's email
	 * @param password user's password
	 * @return authenticated User entity
	 * @throws AuthenticationException in case of authentication failure
	 */
	User login(String email, String password) throws AuthenticationException;
	
	/**
	 * finds all registered users with role "client"
	 * @return list of User entities
	 */
	List<User> getAllClients();
	
	/**
	 * changes user status
	 * @param userId id of the user whose status needs to be changed
	 */
	void changeStatus(Long userId);
	
	/**
	 * finds user by login
	 * @param login user login
	 * @return User entity
	 */
	User findUserByLogin(String login);
	
	List<User> sortUsersByAverageResult(List<User> users);
	
}