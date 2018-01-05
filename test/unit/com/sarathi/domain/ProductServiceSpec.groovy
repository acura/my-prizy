package com.sarathi.domain
import grails.test.mixin.*
import spock.lang.Specification

import com.sarathi.strategy.impl.IdealPrice
import com.sarathi.strategy.impl.MeanPrice


@TestFor(ProductService)
@Mock([Product, Price])
class ProductServiceSpec extends Specification {
	
	void "test save product method"() {
		given: "create product"
			Product product = new Product(barcode: "NOKIA6ANDROID", productName: "Nokia 6", 
				description: "Android Phone")
  
		when: "service save() is called"
			boolean res = service.saveProduct(product)
  
		then: "Expect false"
			res == true
	}
	
	void "test delete product method"() {
		given:
			def product
			def productReturn
			int productCount
			int countReturn

		when:"The delete product action is executed with an invalid barcode id"
			productCount = Product.count
			service.deleteProduct(new Product(barcode:"NOKIA6ANDROID"));
			productReturn = service.findProduct("ABX123XYZIWE11");
			countReturn = Product.count
		then:"The product is not deleted from the database as it is not present and the product count is not changed"
			productReturn == null
			countReturn == productCount
		
		when:"The delete product action is executed with a valid instance"
			product = new Product(barcode: "NOKIA6ANDROID", productName: "Nokia 6", 
				description: "Android Phone")
			service.saveProduct(product)
			productCount = Product.count
			service.deleteProduct(product);
			productReturn = service.findProduct(product.barcode);
			countReturn = Product.count
		then:"The product is deleted from the database and product count is decresed by one"
			productReturn == null
			countReturn == productCount-1
	}
	
	void "test getPrice method"() {
		given:
			Product product = new Product(barcode: "NOKIA6ANDROID", productName: "Nokia 6", 
				description: "Android Phone")
			def price
			BigDecimal res
			service.saveProduct(product)
			for(int i=11;i<=20;i++) {
				price = new Price(price: i, product: product)
				service.savePrice(price)
			}
			
		when:
			res = service.getPrice(product, new IdealPrice())
		then:
			res == 18.60
			
		when:
			res = service.getPrice(product, new MeanPrice())
		then:
			res == 18.60
			
	}
	
	void "test getAllStrategiesNameList"() {
		when:
			List<String> list = service.getAllStrategiesNameList()
		then:
			list.size() == 2
	}
	
	void "test getGeneralPriceMap"() {
		given:
			Product product = new Product(barcode: "NOKIA6ANDROID", productName: "Nokia 6", 
				description: "Android Phone")
			def price
			service.saveProduct(product)
			for(int i=11;i<=20;i++) {
				price = new Price(price: i, product: product)
				service.savePrice(price)
			}
			def priceMap = [:]
			
		when:
			priceMap = service.getGeneralPriceMap(product)
		
		then:
			priceMap["Max"] == 20
		
	}
	
	void "test findProduct"() {
		given:
			Product product = new Product(barcode: "NOKIA6ANDROID", productName: "Nokia 6", 
				description: "Android Phone")
			def price
			service.saveProduct(product)
			for(int i=11;i<=20;i++) {
				price = new Price(price: i, product: product)
				service.savePrice(price)
			}
			Product p 
			
		when:
			p = service.findProduct(product.getBarcode())
			
		then:
			p.getBarcode() == product.getBarcode()
	}
	
	void "test saveListOfPrices"() {
		given:
			Product product = new Product(barcode: "NOKIA6ANDROID", productName: "Nokia 6", 
				description: "Android Phone")
			service.saveProduct(product)
			List<String> priceList = Arrays.asList("11","12","13","14","15","16","17","18","19","20")
			
		when:
			service.saveListOfPrices(priceList, product);
		then:
			List<BigDecimal> prices = service.listPricesByProductId(product.getBarcode())
			prices.size() == priceList.size()
	}
	
	void "test getWordSeparated"() {
		given:
			String name = "IdealPrice"
			String res = ""
		
		when:
			res = service.getWordSeparated(name)
			
		then:
			res.equals("Ideal Price")
	}
	
	void "test getProductCountForSearch"() {
		given:
			Product product
			for(int i=11;i<=20;i++) {
				product = new Product(barcode: "NOKIA6ANDROID"+i, productName: "Nokia 6", 
						description: "Android Phone")
				product.save()
			}
			int productCount
			
		when:
			productCount = service.getProductCountForSearch("NOKIA6ANDROID")
			
		then:
			productCount == 10
			
		
	}
	
	void "test searchByBarcode"() {
		given:
		Product product
		List<Product> products1 = new ArrayList<>()
		List<Product> products2 = new ArrayList<>()
		for(int i=11;i<=30;i++) {
			product = new Product(barcode: "NOKIA6ANDROID"+i, productName: "Nokia 6",
					description: "Android Phone")
			products1.add(product)
			product.save()
		}
		
	when:
		products2 = service.searchByBarcode("NOKIA6ANDROID",10,10)
		
	then:
		products1.containsAll(products2)
		products2.size() == 10
	}
	
	
}
