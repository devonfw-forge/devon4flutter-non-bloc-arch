package com.example.domain.myapp.employeemanagement.dataaccess.api;

import com.example.domain.myapp.employeemanagement.common.api.PageableDtoSchema;
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
 * Entity definition of Pageable
 */
@Entity
@javax.persistence.Table(name = "PageableDtoSchema")
public class PageableDtoSchemaEntity extends ApplicationPersistenceEntity implements PageableDtoSchema {

  private static final long serialVersionUID = 1L;

	private long pageSize;
	private long pageNumber;
	private List<PageableSortDtoSchemaEntity> sort; 

	public long getPageSize() {
	  return this.pageSize;
	}
	
	public void setPageSize(long pageSize) {
	  this.pageSize = pageSize;
	}
	
	public long getPageNumber() {
	  return this.pageNumber;
	}
	
	public void setPageNumber(long pageNumber) {
	  this.pageNumber = pageNumber;
	}
	
  public void setSort(List<PageableSortDtoSchemaEntity> sort) {
    this.sort = sort;
  }
  
  public List<PageableSortDtoSchemaEntity> getSort() {
    return this.sort;
  }
	

}
