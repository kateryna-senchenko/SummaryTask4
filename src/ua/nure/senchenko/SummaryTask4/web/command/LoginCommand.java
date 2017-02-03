package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.UserService;
import ua.nure.senchenko.SummaryTask4.entity.*;
import ua.nure.senchenko.SummaryTask4.exception.AuthenticationException;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.SortCriteria;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.impl.UserServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that logs in a user
 */
public class LoginCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
		

		final HttpSession session = request.getSession();
		
		session.removeAttribute("loginErrorMessage");
		session.removeAttribute("registrationErrorMessage");

		final UserService userService = UserServiceImpl.getInstance(Mode.PROD);
		final TestService testService = TestServiceImpl.getInstance(Mode.PROD);
		
		final String email = request.getParameter("email");
		final String password = request.getParameter("password");

		User user;
		String address = null;
		
		try {
			
			user = userService.login(email, password);
			final Role userRole = user.getRole();

			if (userRole.getName().equals("admin")) {
				address = Path.PAGE_ADMIN_VIEW;
				List<User> users = userService.getAllClients();
				users = userService.sortUsersByAverageResult(users);
				session.setAttribute("listOfClients", users);
			}

			if (userRole.getName().equals("client")) {
				
				if(user.getStatus().getName().equals("blocked")){
					address = Path.PAGE_BLOCKED;
				}else{
					address = Path.PAGE_STUDENT_VIEW;
				}
			}

			session.removeAttribute("loginErrorMessage");
			session.removeAttribute("registrationErrorMessage");
			session.setAttribute("user", user);
			session.setAttribute("userRole", userRole);
			session.setAttribute("listOfSubjects", testService.getAllSubjects());
			session.setAttribute("listOfTests", testService.getAllTests());
			session.setAttribute("complexityLevels", testService.getAllComplexityLevels());
			session.setAttribute("sortCriteria", SortCriteria.values());
		} catch (AuthenticationException e) {
			address = Path.PAGE_LOGIN;
			session.setAttribute("loginErrorMessage", e.getErrorType());
			session.setAttribute("email", email);
		}

		return address;
	}

}
