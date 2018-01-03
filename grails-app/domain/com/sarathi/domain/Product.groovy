package com.sarathi.domain

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.CascadeType

class Product {

	String barcode
	String productName
	String description

	static hasMany = [prices:Price]

	static constraints = {
		productName blank: false, size: 3..50
		description size: 0..50, nullable: false, blank: false
		barcode blank: false, size: 3..50,
			matches: '^[a-zA-Z0-9]{4,50}$'

	}

	static mapping = {
		prices CascadeType: 'all-delete-orphan'
		id name: 'barcode', generator: 'assigned', type: 'String'
		prices cascade: "all-delete-orphan" 
	}

	def getPK() {
		["barcode":barcode]
	}
	
	@Override
	public String toString() {
		return productName+" ( "+barcode+" )"
	}
}
