package com.sarathi.domain

import java.text.NumberFormat;

class Price {

	BigDecimal price;
	static belongsTo = [product: Product]

	static constraints = {
		price blank: false,size: 1..10,
		matches: '^[0-9]$'
	}
	
	/*@Override
	public String toString() {
		//return "\$" + NumberFormat.getCurrencyInstance().format(price);
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Currency.getInstance("USD"));
		numberFormat.setMaximumFractionDigits(3);
		numberFormat.setMinimumFractionDigits(3);
		return numberFormat.format(price);
		
	}*/
}
