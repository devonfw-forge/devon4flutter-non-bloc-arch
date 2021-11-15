package com.flutter.counter.common.api;

import com.flutter.general.common.api.ApplicationEntity;
import java.util.List;
import java.util.Set;
import java.math.BigDecimal;

public interface Counter extends ApplicationEntity {

	public BigInteger getAmount();

	public void setAmount(BigInteger amount);

}
