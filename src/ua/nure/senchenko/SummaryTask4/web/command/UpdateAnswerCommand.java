package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.entity.Answer;
import ua.nure.senchenko.SummaryTask4.entity.Question;
import ua.nure.senchenko.SummaryTask4.exception.AnswerException;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that updates an answer
 */
public class UpdateAnswerCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{

		final HttpSession session = request.getSession();
		session.removeAttribute("errorMessage");

		final TestService testService = TestServiceImpl.getInstance(Mode.PROD);

		final Answer answer = (Answer) session.getAttribute("answer");
		final Question question = (Question) session.getAttribute("question");
		
		boolean correct;
		if (request.getParameter("checkbox") != null) {
			correct = true;
		} else {
			correct = false;
		}

		String address;
		
		try {
			testService.updateAnswer(question.getId(), answer.getId(), request.getParameter("content"), correct);
			address = Path.PAGE_EDIT_QUESTION;

			session.setAttribute("question", testService.findQuestionById(question.getId()));
			session.setAttribute("answer", testService.findAnswerById(answer.getId()));
		} catch (AnswerException e) {
			session.setAttribute("errorMessage", e.getErrorType());
			address = Path.PAGE_EDIT_ANSWER;
		}

		return address;
	}

}
