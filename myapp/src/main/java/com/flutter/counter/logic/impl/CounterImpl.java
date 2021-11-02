package com.flutter.counter.logic.impl;

import com.flutter.general.logic.base.AbstractComponentFacade;
import com.flutter.counter.logic.api.Counter;
import com.flutter.counter.logic.api.to.CounterEto;
import com.flutter.counter.logic.api.usecase.UcFindCounter;
import com.flutter.counter.logic.api.usecase.UcManageCounter;
import com.flutter.counter.logic.api.to.CounterSearchCriteriaTo;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Implementation of component interface of counter
 */
@Named
public class CounterImpl extends AbstractComponentFacade implements Counter {

    @Inject
    private UcFindCounter ucFindCounter;

    @Inject
    private UcManageCounter ucManageCounter;

    @Override
    public CounterEto findCounter(long id) {

      return this.ucFindCounter.findCounter(id);
    }

    @Override
    public Page<CounterEto> findCounters(CounterSearchCriteriaTo criteria) {
      return this.ucFindCounter.findCounters(criteria);
    }

    @Override
    public CounterEto saveCounter(CounterEto counter) {

      return this.ucManageCounter.saveCounter(counter);
    }

    @Override
    public boolean deleteCounter(long id) {

      return this.ucManageCounter.deleteCounter(id);
    }
    
  	@Override
  	public void getCounter(
)
			{
// TODO getCounter
}
  		
  	@Override
  	public void resetCounter(
)
			{
// TODO resetCounter
}
  		
  	@Override
  	public void incCounter(
  		    CounterEto counter
  			)
			{
// TODO incCounter
}
  		
}
