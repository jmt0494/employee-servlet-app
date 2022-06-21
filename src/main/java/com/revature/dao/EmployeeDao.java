package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Employee;
import com.revature.util.HibernateUtil;

public class EmployeeDao {
	
	// CRUD methods
	
	public int insert(Employee e) {
		// grab the session object
		Session ses = HibernateUtil.getSession();
		
		//begin tx
		Transaction tx = ses.beginTransaction();
		
		// cature the pk returned when the session method save() is called
		int pk = (int) ses.save(e);
		
		//return the pk
		return pk;
	}
	
	public List<Employee> findAll() {
		return new ArrayList<Employee>();
	}
	
	public boolean delete(int id) {
		return false;
	}
	
	public boolean update(Employee e) {
		return false;
	}
}
