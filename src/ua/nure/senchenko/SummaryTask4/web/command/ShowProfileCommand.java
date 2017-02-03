package ua.nure.senchenko.SummaryTask4.web.command;

import java.io.IOException;

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
 * Command that navigates to the profile page
 */
public class ShowProfileCommand extends Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		final HttpSession session = request.getSession();
		final UserService userService = UserServiceImpl.getInstance(Mode.PROD);
		
		User user = (User) session.getAttribute("user");
		user = userService.findUserByLogin(user.getEmail());
		String address;
		if (user.getStatus().getName().equals("blocked")) {
			address = Path.PAGE_BLOCKED;
		}else{
			address = Path.PAGE_PROFILE;
		}
		session.setAttribute("user", user);
		return address;
	}

}
