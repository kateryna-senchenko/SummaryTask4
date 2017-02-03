package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.UserService;
import ua.nure.senchenko.SummaryTask4.entity.Subject;
import ua.nure.senchenko.SummaryTask4.entity.Test;
import ua.nure.senchenko.SummaryTask4.entity.User;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.impl.UserServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that selects tests by specified subject
 */
public class ChooseSubjectCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		final HttpSession session = request.getSession();
		session.removeAttribute("errorMessage");
		
		final UserService userService = UserServiceImpl.getInstance(Mode.PROD);
		
		User user = (User) session.getAttribute("user");
		user = userService.findUserByLogin(user.getEmail());
		
		String address;

		if (user.getStatus().getName().equals("blocked")) {
			
			address = Path.PAGE_BLOCKED;
			
		} else {

			final TestService testService = TestServiceImpl.getInstance(Mode.PROD);
			final String subjectId = request.getParameter("subject");
			final Subject subject = testService.findSubjectById(Integer.valueOf(subjectId));

			List<Test> listOfTests = testService.findTestsBySubject(subject);

			if (request.getParameterValues("checkbox") != null) {
				
				final List<String> checkedSortCriteria = Arrays.asList(request.getParameterValues("checkbox"));
				listOfTests = testService.sortBySpecifiedCriteria(listOfTests, checkedSortCriteria);
				session.setAttribute("checkedSortCriteria", checkedSortCriteria);
				
			}
			address = Path.PAGE_STUDENT_VIEW;

			session.setAttribute("subject", subject);
			session.setAttribute("listOfTests", listOfTests);
		}
		return address;
	}

}
