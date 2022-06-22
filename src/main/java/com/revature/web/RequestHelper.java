package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
	
	public static void processEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. set content type to be application/json
		response.setContentType("application/json");
		
		// 2. call the findAll() method from the employee service
		List<Employee> emps = eserv.getAll();
		
		// 3. transform the list to a string
		String jsonString = om.writeValueAsString(emps);
		
		// write it out
		PrintWriter out = response.getWriter();
		out.write(jsonString);
		
	}
	
	public static void processRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. extract all values from the parameters
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//2. construct a new employee object
		Employee e = new Employee(firstName, lastName, username, password);
		
		//3. call the register()method from the service layer
		int pk = eserv.register(e);
		
		// 4. chec its ID... if it's > 0 its successfull
		if (pk > 0) {
			
			e.setId(pk);
			HttpSession session = request.getSession();
			session.setAttribute("the-user", e);
			
			request.getRequestDispatcher("home.html").forward(request, response);
			// using the request dispatcher , forward the request and response to a new resource
		} else {
			// if its -1 that means the register method failed (and there is probably a duplicate user)
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<h1>Registration failed. Username already exists</h1>");
			out.println("<a href=\"index.html\">Back</a>");

		}
	}
	
	
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
