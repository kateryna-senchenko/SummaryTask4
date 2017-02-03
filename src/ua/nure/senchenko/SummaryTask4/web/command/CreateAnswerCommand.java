package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.entity.Question;
import ua.nure.senchenko.SummaryTask4.entity.Test;
import ua.nure.senchenko.SummaryTask4.exception.AnswerException;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that creates an answer
 */
public class CreateAnswerCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		final HttpSession session = request.getSession();
		
		session.removeAttribute("errorMessage");
		
		final TestService testService = TestServiceImpl.getInstance(Mode.PROD);
		
		final String content = request.getParameter("content");
		boolean correct;
		
		if(request.getParameter("checkbox") != null){
			correct = true;
		}else{
			correct = false;
		}
		
		final Question question = (Question) session.getAttribute("question");
		final Test test = (Test) session.getAttribute("test");
		
		String address;
		
		try {
			testService.createAnswer(question.getId(), content, correct);
			address = Path.PAGE_EDIT_QUESTION;
			session.setAttribute("test", testService.findTestById(test.getId()));
			session.setAttribute("question", testService.findQuestionById(question.getId()));

		} catch (AnswerException e) {
			address = Path.PAGE_EDIT_ANSWER;
			session.setAttribute("errorMessage", e.getErrorType());
		}
		
		return address;
	}
	
}
