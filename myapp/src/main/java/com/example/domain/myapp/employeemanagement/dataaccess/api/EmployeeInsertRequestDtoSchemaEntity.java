package com.example.domain.myapp.employeemanagement.dataaccess.api;

import com.example.domain.myapp.employeemanagement.common.api.EmployeeInsertRequestDtoSchema;
import com.example.domain.myapp.general.dataaccess.api.ApplicationPersistenceEntity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Transient;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Entity definiton of Employee
 */
@Entity
@javax.persistence.Table(name = "EmployeeInsertRequestDtoSchema")
public class EmployeeInsertRequestDtoSchemaEntity extends ApplicationPersistenceEntity implements EmployeeInsertRequestDtoSchema {

  private static final long serialVersionUID = 1L;

	@Size(max = 30, min = 3)	
	private String name;
	@Size(max = 100, min = 3)	
	private String surname;
	@Size(max = 100, min = 3)	
	private String email;

	public String getName() {
	  return this.name;
	}
	
	public void setName(String name) {
	  this.name = name;
	}
	
	public String getSurname() {
	  return this.surname;
	}
	
	public void setSurname(String surname) {
	  this.surname = surname;
	}
	
	public String getEmail() {
	  return this.email;
	}
	
	public void setEmail(String email) {
	  this.email = email;
	}
	

}
