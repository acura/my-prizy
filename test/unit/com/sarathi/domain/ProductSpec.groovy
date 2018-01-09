package com.sarathi.domain

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification


@TestMixin(GrailsUnitTestMixin)
class ProductSpec extends Specification {

	def product

	def setup() {
		mockForConstraintsTests(Product)
		product = new Product(barcode : 'NOKIA6ANDROID', productName: 'Nokia 6', description: 'Android Phone' );
	}

	void "test for barcode"() {
		when: "when barcode is blank"
			def testProduct = new Product(barcode : '', productName: 'Nokia 6', description: 'Android Phone' );
			mockForConstraintsTests(Product, [testProduct])
			def result = testProduct.validate()

		then:
			testProduct.errors['barcode'] == 'nullable'
			testProduct.errors.getFieldError().field== 'barcode'
			result == false


		when: "when barcode is null"
			testProduct = new Product( productName: 'Nokia 6',	description: 'Android Phone' );
			mockForConstraintsTests(Product, [testProduct])
			result = testProduct.validate()
			
		then:
			testProduct.errors['barcode'] == 'nullable'
			testProduct.errors.getFieldError().field== 'barcode'
			result == false
			
		when: "when barcode length is less than 3"
			testProduct = new Product(barcode : '1q', productName: 'Nokia 6', description: 'Android Phone' );
			mockForConstraintsTests(Product, [testProduct])
			result = testProduct.validate()

		then:
			testProduct.errors['barcode'] == 'size'
			testProduct.errors.getFieldError().field== 'barcode'
			result == false
			
		when: "when barcode length is greater than 50"
			testProduct = new Product(barcode : 'NOKIA6ANDROIDNOKIA6ANDROIDNOKIA6ANDROIDNOKIA6ANDROIDNOKIA6ANDROID', 
				productName: 'Nokia 6', description: 'Android Phone' );
			mockForConstraintsTests(Product, [testProduct])
			result = testProduct.validate()

		then:
			testProduct.errors['barcode'] == 'size'
			testProduct.errors.getFieldError().field== 'barcode'
			result == false
	}

	void "test for productName"() {

		when: "when productName is blank"
			def testProduct = new Product(barcode : 'NOKIA6ANDROID', productName: '', description: 'Android Phone' );
			mockForConstraintsTests(Product, [testProduct])
			def result = testProduct.validate()

		then:
			testProduct.errors['productName'] == 'nullable'
			testProduct.errors.getFieldError().field== 'productName'
			result == false


		when: "when productName is null"
			testProduct = new Product( barcode : 'NOKIA6ANDROID', description: 'Android Phone' );
			mockForConstraintsTests(Product, [testProduct])
			result = testProduct.validate()

		then:
			testProduct.errors['productName'] == 'nullable'
			testProduct.errors.getFieldError().field== 'productName'
			result == false
	}

	void "test for description attribute"() {

		given:
			Product testProduct
			def result
			
		when: "when description is blank"
			testProduct = new Product(barcode : 'NOKIA6ANDROID', productName: 'Nokia 6', description: '' );
			mockForConstraintsTests(Product, [testProduct])
			result = testProduct.validate()

		then:
			testProduct.errors['description'] == 'nullable'
			testProduct.errors.getFieldError().field== 'description'
			result == false

		when: "when description is null"
			testProduct = new Product( barcode : 'NOKIA6ANDROID', productName: 'Nokia 6' );
			mockForConstraintsTests(Product, [testProduct])
			result = testProduct.validate()

		then:
			testProduct.errors['description'] == 'nullable'
			testProduct.errors.getFieldError().field== 'description'
			result == false

		when: "when description is of length more than 50"
			testProduct = new Product( barcode : 'NOKIA6ANDROID', productName: 'Nokia 6', 
				description: 'The Nokia 6 is powered by 1.4GHz octa-core Qualcomm Snapdragon 430 processor processor and it comes with 3GB of RAM.');
			mockForConstraintsTests(Product, [testProduct])
			result = testProduct.validate()

		then:
			testProduct.errors['description'] == 'size'
			testProduct.errors.getFieldError().field== 'description'
			result == false
	}

	void "test for all valid data"() {
		when: "when all data is valid"
			def testProduct = new Product(barcode : 'NOKIA6ANDROID', productName: 'Nokia 6', 
				description: 'Android Phone' );
			mockForConstraintsTests(Product, [testProduct])
			def result = testProduct.validate()

		then: "validation should be true"
			testProduct.errors['barcode'] == null
			testProduct.errors['productName'] == null
			testProduct.errors['description'] == null
			result == true
	}
}
