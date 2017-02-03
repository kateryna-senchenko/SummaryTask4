package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.entity.Test;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that deletes a question
 */
public class DeleteQuestionCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		final HttpSession session = request.getSession();
		final Test test = (Test) session.getAttribute("test");
		final TestService testService = TestServiceImpl.getInstance(Mode.PROD);
		
		final Long questionId = Long.valueOf(request.getParameter("questionId"));
		testService.deleteQuestion(questionId);
		
		String address = Path.PAGE_EDIT_TEST;
		
		session.setAttribute("test", testService.findTestById(test.getId()));
		
		return address;
	}

}
