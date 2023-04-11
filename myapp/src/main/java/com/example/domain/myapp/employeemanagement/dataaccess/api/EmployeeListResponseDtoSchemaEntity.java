package com.example.domain.myapp.employeemanagement.dataaccess.api;

import com.example.domain.myapp.employeemanagement.common.api.EmployeeListResponseDtoSchema;
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
@javax.persistence.Table(name = "EmployeeListResponseDtoSchema")
public class EmployeeListResponseDtoSchemaEntity extends ApplicationPersistenceEntity implements EmployeeListResponseDtoSchema {

  private static final long serialVersionUID = 1L;

	private List<EmployeeListContentResponseDtoSchemaEntity> content; 
	private void pageable;
	private long totalElements;

  public void setContent(List<EmployeeListContentResponseDtoSchemaEntity> content) {
    this.content = content;
  }
  
  public List<EmployeeListContentResponseDtoSchemaEntity> getContent() {
    return this.content;
  }
	
	public void getPageable() {
	  return this.pageable;
	}
	
	public void setPageable(void pageable) {
	  this.pageable = pageable;
	}
	
	public long getTotalElements() {
	  return this.totalElements;
	}
	
	public void setTotalElements(long totalElements) {
	  this.totalElements = totalElements;
	}
	

}
