package com.sarathi.domain

import grails.transaction.Transactional

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
import com.sarathi.reflection.ReflectionHelper
import com.sarathi.strategy.PricingStategy

@Transactional
class ProductService {

	def listProducts(params) {
		synchronized (this) {
			return Product.list(params)
		}
	}
	
	def saveProduct(Product product) {
		synchronized (this) {
			try {
				product.save(flush: true, failOnError: true)
				return true;
			} catch(MySQLIntegrityConstraintViolationException e) {
				throw e
			}
		}
	}
	
	def savePrice(Price price) {
		synchronized (this) {
			return price.save()
		}
	}
	
	def updateProduct(Product product) {
		synchronized (this) {
			Boolean res = product.save()
			return res
		}
	}
	
	def deleteProduct(Product product) {
		synchronized (this) {
			return product.delete()
		}
	}
	
	def getPriceByStrategyReference(Product product, PricingStategy ref) {
		synchronized (this) {
			List<BigDecimal> prices = listPricesByProduct(product)
			return ref.calculate(prices)
		}
	}
	
	def listPricesByProduct(Product p) {
		synchronized (this) {
			def criteria = Price.createCriteria()
			List<BigDecimal> result = criteria.list(){
				eq("product", p)
				projections {
					property("price")
				}
			}
			return result;
		}
	}
	
	def listPricesByProductId(String barcode) {
		synchronized (this) {
			def criteria = Price.createCriteria()
			List<BigDecimal> result = criteria.list() {
				eq("product", new Product(barcode: barcode))
				projections {
					property("price")
				}
			}
			return result;
		}
	}
	
	def getAllStrategiesNameList() {
		synchronized (this) {
			List<Class<?>> list = ReflectionHelper.findClassesImpmenenting()
			List<String> strategies = new ArrayList<>();
			list.forEach {c -> 
				String str = c.getName();
				strategies.add(str.substring(str.lastIndexOf(".")+1))
			}
			return strategies
		}
	}
	
	def getGeneralPriceMap(Product productInstance) {
		synchronized (this) {
			if(productInstance==null)return null;
			return getGeneralPrices(productInstance)
		}
	}
	
	def getGeneralPrices(Product p) {
		synchronized (this) {
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
	}
	
	def getStrategyNameList() {
		synchronized (this) {
			def list = getAllStrategiesDefined()
			def strategyNameList = []
			list.forEach{c -> 
				String str = c.getName();
				String name = str.substring(str.lastIndexOf(".")+1)
				strategyNameList.add(getWordSeparated(name))
			}
			return strategyNameList
		}
	}
	
	def getReference(String packageName, String className) {
		synchronized (this) {
			String fullPath = packageName+".impl."+className;
			PricingStategy ref = (PricingStategy) Class.forName(fullPath).newInstance();
			return ref
		}
	}
	
	def getStrategyHint(String packageName, String className) {
		synchronized (this) {
			String fullPath = packageName+".impl."+className;
			PricingStategy ref = (PricingStategy) Class.forName(fullPath).newInstance();
			return ref.HINT;
		}
	}
	
	def getAllStrategiesDefined() {
		synchronized (this) {
			return ReflectionHelper.findClassesImpmenenting()
		}
	}
	
	def searchByBarcode(String text, Integer max, Integer offset){
		synchronized (this) {
			if(offset==null) {
				offset=0
			}
			
			def criteria = Product.createCriteria()
			List<Product> result = criteria.list(max: max, offset: offset){
				like('barcode', "%"+text.trim()+"%")
			}
			return result
		}
	}
	
	def getProductCountForSearch(String text) {
		synchronized (this) {
			def criteria = Product.createCriteria()
			List<Product> result = criteria.list(){
				like('barcode', "%"+text.trim()+"%")
				projections {
					count()
				}
			}
			return result[0]
		}
	}
	
	def getWordSeparated(String str) {
		synchronized (this) {
			String temp = "";
			for (String w : str.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
				temp = temp + " " + w;
			}
			temp = temp.trim();
			
			return temp;
		}
	}
	
	def saveListOfPrices(List<String> priceList, Product productInstance) {
		synchronized (this) {
			priceList.forEach {tempPrice -> 
				Price price = new Price(price: tempPrice, product: productInstance)
				price.save()
			}
		}
	}
	
	@Transactional (readOnly=true)
	Product findProduct(String barCode){
		synchronized (this) {
			Product product
			if(null != barCode && !barCode.isEmpty()){
				product = Product.findByBarcode(barCode)
			}
			return product
		}
	}
	
	def deletePriceByBarcode(String barcode) {
		synchronized (this) {
			Price.where {product== Product.findByBarcode(barcode)}.deleteAll()
		}
	}
	
	
}
