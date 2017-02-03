package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.UserService;
import ua.nure.senchenko.SummaryTask4.entity.User;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.impl.UserServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that deletes a test
 */
public class DeleteTestCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		final HttpSession session = request.getSession();

		final TestService testService = TestServiceImpl.getInstance(Mode.PROD);
		final UserService userService = UserServiceImpl.getInstance(Mode.PROD);

		final Long testId = Long.valueOf(request.getParameter("testId"));
		testService.deleteTest(testId);
		
		String address = Path.PAGE_ADMIN_VIEW;
		
		List<User> users = userService.getAllClients();
		users = userService.sortUsersByAverageResult(users);
		session.setAttribute("listOfClients", users);
		
		session.setAttribute("listOfTests", testService.getAllTests());
		
		return address;
		
	}

}
