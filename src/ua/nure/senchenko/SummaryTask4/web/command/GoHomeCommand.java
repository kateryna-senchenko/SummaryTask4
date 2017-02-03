package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.senchenko.SummaryTask4.TestService;
import ua.nure.senchenko.SummaryTask4.UserService;
import ua.nure.senchenko.SummaryTask4.entity.Role;
import ua.nure.senchenko.SummaryTask4.entity.User;
import ua.nure.senchenko.SummaryTask4.impl.Mode;
import ua.nure.senchenko.SummaryTask4.impl.TestServiceImpl;
import ua.nure.senchenko.SummaryTask4.impl.UserServiceImpl;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Command that navigates to home page
 */
public class GoHomeCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		final HttpSession session = request.getSession();
		session.removeAttribute("errorMessage");

		final UserService userService = UserServiceImpl.getInstance(Mode.PROD);
		final TestService testService = TestServiceImpl.getInstance(Mode.PROD);
		
		User user = (User) session.getAttribute("user");
		user = userService.findUserByLogin(user.getEmail());
		
		session.setAttribute("listOfTests", testService.getAllTests());
		String address = null;
		final Role userRole = user.getRole();

		if (userRole.getName().equals("admin")) {
			address = Path.PAGE_ADMIN_VIEW;
			List<User> users = userService.getAllClients();
			users = userService.sortUsersByAverageResult(users);
			session.setAttribute("listOfClients", users);
		}

		if (userRole.getName().equals("client")) {

			if (user.getStatus().getName().equals("blocked")) {
				address = Path.PAGE_BLOCKED;
			}else{
				address = Path.PAGE_STUDENT_VIEW;
			}
		}

		return address;
	}

}
