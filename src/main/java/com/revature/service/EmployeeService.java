package com.revature.service;

import java.util.List;
import java.util.Optional;

import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;

public class EmployeeService {

	private EmployeeDao edao;
	
	public EmployeeService(EmployeeDao edao) {
		this.edao = edao;
	}
	
	public Employee confirmedLogin(String username, String password) {
		
		Optional<Employee> possibleEmp = edao.findAll().stream()
				.filter(e -> (e.getUsername().equals(username) && e.getPassword().equals(password)))
				.findFirst();
		
		return (possibleEmp.isPresent() ? possibleEmp.get() : new Employee());
		
	}
	
	public List<Employee> getAll() {
		return edao.findAll();
	}
	
	public int register(Employee e) {
		return edao.insert(e);
	}
	
}
