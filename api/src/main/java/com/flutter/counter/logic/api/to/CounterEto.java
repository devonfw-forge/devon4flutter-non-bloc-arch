package com.flutter.counter.logic.api.to;

import com.devonfw.module.basic.common.api.to.AbstractEto;
import com.flutter.counter.common.api.Counter;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Entity transport object of Counter
 */
public class CounterEto extends AbstractEto implements Counter {

	private static final long serialVersionUID = 1L;

	private BigInteger amount;

	public BigInteger getAmount() {
		return this.amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
	  result = prime * result + ((this.amount == null) ? 0 : this.amount.hashCode());
        return result;
    }

	@Override
	public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    // class check will be done by super type EntityTo!
    if (!super.equals(obj)) {
      return false;
    }
    CounterEto other = (CounterEto) obj;
	if (this.amount == null) {
		if (other.amount != null) {
		return false;
		}
	} else if(!this.amount.equals(other.amount)){
		return false;
	}
    return true;
  }

}
