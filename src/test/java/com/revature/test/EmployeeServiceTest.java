package com.revature.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.dao.EmployeeDao;
import com.revature.models.Employee;
import com.revature.service.EmployeeService;

public class EmployeeServiceTest {
	
	private EmployeeService eserv;
	private EmployeeDao mockdao;
	
	@Before
	public void setup() {
		mockdao = mock(EmployeeDao.class);
		eserv = new EmployeeService(mockdao);
		
	}
	
	@After
	public void teardown() {
		mockdao = null;
		eserv = null;
	}
	
	@Test
	public void testConfirmLogin_success() {
		// 1. create a fake list of emps
		//this is the dummy data we feed to the mackito
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green");
		Employee e2 = new Employee(20, "Clint", "Barton", "hawkeye", "arrows");

		List<Employee> emps = new ArrayList<Employee>();
		emps.add(e1);
		emps.add(e2);
		
		// 2. setup the mock dao behavior
		//findAll() method behavior to provide fake data
		when(mockdao.findAll()).thenReturn(emps);
		
		//cature the actual output of the method
		Employee actual = eserv.confirmedLogin("thehulk", "green");
		// cature the expected output of the methods
		Employee expected = e1;
		
		//assert that theyre equal
		assertEquals(expected, actual);
	}
	
	@Test
	public void testConfirmLogin_fialed() {
		// 1. create a fake list of emps
		//this is the dummy data we feed to the mackito
		Employee e1 = new Employee(20, "Bruce", "Banner", "thehulk", "green");
		Employee e2 = new Employee(20, "Clint", "Barton", "hawkeye", "arrows");
		
		List<Employee> emps = new ArrayList<Employee>();
		emps.add(e1);
		emps.add(e2);
		
		// 2. setup the mock dao behavior
		//findAll() method behavior to provide fake data
		when(mockdao.findAll()).thenReturn(emps);
		
		//cature the actual output of the method
		Employee actual = eserv.confirmedLogin("thehulk", "blue");
		// cature the expected output of the methods
		Employee expected = new Employee();
		
		//assert that they're equal
		assertEquals(expected, actual);
	}
	
}
