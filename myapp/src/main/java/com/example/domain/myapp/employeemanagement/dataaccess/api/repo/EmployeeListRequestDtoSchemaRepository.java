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

import com.example.domain.myapp.employeemanagement.common.api.EmployeeListRequestDtoSchema;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeListRequestDtoSchemaEntity;
import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeListRequestDtoSchemaSearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;

/**
 * {@link DefaultRepository} for {@link EmployeeListRequestDtoSchemaEntity}
  */
public interface EmployeeListRequestDtoSchemaRepository extends DefaultRepository<EmployeeListRequestDtoSchemaEntity> {

  /**
   * @param criteria the {@link EmployeeListRequestDtoSchemaSearchCriteriaTo} with the criteria to search.
   * @return the {@link Page} of the {@link EmployeeListRequestDtoSchemaEntity} objects that matched the search.
   * If no pageable is set, it will return a unique page with all the objects that matched the search.
   */
  default Page<EmployeeListRequestDtoSchemaEntity> findByCriteria(EmployeeListRequestDtoSchemaSearchCriteriaTo criteria) {

    EmployeeListRequestDtoSchemaEntity alias = newDslAlias();
    JPAQuery<EmployeeListRequestDtoSchemaEntity> query = newDslQuery(alias);

String employeeId = criteria.getEmployeeId();
if (employeeId != null && !employeeId.isEmpty()) {
QueryUtil.get().whereString(query, $(alias.getEmployeeId()), employeeId, criteria.getEmployeeIdOption());
}String name = criteria.getName();
if (name != null && !name.isEmpty()) {
QueryUtil.get().whereString(query, $(alias.getName()), name, criteria.getNameOption());
}String surname = criteria.getSurname();
if (surname != null && !surname.isEmpty()) {
QueryUtil.get().whereString(query, $(alias.getSurname()), surname, criteria.getSurnameOption());
}String email = criteria.getEmail();
if (email != null && !email.isEmpty()) {
QueryUtil.get().whereString(query, $(alias.getEmail()), email, criteria.getEmailOption());
}Void pageable = criteria.getPageable();
if (pageable != null) {
query.where($(alias.getPageable()).eq(pageable));
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
  public default void addOrderBy(JPAQuery<EmployeeListRequestDtoSchemaEntity> query, EmployeeListRequestDtoSchemaEntity alias, Sort sort) {
    if (sort != null && sort.isSorted()) {
      Iterator<Order> it = sort.iterator();
      while (it.hasNext()) {
        Order next = it.next();
        switch(next.getProperty()) {
	  case "employeeId":
            if (next.isAscending()) {
                query.orderBy($(alias.getEmployeeId()).asc());
            } else {
                query.orderBy($(alias.getEmployeeId()).desc());
            }   
          break;
	  case "name":
            if (next.isAscending()) {
                query.orderBy($(alias.getName()).asc());
            } else {
                query.orderBy($(alias.getName()).desc());
            }   
          break;
	  case "surname":
            if (next.isAscending()) {
                query.orderBy($(alias.getSurname()).asc());
            } else {
                query.orderBy($(alias.getSurname()).desc());
            }   
          break;
	  case "email":
            if (next.isAscending()) {
                query.orderBy($(alias.getEmail()).asc());
            } else {
                query.orderBy($(alias.getEmail()).desc());
            }   
          break;
	  case "pageable":
            if (next.isAscending()) {
                query.orderBy($(alias.getPageable()).asc());
            } else {
                query.orderBy($(alias.getPageable()).desc());
            }   
          break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '"+next.getProperty()+"'");
        }
      }
    }
}

}