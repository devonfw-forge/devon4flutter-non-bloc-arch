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

import com.example.domain.myapp.employeemanagement.common.api.PageableSortDtoSchema;
import com.example.domain.myapp.employeemanagement.dataaccess.api.PageableSortDtoSchemaEntity;
import com.example.domain.myapp.employeemanagement.logic.api.to.PageableSortDtoSchemaSearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;

/**
 * {@link DefaultRepository} for {@link PageableSortDtoSchemaEntity}
  */
public interface PageableSortDtoSchemaRepository extends DefaultRepository<PageableSortDtoSchemaEntity> {

  /**
   * @param criteria the {@link PageableSortDtoSchemaSearchCriteriaTo} with the criteria to search.
   * @return the {@link Page} of the {@link PageableSortDtoSchemaEntity} objects that matched the search.
   * If no pageable is set, it will return a unique page with all the objects that matched the search.
   */
  default Page<PageableSortDtoSchemaEntity> findByCriteria(PageableSortDtoSchemaSearchCriteriaTo criteria) {

    PageableSortDtoSchemaEntity alias = newDslAlias();
    JPAQuery<PageableSortDtoSchemaEntity> query = newDslQuery(alias);

String property = criteria.getProperty();
if (property != null && !property.isEmpty()) {
QueryUtil.get().whereString(query, $(alias.getProperty()), property, criteria.getPropertyOption());
}String direction = criteria.getDirection();
if (direction != null && !direction.isEmpty()) {
QueryUtil.get().whereString(query, $(alias.getDirection()), direction, criteria.getDirectionOption());
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
  public default void addOrderBy(JPAQuery<PageableSortDtoSchemaEntity> query, PageableSortDtoSchemaEntity alias, Sort sort) {
    if (sort != null && sort.isSorted()) {
      Iterator<Order> it = sort.iterator();
      while (it.hasNext()) {
        Order next = it.next();
        switch(next.getProperty()) {
	  case "property":
            if (next.isAscending()) {
                query.orderBy($(alias.getProperty()).asc());
            } else {
                query.orderBy($(alias.getProperty()).desc());
            }   
          break;
	  case "direction":
            if (next.isAscending()) {
                query.orderBy($(alias.getDirection()).asc());
            } else {
                query.orderBy($(alias.getDirection()).desc());
            }   
          break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '"+next.getProperty()+"'");
        }
      }
    }
}

}