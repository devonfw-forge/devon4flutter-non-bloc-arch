package com.flutter.counter.logic.api.usecase;

import com.flutter.counter.logic.api.to.CounterEto;

/**
 * Interface of UcManageCounter to centralize documentation and signatures of methods.
 */
public interface UcManageCounter {

	/**
	 * Deletes a counter from the database by its id 'counterId'.
	 *
	 * @param counterId Id of the counter to delete
	 * @return boolean <code>true</code> if the counter can be deleted, <code>false</code> otherwise
	 */
	boolean deleteCounter(long counterId);

	/**
	 * Saves a counter and store it in the database.
	 *
	 * @param counter the {@link CounterEto} to create.
	 * @return the new {@link CounterEto} that has been saved with ID and version.
	 */
	CounterEto saveCounter(CounterEto counter);

}
