package ua.nure.senchenko.SummaryTask4.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import ua.nure.senchenko.SummaryTask4.web.command.Command;
import ua.nure.senchenko.SummaryTask4.web.command.CommandContainer;


public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1625384198710809866L;
	
	private static final Logger LOG = Logger.getLogger(Controller.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	
	private void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		LOG.debug("Controller starts");
		
		String address;

		final String commandName = request.getParameter("command");
		
		if (commandName == null){
			address = Path.PAGE_LOGIN;
		} else{
			Command command = CommandContainer.get(commandName);
			address = command.execute(request, response);
		}

		LOG.debug("Controller finished, going to " + address);
		response.sendRedirect(address);
	}
}
