package com.example.domain.myapp.employeemanagement.dataaccess.api.repo;

import static com.querydsl.core.alias.Alias.$;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.querydsl.jpa.impl.JPAQuery;
import java.util.Iterator;

import com.example.domain.myapp.employeemanagement.common.api.EmployeeListResponseDtoSchema;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeListResponseDtoSchemaEntity;
import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeListResponseDtoSchemaSearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;

/**
 * {@link DefaultRepository} for {@link EmployeeListResponseDtoSchemaEntity}
  */
public interface EmployeeListResponseDtoSchemaRepository extends DefaultRepository<EmployeeListResponseDtoSchemaEntity> {

  /**
   * @param criteria the {@link EmployeeListResponseDtoSchemaSearchCriteriaTo} with the criteria to search.
   * @return the {@link Page} of the {@link EmployeeListResponseDtoSchemaEntity} objects that matched the search.
   * If no pageable is set, it will return a unique page with all the objects that matched the search.
   */
  default Page<EmployeeListResponseDtoSchemaEntity> findByCriteria(EmployeeListResponseDtoSchemaSearchCriteriaTo criteria) {

    EmployeeListResponseDtoSchemaEntity alias = newDslAlias();
    JPAQuery<EmployeeListResponseDtoSchemaEntity> query = newDslQuery(alias);

Void pageable = criteria.getPageable();
if (pageable != null) {
query.where($(alias.getPageable()).eq(pageable));
}Long totalElements = criteria.getTotalElements();
if (totalElements != null) {
query.where($(alias.getTotalElements()).eq(totalElements));
}    if (criteria.getPageable() == null) {
      criteria.setPageable(PageRequest.of(0, Integer.MAX_VALUE));
    } else {
      addOrderBy(query, alias, criteria.getPageable().getSort());
    }
    
    return QueryUtil.get().findPaginated(criteria.getPageable(), query, true);
  }
  
  /**
   * Add sorting to the given query on the given alias
   * 
   * @param query to add sorting to
   * @param alias to retrieve columns from for sorting
   * @param sort specification of sorting
   */
  public default void addOrderBy(JPAQuery<EmployeeListResponseDtoSchemaEntity> query, EmployeeListResponseDtoSchemaEntity alias, Sort sort) {
    if (sort != null && sort.isSorted()) {
      Iterator<Order> it = sort.iterator();
      while (it.hasNext()) {
        Order next = it.next();
        switch(next.getProperty()) {
	  case "pageable":
            if (next.isAscending()) {
                query.orderBy($(alias.getPageable()).asc());
            } else {
                query.orderBy($(alias.getPageable()).desc());
            }   
          break;
	  case "totalElements":
            if (next.isAscending()) {
                query.orderBy($(alias.getTotalElements()).asc());
            } else {
                query.orderBy($(alias.getTotalElements()).desc());
            }   
          break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '"+next.getProperty()+"'");
        }
      }
    }
}

}