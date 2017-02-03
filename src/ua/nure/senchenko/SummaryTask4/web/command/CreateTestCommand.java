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
import ua.nure.senchenko.SummaryTask4.entity.User;
import ua.nure.senchenko.SummaryTask4.exception.ErrorType;
import ua.nure.senchenko.SummaryTask4.exception.TestException;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.impl.UserServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that creates a test
 */
public class CreateTestCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		final HttpSession session = request.getSession();
		session.removeAttribute("errorMessage");
		
		final TestService testService = TestServiceImpl.getInstance(Mode.PROD);
		final UserService userService = UserServiceImpl.getInstance(Mode.PROD);
		
		final String subjectId = request.getParameter("subject");
		final Subject subject = testService.findSubjectById(Integer.valueOf(subjectId));
		
		final String name = request.getParameter("testName");
		final String dur = request.getParameter("duration");
		
		final String levelId = request.getParameter("complexity");
		final Complexity level = testService.findComplexityById(Integer.valueOf(levelId));
		
		String address;
		try {
			
			if(dur == null || dur.trim().isEmpty()){
				throw new TestException(ErrorType.INVALID_DURATION);
			}
			
			final int duration = Integer.valueOf(dur);
			testService.createTest(subject, name, duration, level);
			session.removeAttribute("questions");
			session.removeAttribute("answers");
			session.removeAttribute("subject");
			session.removeAttribute("testName");
			session.removeAttribute("duration");
			session.removeAttribute("level");

			address = Path.PAGE_ADMIN_VIEW;
			List<User> users = userService.getAllClients();
			users = userService.sortUsersByAverageResult(users);
			session.setAttribute("listOfClients", users);
			session.setAttribute("listOfTests", testService.getAllTests());
		
		} catch (TestException e) {
			session.setAttribute("errorMessage", e.getErrorType());
			address = Path.PAGE_EDIT_TEST;
		}
		
		return address;
	}

}
