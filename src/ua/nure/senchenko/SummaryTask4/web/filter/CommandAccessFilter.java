package ua.nure.senchenko.SummaryTask4.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.senchenko.SummaryTask4.entity.Role;
import ua.nure.senchenko.SummaryTask4.web.Path;

/**
 * Filter that provides role validating
 */
public class CommandAccessFilter implements Filter {
	
	private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);

	private Map<String, List<String>> accessMap = new HashMap<String, List<String>>();
	private List<String> common = new ArrayList<String>();	
	private List<String> outOfControl = new ArrayList<String>();
	
	public void destroy() {
		LOG.debug("Filter destruction starts");
		LOG.debug("Filter destruction finished");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOG.debug("Filter starts");
		
		if (accessAllowed(request)) {
			LOG.debug("Filter finished");
			chain.doFilter(request, response);
		} else {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect(Path.PAGE_LOGIN);  
			
		}
	}
	
	private boolean accessAllowed(ServletRequest request) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = request.getParameter("command");
		if (commandName == null || commandName.isEmpty()) {
			return false;
		}
		
		if (outOfControl.contains(commandName)) {
			return true;
		}
		
		HttpSession session = httpRequest.getSession(false);
		if (session == null) { 
			return false;
		}
		
		Role userRole = (Role) session.getAttribute("userRole");
		if (userRole == null) {
			return false;
		}
		
		return accessMap.get(userRole.getName()).contains(commandName)
				|| common.contains(commandName);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("Filter initialization starts");
		
		accessMap.put("admin", Arrays.asList(fConfig.getInitParameter("admin").split("\\p{Blank}")));
		accessMap.put("client", Arrays.asList(fConfig.getInitParameter("client").split("\\p{Blank}")));

		common = Arrays.asList(fConfig.getInitParameter("common").split("\\p{Blank}"));

		outOfControl = Arrays.asList(fConfig.getInitParameter("out-of-control").split("\\p{Blank}"));
		
		LOG.debug("Filter initialization finished");
	}
	
	
}
