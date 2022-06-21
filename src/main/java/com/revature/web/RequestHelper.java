package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;
import com.revature.service.EmployeeService;

public class RequestHelper {

	//employeeservice
	private static EmployeeService eserv = new EmployeeService(new EmployeeDao());
	//object mapper (for frontend)
	private static ObjectMapper  om = new ObjectMapper();
	
	
	/**
	 * Extracts the parameters from a request (username and password) from the UI
	 * Call the confirmLogin() method from the employeeService and
	 * see ifa user with that username and password exitst
	 * 
	 * who will privide the methof with the HttpRequest? the ui
	 * we need to build an html doc with a form that will send these parameters to the method
	 */
	public static void processLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		// 1. extract the parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// 2. call the confirm login() method from the employeeService and see what it retures
		Employee e = eserv.confirmedLogin(username, password);
		// 3. if the user exists print their into to the screan
		if (e.getId() > 0) {
			 // grab the session
			HttpSession session = request.getSession();
			
			// add the user to the session
			session.setAttribute("the-user", e);
			
			//printout the users data with the print writer
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			
			out.println("<h1>Welcome " + e.getFirstName() + "!</h1>");
			
			String json = om.writeValueAsString(e);
			out.println(json);
			
			
		} else {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("No user found, sorry");
		}
			// alternitively you can redirect to another resource
		
	}
	
}
