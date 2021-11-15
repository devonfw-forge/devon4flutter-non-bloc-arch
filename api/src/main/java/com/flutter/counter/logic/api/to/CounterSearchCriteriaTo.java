package com.flutter.counter.logic.api.to;

import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;
import com.flutter.general.common.api.to.AbstractSearchCriteriaTo;
import java.util.List;
import java.util.Set;
import java.math.BigDecimal;

/**
 * {@link SearchCriteriaTo} to find instances of {@link com.flutter.counter.common.api.Counter}s.
 */
public class CounterSearchCriteriaTo extends AbstractSearchCriteriaTo {

	private static final long serialVersionUID = 1L;

	private BigInteger amount;

	/**
	 * The constructor.
	 */
	public CounterSearchCriteriaTo() {

    super();
  }

	public BigInteger getAmount() {
		return this.amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

}
