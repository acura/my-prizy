package com.prizy.test

import spock.lang.Specification
import grails.test.mixin.*
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification

import com.prizy.domain.Price;
import com.prizy.domain.Product;
import com.prizy.service.PriceService;
import com.prizy.service.ProductService;
import com.prizy.strategy.impl.IdealPrice

@TestFor(ProductService)
@Mock([PriceService,Price,Product])
class ThreadingTestSpec extends Specification {

	void "test save product method"() {
		given: "create product"
			Product testProduct = new Product(barcode: "NOKIA6ANDROID", productName: "Nokia 6", 
				description: "Android Phone")
			def price
			List<BigDecimal> prices = new ArrayList<>()
			final def idealPrice1,idealPrice2,meanPrice1,meanPrice2
  
		when: "service save() is called"
			prices.add(new BigDecimal("12")); prices.add(new BigDecimal("25"));
			prices.add(new BigDecimal("37")); prices.add(new BigDecimal("59"));
			prices.add(new BigDecimal("69")); prices.add(new BigDecimal("79"));
			prices.add(new BigDecimal("85"));
			prices.forEach {p ->
				price = new Price(price: p, product: testProduct)
				service.savePrice(price)
			}
			
			new Thread(){
				@Override
				public void run() {
					idealPrice1 = service.getPriceByStrategyReference(testProduct, new IdealPrice())
				};
			}.start().sleep(1000);
		
			new Thread(){
				@Override
				public void run() {
					idealPrice2 = service.getPriceByStrategyReference(testProduct, new IdealPrice())
				};
			}.start().sleep(1000);
		
			new Thread(){
				@Override
				public void run() {
					meanPrice1 = service.getPriceByStrategyReference(testProduct, new IdealPrice())
				};
			}.start().sleep(1000);
		
			new Thread(){
				@Override
				public void run() {
					meanPrice2 = service.getPriceByStrategyReference(testProduct, new IdealPrice())
				};
			}.start().sleep(1000);
		
			for(int i=1;i<=10;i++) {
				new Thread(){
					@Override
					public void run() {
						meanPrice2 = service.getPriceByStrategyReference(testProduct, new IdealPrice())
					};
				}.start().sleep(1000);
			}
		
		then: 
			idealPrice1 == idealPrice2
			meanPrice1 == meanPrice2
	}
}
