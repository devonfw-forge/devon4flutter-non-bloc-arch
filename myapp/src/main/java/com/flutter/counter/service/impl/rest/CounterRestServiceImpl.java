package com.flutter.counter.service.impl.rest;

import javax.inject.Inject;
import javax.inject.Named;

import com.flutter.counter.logic.api.Counter;
import com.flutter.counter.logic.api.to.CounterEto;
import com.flutter.counter.logic.api.to.CounterSearchCriteriaTo;
import com.flutter.counter.service.api.rest.CounterRestService;

import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * The service implementation for REST calls in order to execute the logic of component {@link Counter}.
 */
@Named("CounterRestService")
public class CounterRestServiceImpl implements CounterRestService {

  @Inject
  private Counter counter;

  @Override
  public CounterEto getCounter(long id) {

    return this.counter.findCounter(id);
  }

  @Override
  public CounterEto saveCounter(CounterEto counter) {

    return this.counter.saveCounter(counter);
  }

  @Override
  public void deleteCounter(long id) {

    this.counter.deleteCounter(id);
  }

  @Override
  public Page<CounterEto> findCountersByPost(CounterSearchCriteriaTo searchCriteriaTo) {

    return this.counter.findCounters(searchCriteriaTo);
  }
  
  @Override
  public void getCounter(
  			) {

     this.counter.getCounter(
 			);
  }
  @Override
  public void resetCounter(
  			) {

     this.counter.resetCounter(
 			);
  }
  @Override
  public void incCounter(
  		  CounterEto counter
  			) {

     this.counter.incCounter(
  		  counter
 			);
  }

}