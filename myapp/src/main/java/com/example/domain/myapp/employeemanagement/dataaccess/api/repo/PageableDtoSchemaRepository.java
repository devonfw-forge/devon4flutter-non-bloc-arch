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

import com.example.domain.myapp.employeemanagement.common.api.PageableDtoSchema;
import com.example.domain.myapp.employeemanagement.dataaccess.api.PageableDtoSchemaEntity;
import com.example.domain.myapp.employeemanagement.logic.api.to.PageableDtoSchemaSearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;

/**
 * {@link DefaultRepository} for {@link PageableDtoSchemaEntity}
  */
public interface PageableDtoSchemaRepository extends DefaultRepository<PageableDtoSchemaEntity> {

  /**
   * @param criteria the {@link PageableDtoSchemaSearchCriteriaTo} with the criteria to search.
   * @return the {@link Page} of the {@link PageableDtoSchemaEntity} objects that matched the search.
   * If no pageable is set, it will return a unique page with all the objects that matched the search.
   */
  default Page<PageableDtoSchemaEntity> findByCriteria(PageableDtoSchemaSearchCriteriaTo criteria) {

    PageableDtoSchemaEntity alias = newDslAlias();
    JPAQuery<PageableDtoSchemaEntity> query = newDslQuery(alias);

Long pageSize = criteria.getPageSize();
if (pageSize != null) {
query.where($(alias.getPageSize()).eq(pageSize));
}Long pageNumber = criteria.getPageNumber();
if (pageNumber != null) {
query.where($(alias.getPageNumber()).eq(pageNumber));
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
  public default void addOrderBy(JPAQuery<PageableDtoSchemaEntity> query, PageableDtoSchemaEntity alias, Sort sort) {
    if (sort != null && sort.isSorted()) {
      Iterator<Order> it = sort.iterator();
      while (it.hasNext()) {
        Order next = it.next();
        switch(next.getProperty()) {
	  case "pageSize":
            if (next.isAscending()) {
                query.orderBy($(alias.getPageSize()).asc());
            } else {
                query.orderBy($(alias.getPageSize()).desc());
            }   
          break;
	  case "pageNumber":
            if (next.isAscending()) {
                query.orderBy($(alias.getPageNumber()).asc());
            } else {
                query.orderBy($(alias.getPageNumber()).desc());
            }   
          break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '"+next.getProperty()+"'");
        }
      }
    }
}

}