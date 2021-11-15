package com.flutter.counter.logic.api;

import com.flutter.counter.logic.api.usecase.UcFindCounter;
import com.flutter.counter.logic.api.usecase.UcManageCounter;
import java.util.List;

/**
 * Interface for Counter component.
 */
public interface Counter extends UcFindCounter, UcManageCounter {

	void getCounter();

	void resetCounter();

	void incCounter(CounterEto counter);

}
