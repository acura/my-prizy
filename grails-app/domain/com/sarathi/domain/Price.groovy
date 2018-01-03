package com.sarathi.domain

import java.text.NumberFormat;

class Price {

	BigDecimal price;
	static belongsTo = [product: Product]

	static constraints = {
		price nullable:false, matches: '^[0-9]$', scale: 2
			
	}
}
