package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.UserService;
import ua.nure.senchenko.SummaryTask4.entity.User;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.UserServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that changes status of a user
 */
public class ChangeStatusCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		final HttpSession session = request.getSession();

		final UserService userService = UserServiceImpl.getInstance(Mode.PROD);
		
		final Long userId = Long.valueOf(request.getParameter("userId"));
		
		userService.changeStatus(userId);
		
		List<User> users = userService.getAllClients();
		users = userService.sortUsersByAverageResult(users);
		session.setAttribute("listOfClients", users);
		
		return Path.PAGE_ADMIN_VIEW;
	}

}
