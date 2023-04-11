package com.flutter.counter.logic.impl.usecase;

import com.flutter.counter.logic.api.to.CounterEto;
import com.flutter.counter.logic.api.usecase.UcManageCounter;
import com.flutter.counter.logic.base.usecase.AbstractCounterUc;
import com.flutter.counter.dataaccess.api.CounterEntity;
import org.springframework.validation.annotation.Validated;
import java.util.Objects;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use case implementation for modifying and deleting Counters
 */
@Named
@Validated
@Transactional
public class UcManageCounterImpl extends AbstractCounterUc implements UcManageCounter {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageCounterImpl.class);

  @Override
  public boolean deleteCounter(long counterId) {

    CounterEntity counter = getCounterRepository().find(counterId);
    getCounterRepository().delete(counter);
    LOG.debug("The counter with id '{}' has been deleted.", counterId);
    return true;
  }

  @Override
  public CounterEto saveCounter(CounterEto counter) {

   Objects.requireNonNull(counter, "counter");

	 CounterEntity counterEntity = getBeanMapper().map(counter, CounterEntity.class);

   //initialize, validate counterEntity here if necessary
   CounterEntity resultEntity = getCounterRepository().save(counterEntity);
   LOG.debug("Counter with id '{}' has been created.",resultEntity.getId());
   return getBeanMapper().map(resultEntity, CounterEto.class);
  }
}
