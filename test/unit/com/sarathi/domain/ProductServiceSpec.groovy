

package com.sarathi.domain

import com.sun.xml.internal.ws.client.sei.ValueSetter.ReturnValue;

import javax.validation.constraints.AssertTrue;

import grails.test.mixin.TestFor
import groovy.mock.interceptor.MockFor;
import spock.lang.Specification


@TestFor(ProductService)
@Mock(Product)
class ProductServiceSpec extends Specification {
	
	void "test save product method"() {
		given: "create product"
			Product product = new Product(barcode: "123qwe", productName: "test pro", 
				description: "sdfdsfds")
  
		when: "service save() is called"
			boolean res = service.saveProduct(product)
  
		then: "Expect false"
			res == false
	}
	
	void "test update product method"() {
		given:""
			Product product = new Product(barcode: "123qwe", productName: "test pro",
				description: "sdfdsfds")
			service.saveProduct(product)
		when: "call service update method"
			boolean res = service.updateProduct(product)
		then: "expect true"
			res == true
	}
	
}
