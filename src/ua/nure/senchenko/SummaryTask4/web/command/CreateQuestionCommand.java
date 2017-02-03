package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.entity.Test;
import ua.nure.senchenko.SummaryTask4.exception.QuestionException;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that creates a question
 */
public class CreateQuestionCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		final HttpSession session = request.getSession();
		
		session.removeAttribute("errorMessage");

		final TestService testService = TestServiceImpl.getInstance(Mode.PROD);
		
		final String questionText = request.getParameter("questionText");
		final Test test = (Test) session.getAttribute("test");
		
		String address;
		try {
			testService.createQuestion(test.getId(), questionText);
			address = Path.PAGE_EDIT_TEST;
			session.setAttribute("test", testService.findTestById(test.getId()));
		} catch (QuestionException e) {
			address = Path.PAGE_EDIT_QUESTION;
			session.setAttribute("errorMessage", e.getErrorType());
		}
		

		return address;
	}

}
