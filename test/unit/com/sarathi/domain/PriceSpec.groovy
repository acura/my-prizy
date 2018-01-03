package com.sarathi.domain

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification



@TestMixin(GrailsUnitTestMixin)
class PriceSpec extends Specification {

	def price

	def setup() {
		mockForConstraintsTests(Price)
		price = new Price(price : new BigDecimal("100.2"));
	}

	def cleanup() {
	}

	void "test for price"() {
		when: "price is blank"
		Product product = new Product(barcode: "NOKIA6ANDROID", productName: "Nokia 6",
			description: "Android Phone")
			Price testPrice = new Price(price : '', product: product);
			mockForConstraintsTests(Price, [testPrice])
			def result = testPrice.validate()

		then:
			testPrice.errors['price'] == 'nullable'
			testPrice.errors.getFieldError().field== 'price'
			result == false

		when: "price is null"
			testPrice = new Price(product: product);
			mockForConstraintsTests(Price, [testPrice])
			result = testPrice.validate()

		then:
			testPrice.errors['price'] == 'nullable'
			testPrice.errors.getFieldError().field== 'price'
			result == false
	}

	void "test for all valid data "() {
		when: "all valid data"
			Product product = new Product(barcode: "NOKIA6ANDROID", productName: "Nokia 6",
						description: "Android Phone")
			Price testPrice = new Price(price : new BigDecimal("100.2"), product: product);
			mockForConstraintsTests(Price, [testPrice])
			def result = testPrice.validate()

		then: "validation result should be true"
			testPrice.errors['price'] == null
			result == true
	}
}
