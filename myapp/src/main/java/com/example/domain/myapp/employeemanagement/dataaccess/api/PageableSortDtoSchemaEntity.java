package com.example.domain.myapp.employeemanagement.dataaccess.api;

import com.example.domain.myapp.employeemanagement.common.api.PageableSortDtoSchema;
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
 * Entity definition of PageSort
 */
@Entity
@javax.persistence.Table(name = "PageableSortDtoSchema")
public class PageableSortDtoSchemaEntity extends ApplicationPersistenceEntity implements PageableSortDtoSchema {

  private static final long serialVersionUID = 1L;

	@Size(max = 100, min = 3)	
	private String property;
	@Size(max = 100, min = 3)	
	private String direction;

	public String getProperty() {
	  return this.property;
	}
	
	public void setProperty(String property) {
	  this.property = property;
	}
	
	public String getDirection() {
	  return this.direction;
	}
	
	public void setDirection(String direction) {
	  this.direction = direction;
	}
	

}
