package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that logs out a user
 */
public class LogoutCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		final HttpSession session = request.getSession();
		
		session.invalidate();
		
		return Path.PAGE_LOGIN;
	}

}
