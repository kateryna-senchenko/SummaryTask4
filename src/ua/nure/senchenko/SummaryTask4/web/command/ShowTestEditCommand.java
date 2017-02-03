package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that navigates to test editing
 */
public class ShowTestEditCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		final HttpSession session = request.getSession();
		final TestService testService = TestServiceImpl.getInstance(Mode.PROD);
		
		final Long testId = Long.valueOf(request.getParameter("testId"));
		
		String address = Path.PAGE_EDIT_TEST;
		
		session.setAttribute("test", testService.findTestById(testId));
		session.setAttribute("mode_test", "updateTest");

		
		return address;
	}

}
