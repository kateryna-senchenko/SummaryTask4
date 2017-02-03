package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.entity.Question;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that deletes an answer
 */
public class DeleteAnswerCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		final HttpSession session = request.getSession();
		final Question question = (Question) session.getAttribute("question");
		final TestService testService = TestServiceImpl.getInstance(Mode.PROD);
		
		final Long answerId = Long.valueOf(request.getParameter("answerId"));
		testService.deleteAnswer(answerId);
		
		String address = Path.PAGE_EDIT_QUESTION;
		session.setAttribute("question", testService.findQuestionById(question.getId()));
		
		return address;
	}

}
