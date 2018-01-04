package com.sarathi.domain

import grails.transaction.Transactional

@Transactional(readOnly = false)
class PriceService {

    def serviceMethod() {
    }
	
	def synchronized searchByProductBarcode(String searchText, Integer max, Integer offset){
		if(offset==null) {
			offset=0
		}
		def criteria = Price.createCriteria()
		def result
		synchronized (this) {
			result = criteria.list(max: max, offset: offset) {
				and {
					like("product.barcode", "%"+searchText.trim()+"%")
				}
			}
		}
		return result
	}
	
	def getPriceCountForSearch(String searchText) {
		def criteria = Price.createCriteria()
		List<Price> result = new ArrayList<>()
		synchronized (this) {
			result = criteria.list(){
				and {
					like("product.barcode", "%"+searchText.trim()+"%")
				}
				projections {
					count()
				}
			}
		}
		return result[0]
	}
}
