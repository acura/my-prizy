package com.prizy.domain

class Price {

	BigDecimal price;
	String storeName
	String notes
	static belongsTo = [product: Product]

	static constraints = {
		price nullable:false, matches: '^[0-9]$', scale: 2
	}

}