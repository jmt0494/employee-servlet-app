package com.revature.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.revature.models.Employee;
import com.revature.util.HibernateUtil;

// servlet >> service >> dao
public class EmployeeDao {
	
	// CRUD methods
	
	public int insert(Employee e) {
		// grab the session object
		Session ses = HibernateUtil.getSession();
		
		//begin tx
		Transaction tx = ses.beginTransaction();
		
		// cature the pk returned when the session method save() is called
		int pk;
		try {
			pk = (int) ses.save(e);
		} catch (ConstraintViolationException e1) {
			e1.printStackTrace();
			return -1;
		}
		
		tx.commit();
		//return the pk
		return pk;
	}
	
	public List<Employee> findAll() {
		
		//grab the session
		Session ses = HibernateUtil.getSession();
		
		// make an hql -- Hibernate query language
		List<Employee> emps = ses.createQuery("from Employee", Employee.class).list();
		
		return emps;
	}
	
	public boolean delete(int id) {
		return false;
	}
	
	public boolean update(Employee e) {
		return false;
	}
}
