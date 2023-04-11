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

import com.example.domain.myapp.employeemanagement.common.api.EmployeeDetailResponseDtoSchema;
import com.example.domain.myapp.employeemanagement.dataaccess.api.EmployeeDetailResponseDtoSchemaEntity;
import com.example.domain.myapp.employeemanagement.logic.api.to.EmployeeDetailResponseDtoSchemaSearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;

/**
 * {@link DefaultRepository} for {@link EmployeeDetailResponseDtoSchemaEntity}
  */
public interface EmployeeDetailResponseDtoSchemaRepository extends DefaultRepository<EmployeeDetailResponseDtoSchemaEntity> {

  /**
   * @param criteria the {@link EmployeeDetailResponseDtoSchemaSearchCriteriaTo} with the criteria to search.
   * @return the {@link Page} of the {@link EmployeeDetailResponseDtoSchemaEntity} objects that matched the search.
   * If no pageable is set, it will return a unique page with all the objects that matched the search.
   */
  default Page<EmployeeDetailResponseDtoSchemaEntity> findByCriteria(EmployeeDetailResponseDtoSchemaSearchCriteriaTo criteria) {

    EmployeeDetailResponseDtoSchemaEntity alias = newDslAlias();
    JPAQuery<EmployeeDetailResponseDtoSchemaEntity> query = newDslQuery(alias);

Long modificationCounter = criteria.getModificationCounter();
if (modificationCounter != null) {
query.where($(alias.getModificationCounter()).eq(modificationCounter));
}Long id = criteria.getId();
if (id != null) {
query.where($(alias.getId()).eq(id));
}Long employeeId = criteria.getEmployeeId();
if (employeeId != null) {
query.where($(alias.getEmployeeId()).eq(employeeId));
}String name = criteria.getName();
if (name != null && !name.isEmpty()) {
QueryUtil.get().whereString(query, $(alias.getName()), name, criteria.getNameOption());
}String surname = criteria.getSurname();
if (surname != null && !surname.isEmpty()) {
QueryUtil.get().whereString(query, $(alias.getSurname()), surname, criteria.getSurnameOption());
}String email = criteria.getEmail();
if (email != null && !email.isEmpty()) {
QueryUtil.get().whereString(query, $(alias.getEmail()), email, criteria.getEmailOption());
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
  public default void addOrderBy(JPAQuery<EmployeeDetailResponseDtoSchemaEntity> query, EmployeeDetailResponseDtoSchemaEntity alias, Sort sort) {
    if (sort != null && sort.isSorted()) {
      Iterator<Order> it = sort.iterator();
      while (it.hasNext()) {
        Order next = it.next();
        switch(next.getProperty()) {
	  case "modificationCounter":
            if (next.isAscending()) {
                query.orderBy($(alias.getModificationCounter()).asc());
            } else {
                query.orderBy($(alias.getModificationCounter()).desc());
            }   
          break;
	  case "id":
            if (next.isAscending()) {
                query.orderBy($(alias.getId()).asc());
            } else {
                query.orderBy($(alias.getId()).desc());
            }   
          break;
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
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '"+next.getProperty()+"'");
        }
      }
    }
}

}