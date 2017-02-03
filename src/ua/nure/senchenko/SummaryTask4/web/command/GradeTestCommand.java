package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.entity.Test;
import ua.nure.senchenko.SummaryTask4.entity.User;
import ua.nure.senchenko.SummaryTask4.entity.Result;

import ua.nure.senchenko.SummaryTask4.exception.GradingException;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that grades a test
 */
public class GradeTestCommand extends Command {


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		final HttpSession session = request.getSession();
		
		final TestService testService = TestServiceImpl.getInstance(Mode.PROD);
		
		final Test test = (Test) session.getAttribute("test");
		final User user = (User) session.getAttribute("user");
		
		List<String> checkedAnswerIds = null;
		
		if(request.getParameterValues("checkbox") != null){
			checkedAnswerIds = Arrays.asList(request.getParameterValues("checkbox"));
		}
		

		Result result;
		try {
			result = testService.gradeTest(user, test, checkedAnswerIds);
			session.setAttribute("testResult", result);
		} catch (GradingException e) {
			session.setAttribute("testResult", e.getErrorType());
		}
		
		String address = Path.PAGE_TEST_RESULT;
		
		return address;
	}

}
