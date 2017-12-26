package com.sarathi.domain

import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.sarathi.reflection.ReflectionHelper
import com.sarathi.strategy.PricingStategy

import grails.transaction.Transactional

@Transactional
class ProductService {

    def priceService
	
    def serviceMethod() {
    }
	
	def synchronized listProducts(params) {
		return Product.list(params)
	}
	
	def synchronized saveProduct(Product product) {
		println "saveProduct............................................."
		try {
			product.save(flush: true, failOnError: true)
			return false;
		} catch(MySQLIntegrityConstraintViolationException e) {
			return true;
		}
	}
	
	def synchronized updateProduct(Product product) {
		println "update product"
		println product.getBarcode()
		println product.getProductName()
		println product.getDescription()

		Boolean res = Product.executeQuery("UPDATE Product p SET p.description="
			+product.getDescription()+", p.productName="+product.getProductName()
			+" WHERE barcode='"+product.getBarcode()+"'")
		return res
	}
	
	def synchronized deleteProduct(Product product) {
		return product.delete()
	}
	
	def synchronized getPrice(Product product, PricingStategy ref) {
		List<BigDecimal> prices = priceService.listPricesByProduct(product)
		return ref.calculate(prices)
	}
	
	def synchronized getPriceByProductId(int productId, PricingStategy ref) {
		List<Price> prices = priceService.listPricesByProductId(productId)
		return ref.calculate(prices)
	}
	
	def synchronized getAllStrategiesNameList() {
		List<Class<?>> list = ReflectionHelper.findClassesImpmenenting()
		List<String> strategies = new ArrayList<>();
		for(Class<?> c:list) {
			String str = c.getName();
			PricingStategy ref = (PricingStategy) Class.forName(str).newInstance();
			strategies.add(str.substring(str.lastIndexOf(".")+1))
		}
		return strategies
	}
	
	def synchronized getGeneralPriceMap(Product productInstance) {
		if(productInstance==null)return null;
		return priceService.getGeneralPrices(productInstance)
	}
	
	def synchronized getStrategyNameList(Product productInstance) {
		def list = getAllStrategiesDefined()
		def strategyNameList = []
		
		for(c in list)  {
			String str = c.getName();
			String name = str.substring(str.lastIndexOf(".")+1)
			strategyNameList.add(getWordSeparated(name))
		}
		return strategyNameList
	}
	
	def synchronized getReference(String packageName, String className) {
		String fullPath = packageName+".impl."+className;
		PricingStategy ref = (PricingStategy) Class.forName(fullPath).newInstance();
		return ref
	}
	
	def getStrategyHint(String packageName, String className) {
		String fullPath = packageName+".impl."+className;
		PricingStategy ref = (PricingStategy) Class.forName(fullPath).newInstance();
		return ref.hint;
	}
	
	def synchronized getAllStrategiesDefined() {
		return ReflectionHelper.findClassesImpmenenting()
	}
	
	def synchronized searchByBarcode(String text, Integer max, Integer offset){
		if(offset==null) {
			offset=0
		}
		
		def criteria = Product.createCriteria()
		List<Product> result = criteria.list(max: max, offset: offset){
			like('barcode', "%"+text.trim()+"%")
		}
		return result
	}
	
	def synchronized getProductCountForSearch(String text) {
		def criteria = Product.createCriteria()
		List<Product> result = criteria.list(){
			like('barcode', "%"+text.trim()+"%")
			projections {
				count()
			}
		}
		return result[0]
	}
	
	def synchronized getWordSeparated(String str) {
		String temp = "";
		for (String w : str.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
			temp = temp + " " + w;
		}
		temp = temp.trim();
		
		return temp;
	}
	
	def saveListOfPrices(List<String> priceList, Product productInstance) {
		for(String tempPrice:priceList) {
			Price price = new Price(price: tempPrice, product: productInstance)
			priceService.savePrice(price)
		}
	}
	
	
}
