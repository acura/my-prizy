package com.sarathi.domain

import grails.transaction.Transactional

@Transactional(readOnly = false)
class PriceService {

    def productService
	
    def serviceMethod() {
    }
	
	def synchronized searchByProductBarcode(String text, Integer max, Integer offset){
		if(offset==null) {
			offset=0
		}
		def criteria = Price.createCriteria()
		def result = criteria.list(max: max, offset: offset) {
			and {
				like("product.barcode", "%"+text.trim()+"%")
			}
		}
		return result
	}
	
	def synchronized getPriceCountForSearch(String text, Integer max, Integer offset) {
		if(offset==null) {
			offset=0
		}
		def criteria = Price.createCriteria()
		List<Price> result = criteria.list(){
			and {
				like("product.barcode", "%"+text.trim()+"%")
			}
			projections {
				count()
			}
		}
		return result[0]
	}
	
	def synchronized listPrices(params) {
		return Price.list(params) 
	}
	
	def synchronized savePrice(Price price) {
		return price.save()
	}
	
	def synchronized updatePrice(Price price) {
		return price.save()
	}
	
	def synchronized deletePrice(Price price) {
		return price.delete()
	}
	
	def synchronized deletePriceByBarcode(String barcode) {
		Price.executeUpdate("delete Price where product_id = '"+barcode+"'")
	}
	
	def synchronized listPricesByProduct(Product p) {
		
		def criteria = Price.createCriteria()
		List<BigDecimal> result = criteria.list(){
			eq("product.barcode", p.getBarcode())
			projections {
				property("price")
			}
		}
		return result;
	}
	
	def synchronized listPricesByProductId(String barcode) {
		def criteria = Price.createCriteria()
		List<BigDecimal> result = criteria.list(){
			eq("product.barcode", barcode)
			projections {
				property("price")
			}
		}
		return result;
	}
	
	def synchronized getGeneralPrices(Product p) {
		if(p==null)return null;
		def priceMap = [:]
		def criteria = Price.createCriteria()
		List<Product> result = criteria.list(){
			eq("product.barcode", p.getBarcode())
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
