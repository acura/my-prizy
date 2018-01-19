package com.prizy.strategy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

@FunctionalInterface
public interface PricingStategy {

	public BigDecimal calculate(List<BigDecimal> list);

	default BigDecimal calculateAverage(List<BigDecimal> list) {
		if (list == null || list.isEmpty()) {
			return new BigDecimal(0);
		}

		BigDecimal sum = new BigDecimal(0);
		for (BigDecimal temp : list) {
			sum = sum.add(temp);
		}

		BigDecimal size = new BigDecimal(list.size());
		return (sum.divide(size, MathContext.DECIMAL128)).setScale(2,
				RoundingMode.CEILING);
	}

}
