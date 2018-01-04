package com.sarathi.domain

import grails.transaction.Transactional

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
import com.sarathi.reflection.ReflectionHelper
import com.sarathi.strategy.PricingStategy

@Transactional
class ProductService {

	def synchronized listProducts(params) {
		return Product.list(params)
	}
	
	def synchronized saveProduct(Product product) {
		try {
			product.save(flush: true, failOnError: true)
			return true;
		} catch(MySQLIntegrityConstraintViolationException e) {
			throw e
		}
	}
	
	def synchronized savePrice(Price price) {
		return price.save()
	}
	
	def synchronized updateProduct(Product product) {		
		Boolean res = Product.executeUpdate("UPDATE Product p SET p.description="
			+product.getDescription()+", p.productName="+product.getProductName()
			+" WHERE barcode='"+product.getBarcode()+"'")
		return res
	}
	
	def synchronized deleteProduct(Product product) {
		return product.delete()
	}
	
	def synchronized getPrice(Product product, PricingStategy ref) {
		List<BigDecimal> prices = listPricesByProduct(product)
		return ref.calculate(prices)
	}
	
	def synchronized listPricesByProduct(Product p) {
		def criteria = Price.createCriteria()
		List<BigDecimal> result = criteria.list(){
			eq("product", p)
			projections {
				property("price")
			}
		}
		return result;
	}
	
	def synchronized getPriceByProductId(int productId, PricingStategy ref) {
		List<Price> prices = listPricesByProductId(productId)
		return ref.calculate(prices)
	}
	
	def synchronized listPricesByProductId(String barcode) {
		def criteria = Price.createCriteria()
		List<BigDecimal> result = criteria.list() {
			eq("product", new Product(barcode: barcode))
			projections {
				property("price")
			}
		}
		return result;
	}
	
	def synchronized getAllStrategiesNameList() {
		List<Class<?>> list = ReflectionHelper.findClassesImpmenenting()
		List<String> strategies = new ArrayList<>();
		for(Class<?> c:list) {
			String str = c.getName();
			strategies.add(str.substring(str.lastIndexOf(".")+1))
		}
		return strategies
	}
	
	def synchronized getGeneralPriceMap(Product productInstance) {
		if(productInstance==null)return null;
		return getGeneralPrices(productInstance)
	}
	
	def synchronized getGeneralPrices(Product p) {
		if(p==null)return null;
		def priceMap = [:]
		def criteria = Price.createCriteria()
		List<Product> result = criteria.list(){
			eq("product", p)
			projections {
				avg("price")
				max("price")
				min("price")
			}
		}
		priceMap.put("Average", result[0][0])
		priceMap.put("Max", result[0][1])
		priceMap.put("Min", result[0][2])
		return priceMap
	}
	
	def synchronized getStrategyNameList() {
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
		return ref.HINT;
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
			price.save()
		}
	}
	
	@Transactional (readOnly=true)
	Product findProduct(String barCode){
		Product product
		if(null != barCode && !barCode.isEmpty()){
			product = Product.findByBarcode(barCode)
		}
		return product
	}
	
	def synchronized deletePriceByBarcode(String barcode) {
		Price.executeUpdate("delete Price where product_id = '"+barcode+"'")
	}
	
	
}
