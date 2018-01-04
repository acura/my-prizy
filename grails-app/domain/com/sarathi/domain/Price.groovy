package com.sarathi.domain

class Price {

	BigDecimal price;
	static belongsTo = [product: Product]

	static constraints = {
		price nullable:false, matches: '^[0-9]$', scale: 2
			
	}
}
