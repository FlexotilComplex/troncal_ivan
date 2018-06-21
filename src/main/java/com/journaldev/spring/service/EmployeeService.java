package com.journaldev.spring.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.journaldev.jpa.data.Employee;

@Component
public class EmployeeService {
	
	@PersistenceContext
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Autowired
    private PlatformTransactionManager transactionManager;
	
	@Transactional
	public void register(Employee emp) throws Exception {
		// Save employee
	try {
		this.em.persist(emp);
	
	} catch (Exception e) {
		throw new Exception("Error al registrar");
	}
	}
	
	@Transactional
	public void eliminar(Employee emp) {

	Employee e = em.find(Employee.class, emp.getEmployeeId());
		this.em.remove(e);
	}
	

	
	public List<Employee> listar(){
		
		return em.createQuery("from Employee e").getResultList();
		
	}

	@Transactional
	public void modificar(Employee employee) throws Exception {
		try {
			em.merge(employee);
//			em.merge(null);
		} catch (Exception e) {
			throw new Exception("Error al modificar");
		}
	}
	
}
