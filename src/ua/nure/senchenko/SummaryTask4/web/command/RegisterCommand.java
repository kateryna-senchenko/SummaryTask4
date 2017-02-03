package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.UserService;
import ua.nure.senchenko.SummaryTask4.entity.*;
import ua.nure.senchenko.SummaryTask4.exception.RegistrationException;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.SortCriteria;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.impl.UserServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that registers a user
 */
public class RegisterCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		final HttpSession session = request.getSession();

		session.removeAttribute("loginErrorMessage");
		session.removeAttribute("registrationErrorMessage");

		final String captcha = (String) session.getAttribute("captcha");
		final String responseCaptcha = request.getParameter("response_captcha");

		final String name = request.getParameter("name");
		final String email = request.getParameter("email");
		final String password = request.getParameter("password");
		final String confPassword = request.getParameter("confirmPassword");

		String address = null;

		if (!captcha.equals(responseCaptcha)) {

			address = Path.PAGE_REGISTER;

			session.setAttribute("registrationErrorMessage", "INVALID_CAPTCHA");
			session.setAttribute("name", name);
			session.setAttribute("email", email);

		} else {

			final UserService userService = UserServiceImpl.getInstance(Mode.PROD);
			final TestService testService = TestServiceImpl.getInstance(Mode.PROD);

			User user;
			try {
				user = userService.register(name, email, password, confPassword);
				final Role userRole = user.getRole();


				if (userRole.getName().equals("client")) {
					address = Path.PAGE_STUDENT_VIEW;
				}

				session.removeAttribute("loginErrorMessage");
				session.removeAttribute("registrationErrorMessage");
				session.setAttribute("user", user);
				session.setAttribute("userRole", userRole);
				session.setAttribute("listOfSubjects", testService.getAllSubjects());
				session.setAttribute("listOfTests", testService.getAllTests());
				session.setAttribute("sortCriteria", SortCriteria.values());

			} catch (RegistrationException e) {

				address = Path.PAGE_REGISTER;

				session.setAttribute("registrationErrorMessage", e.getErrorType());
				session.setAttribute("name", name);
				session.setAttribute("email", email);

			}
		}
		return address;
	}

}
