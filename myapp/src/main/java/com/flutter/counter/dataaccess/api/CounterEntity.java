package com.flutter.counter.dataaccess.api;

import com.flutter.counter.common.api.Counter;
import com.flutter.general.dataaccess.api.ApplicationPersistenceEntity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Transient;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

 /**
 * Data access object for Counter entities
 */
@Entity
@javax.persistence.Table(name = "Counter")
public class CounterEntity extends ApplicationPersistenceEntity implements Counter {

  private static final long serialVersionUID = 1L;

	private BigInteger amount;

	public BigInteger getAmount() {
	  return this.amount;
	}
	
	public void setAmount(BigInteger amount) {
	  this.amount = amount;
	}
	

}
