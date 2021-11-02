package com.flutter.counter.dataaccess.api.repo;

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

import com.flutter.counter.common.api.Counter;
import com.flutter.counter.dataaccess.api.CounterEntity;
import com.flutter.counter.logic.api.to.CounterSearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;

/**
 * {@link DefaultRepository} for {@link CounterEntity}
  */
public interface CounterRepository extends DefaultRepository<CounterEntity> {

  /**
   * @param criteria the {@link CounterSearchCriteriaTo} with the criteria to search.
   * @return the {@link Page} of the {@link CounterEntity} objects that matched the search.
   * If no pageable is set, it will return a unique page with all the objects that matched the search.
   */
  default Page<CounterEntity> findByCriteria(CounterSearchCriteriaTo criteria) {

    CounterEntity alias = newDslAlias();
    JPAQuery<CounterEntity> query = newDslQuery(alias);

BigInteger amount = criteria.getAmount();
if (amount != null) {
query.where($(alias.getAmount()).eq(amount));
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
  public default void addOrderBy(JPAQuery<CounterEntity> query, CounterEntity alias, Sort sort) {
    if (sort != null && sort.isSorted()) {
      Iterator<Order> it = sort.iterator();
      while (it.hasNext()) {
        Order next = it.next();
        switch(next.getProperty()) {
	  case "amount":
            if (next.isAscending()) {
                query.orderBy($(alias.getAmount()).asc());
            } else {
                query.orderBy($(alias.getAmount()).desc());
            }   
          break;
          default:
            throw new IllegalArgumentException("Sorted by the unknown property '"+next.getProperty()+"'");
        }
      }
    }
}

}