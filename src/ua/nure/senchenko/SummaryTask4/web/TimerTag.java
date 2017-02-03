package ua.nure.senchenko.SummaryTask4.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class TimerTag extends TagSupport {

	private static final long serialVersionUID = -7839142504964139009L;
	
	private String duration;
	private String location;

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setLocation(String location) {
		this.location = location;
	}


	public int doStartTag() throws JspException {
		
		JspWriter out = pageContext.getOut();
		try {
			
			int min = Integer.parseInt(duration);
			String output = "<script type='text/javascript'>"
					+ "window.onload = createTimer('" + location + "', " + min + ");</script>";
			out.println(output);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SKIP_BODY;
	}

}
