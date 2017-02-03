package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.UserService;
import ua.nure.senchenko.SummaryTask4.entity.Test;
import ua.nure.senchenko.SummaryTask4.entity.User;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.impl.UserServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that selects specified test
 */
public class ChooseTestCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		final HttpSession session = request.getSession();
		session.removeAttribute("errorMessage");

		final TestService testService = TestServiceImpl.getInstance(Mode.PROD);
		final UserService userService = UserServiceImpl.getInstance(Mode.PROD);
		
		User user = (User) session.getAttribute("user");
		user = userService.findUserByLogin(user.getEmail());
		
		String address;
		
		if (user.getStatus().getName().equals("blocked")) {
			
			address = Path.PAGE_BLOCKED;
			
		} else {

			if (request.getParameter("testId") == null) {
				
				address = Path.PAGE_STUDENT_VIEW;
				session.setAttribute("errorMessage", "CHOOSE_TEST");
				
			} else {
				
				final Long testId = Long.valueOf(request.getParameter("testId"));

				if (!testService.canBeTaken(testId)) {
					
					address = Path.PAGE_STUDENT_VIEW;
					session.setAttribute("errorMessage", "CANNOT_BE_TAKEN");
					
				} else if (testService.isAlreadyTakenByUser(user.getId(), testId)) {
					
					address = Path.PAGE_STUDENT_VIEW;
					session.setAttribute("errorMessage", "ALREADY_TAKEN");
					
				} else {
					
					address = Path.PAGE_TAKE_TEST;
					final Test test = testService.findTestById(testId);
					session.setAttribute("test", test);
				}
			}
		}

		return address;
	}

}
