package com.journaldev.prime.faces.beans;

import java.util.List;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.journaldev.jpa.data.Employee;
import com.journaldev.spring.service.EmployeeService;

@ManagedBean
@SessionScoped
public class RegisterEmployee {

	@ManagedProperty("#{employeeService}")
	private EmployeeService employeeService;

	private List<Employee> lstEmpleados;
	
	public List<Employee> getLstEmpleados() {
		return lstEmpleados;
	}
	
	public void setLstEmpleados(List<Employee> lstEmpleados) {
		this.lstEmpleados = lstEmpleados;
	}
	
	private Employee employee = new Employee();

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public boolean verificarSesion(){
		return true;      
	}
	
	public String register() {
		try {
			employeeService.register(employee);
			listar();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("El empleado "+this.employee.getEmployeeName()+" se ha registrado con �xito"));
			employee = new Employee();
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Error al registrar"));
		}
		return "";
	}
	
	public void listar() {
		
		lstEmpleados=(List<Employee>)employeeService.listar();
		
	}
	
	public void eliminar( Employee emp) {
		employeeService.eliminar(emp);
		listar();
	}
	
	public String leer (Employee emp) {
		this.employee = emp;
		return "editar";
	}
	
	public void cerrarSesion() {
		
	}
	
	private TimeZone timeZone;
	public TimeZone getTimeZone() {  
		  TimeZone timeZone = TimeZone.getDefault();  
		  return timeZone;  
	}  
	
	public String modificar() {
		try {
			employeeService.modificar(employee);
			listar();
			employee = new Employee();
			return "index?faces-redirect=true";
			
		} catch (Exception e) {
			employee = new Employee();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Error al modificar"));
			return "index";
		}
	}
	public String cancelar() {
		employee = new Employee();
		return "index?faces-redirect=true";
	}
}
