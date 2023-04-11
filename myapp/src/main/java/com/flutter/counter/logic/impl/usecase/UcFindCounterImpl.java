package com.flutter.counter.logic.impl.usecase;

import com.flutter.counter.logic.api.to.CounterEto;
import com.flutter.counter.logic.api.usecase.UcFindCounter;
import com.flutter.counter.logic.base.usecase.AbstractCounterUc;
import com.flutter.counter.dataaccess.api.CounterEntity;
import com.flutter.counter.logic.api.to.CounterSearchCriteriaTo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use case implementation for searching, filtering and getting Counters
 */
@Named
@Validated
@Transactional
public class UcFindCounterImpl extends AbstractCounterUc implements UcFindCounter {

	  /** Logger instance. */
    private static final Logger LOG = LoggerFactory.getLogger(UcFindCounterImpl.class);


    @Override
    public CounterEto findCounter(long id) {
      LOG.debug("Get Counter with id {} from database.", id);
      Optional<CounterEntity> foundEntity = getCounterRepository().findById(id);
      if (foundEntity.isPresent())
        return getBeanMapper().map(foundEntity.get(), CounterEto.class);
      else
        return null;
    }

    @Override
    public Page<CounterEto> findCounters(CounterSearchCriteriaTo criteria) {
      Page<CounterEntity> counters = getCounterRepository().findByCriteria(criteria);
    return mapPaginatedEntityList(counters, CounterEto.class);
  }

}
