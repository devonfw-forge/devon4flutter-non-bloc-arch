package com.flutter.counter.logic.api.usecase;

import com.flutter.counter.logic.api.to.CounterEto;
import com.flutter.counter.logic.api.to.CounterSearchCriteriaTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UcFindCounter {

	/**
	 * Returns a Counter by its id 'id'.
	 *
	 * @param id The id 'id' of the Counter.
	 * @return The {@link CounterEto} with id 'id'
	 */
	CounterEto findCounter(long id);

	/**
	 * Returns a paginated list of Counters matching the search criteria.
	 *
	 * @param criteria the {@link CounterSearchCriteriaTo}.
	 * @return the {@link List} of matching {@link CounterEto}s.
	 */
	Page<CounterEto> findCounters(CounterSearchCriteriaTo criteria);

}
