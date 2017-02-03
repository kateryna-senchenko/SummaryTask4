package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.UserService;
import ua.nure.senchenko.SummaryTask4.entity.Complexity;
import ua.nure.senchenko.SummaryTask4.entity.Subject;
import ua.nure.senchenko.SummaryTask4.entity.Test;
import ua.nure.senchenko.SummaryTask4.entity.User;
import ua.nure.senchenko.SummaryTask4.exception.ErrorType;
import ua.nure.senchenko.SummaryTask4.exception.TestException;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.impl.UserServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that updates a test
 */
public class UpdateTestCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		final HttpSession session = request.getSession();
		session.removeAttribute("errorMessage");
		
		final TestService testService = TestServiceImpl.getInstance(Mode.PROD);

		final Test test = (Test) session.getAttribute("test");
		final UserService userService = UserServiceImpl.getInstance(Mode.PROD);

		final Subject subject = testService.findSubjectById(Integer.valueOf(request.getParameter("subject")));
		final Complexity level = testService.findComplexityById(Integer.valueOf(request.getParameter("complexity")));
		
		final String dur = request.getParameter("duration");
		String address;
		try {
			if(dur == null || dur.trim().isEmpty()){
				throw new TestException(ErrorType.INVALID_DURATION);
			}
			final int duration = Integer.valueOf(dur);
			testService.updateTest(test.getId(), subject, request.getParameter("testName"), duration, level);
			address = Path.PAGE_ADMIN_VIEW;
			List<User> users = userService.getAllClients();
			users = userService.sortUsersByAverageResult(users);
			session.setAttribute("listOfClients", users);
			session.removeAttribute("question");
			session.removeAttribute("test");
			session.setAttribute("listOfTests", testService.getAllTests());
		}catch (TestException e) {
			session.setAttribute("errorMessage", e.getErrorType());
			address = Path.PAGE_EDIT_TEST;
		}
		
		return address;
	}

}
