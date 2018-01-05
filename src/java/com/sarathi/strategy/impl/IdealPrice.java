package com.sarathi.strategy.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import com.sarathi.strategy.PricingStategy;

public class IdealPrice implements PricingStategy {

	public static final String HINT="Ideal Price. This price is calculated by taking all the prices of this product, "
			+ "removing the 2 highest and 2 lowest, then doing an average with the rest and adding 20% to it.";
	
	@Override
	public synchronized BigDecimal calculate(List<BigDecimal> list) {
		Collections.sort(list);
		if(list.size() <=4) {
			return new BigDecimal(-1);
		}
		
		BigDecimal average = calculateAverage(list.subList(2, list.size()-2));
		BigDecimal twentyPercentage = average.divide(new BigDecimal("5"));
		average = average.add(twentyPercentage);
		return average.setScale(2, RoundingMode.CEILING);
	}
}
