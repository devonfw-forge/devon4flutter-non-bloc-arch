package com.flutter.counter.logic.base.usecase;

import com.flutter.general.logic.base.AbstractUc;
import com.flutter.counter.dataaccess.api.repo.CounterRepository;

import javax.inject.Inject;

/**
 * Abstract use case for Counters, which provides access to the commonly necessary data access objects.
 */
public class AbstractCounterUc extends AbstractUc {

	  /** @see #getCounterRepository() */
	  @Inject
    private CounterRepository counterRepository;

    /**
     * Returns the field 'counterRepository'.
     * @return the {@link CounterRepository} instance.
     */
    public CounterRepository getCounterRepository() {

      return this.counterRepository;
    }

}
