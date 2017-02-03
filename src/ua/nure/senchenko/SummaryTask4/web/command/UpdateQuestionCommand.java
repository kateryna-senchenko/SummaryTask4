package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.entity.Question;
import ua.nure.senchenko.SummaryTask4.entity.Test;
import ua.nure.senchenko.SummaryTask4.exception.QuestionException;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that updates a question
 */
public class UpdateQuestionCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		final HttpSession session = request.getSession();
		session.removeAttribute("errorMessage");

		final TestService testService = TestServiceImpl.getInstance(Mode.PROD);

		final Test test = (Test) session.getAttribute("test");
		final Question question = (Question) session.getAttribute("question");
	
		String address;

		try {
			testService.updateQuestion(test.getId(), question.getId(), request.getParameter("questionText"));
			address = Path.PAGE_EDIT_TEST;
			session.removeAttribute("answer");
			session.setAttribute("question", testService.findQuestionById(question.getId()));
			session.setAttribute("test", testService.findTestById(test.getId()));
		} catch (QuestionException e) {
			session.setAttribute("errorMessage", e.getErrorType());
			address = Path.PAGE_EDIT_QUESTION;
		}
		
		return address;
	}

}
