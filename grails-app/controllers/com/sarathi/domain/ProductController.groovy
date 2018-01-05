package com.sarathi.domain

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import com.sarathi.strategy.PricingStategy

@Transactional(readOnly = true)
class ProductController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	def productService
	def mapTemp = [:]
	def preSearchText

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
		respond productService.listProducts(params), model:[productInstanceCount: Product.count()]
    }
	
	def search(Integer max) {
		String searchText = params.searchText
		if(searchText != null) {
			preSearchText = searchText
		}
		searchText = searchText==null?"":searchText
		Integer offset = params.offset as Integer
		offset==null?0:offset
		ArrayList<Product> productInstanceList = productService.searchByBarcode(preSearchText, max, offset);
		def total = productService.getProductCountForSearch(preSearchText)
		if(offset.equals(0) && searchText != null) {
			render(view: "index",  model: [productInstanceList:productInstanceList, searchText: preSearchText, 
					productInstanceCount:total])
		} else if(offset>0) {
			render(view: "index",  model: [productInstanceList:productInstanceList, searchText: preSearchText, 
					productInstanceCount:total])
		} else {
			render(template:'productSearch', model:[productInstanceList:productInstanceList, 
					searchText: preSearchText, productInstanceCount:total])
		}
	}

    def show(Product productInstance) {
		def priceMap = productService.getGeneralPriceMap(productInstance)
		def strategyNameList = productService.getStrategyNameList()
		
		render(view:'show',model:[productInstance:productInstance, priceMap:priceMap, 
				strategyNameList:strategyNameList])
    }
	
	def calculatePrice() {
		String strategy = params.strategy
		strategy = strategy.replaceAll(" ", "")
		if(strategy.equalsIgnoreCase("SelectStrategy")) {
			render(template:'strategy', model:[price:new BigDecimal("0"), strategyHint: "Select Price Strategy!"])
		} else {
			String packageName = PricingStategy.class.getPackage()
			packageName = packageName.split(" ")[1]
			def barcode = params.barcode
			String strategyHint = productService.getStrategyHint(packageName, strategy);
			def price = productService.getPrice(Product.get(barcode), 
					productService.getReference(packageName, strategy))
			
			render(template:'strategy', model:[price:price==new BigDecimal("-1")?"Less than 4 prices...":price, 
					strategyHint: strategyHint])
		}
	}
	
    def create() {
        respond new Product(params)
    }

    @Transactional
    def save(Product productInstance) {
		
        if (productInstance == null) {
            notFound()
            return
        }

        if (productInstance.hasErrors()) {
            respond productInstance.errors, view:'create'
            return
        }

		try {
			productInstance.save(flush: true, failOnError: true)
			
			String priceList = params.priceList
			List<String> tempPriceList = new ArrayList<>()
			if(null != priceList) {
				priceList.trim()
				priceList.replace(" ", "")
				tempPriceList = Arrays.asList(priceList.split(","));
			}
			
			productService.saveListOfPrices(tempPriceList, productInstance)
		} catch(org.springframework.dao.DuplicateKeyException e) {
			flash.message = message(code: 'default.unique.product.message', args: [message(code: 'product.label', 
					default: 'Product'), productInstance])
			respond flash.message, view:'create', model:[productInstance:productInstance]
			return
		}
		
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'product.label', 
						default: 'Product'), productInstance])
                //redirect productInstance
				//redirect view: "show", productInstance: productInstance
				def priceMap = productService.getGeneralPriceMap(productInstance)
				def strategyNameList = productService.getStrategyNameList()
				render(view:'show',model:[productInstance:productInstance, priceMap:priceMap, 
						strategyNameList:strategyNameList])
            }
            '*' { respond productInstance, [status: CREATED] }
        }
    }

    def edit(Product productInstance) {
		productInstance = Product.get(params.barcode)
        respond productInstance
    }
	
	def addPrices(Product productInstance) {
		productInstance = Product.get(params.barcode)
		//redirect(controller: "price", action: "addPrices", params: [barcode: params.barcode])
		respond productInstance
	}
	@Transactional(readOnly=false)
	def savePrices() {
		Product productInstance = Product.get(params.barcode)
		String priceList = params.priceList
		priceList.replace(" ", "")
		List<String> tempPriceList = Arrays.asList(priceList.split(","));
		if(tempPriceList.isEmpty()) {
			flash.message = message(code: 'default.add.prices.message', args: [message(code: 'product.label', 
					default: 'Product'), productInstance])
			addPrices(productInstance)
		} else {
			flash.message = message(code: 'default.add.prices.success.message', args: [productInstance])
		}
		productService.saveListOfPrices(tempPriceList, productInstance)
		
		def priceMap = productService.getGeneralPriceMap(productInstance)
		def strategyNameList = productService.getStrategyNameList()
		
		render(view:'show',model:[productInstance:productInstance, priceMap:priceMap, 
				strategyNameList:strategyNameList])
		
	}
	
	@Transactional
	def deleteAllPrices() {
		String barcode = params.barcode
		Product productInstance = Product.get(barcode)
		productService.deletePriceByBarcode(barcode)
		
		def priceMap = productService.getGeneralPriceMap(productInstance)
		def strategyNameList = productService.getStrategyNameList()
		
		render(view:'show',model:[productInstance:productInstance, priceMap:priceMap, 
				strategyNameList:strategyNameList])
	}
	
    @Transactional(readOnly=false)
    def update(Product productInstance) {
		productInstance = Product.get(params.barcode)
        if (productInstance == null) {
            notFound()
            return
        } else {
			productInstance.productName =params.productName
			productInstance.description = params.description
        }

        if (productInstance.hasErrors()) {
            respond productInstance.errors, view:'edit'
            return
        }
		def priceMap = productService.getGeneralPriceMap(productInstance)
		def strategyNameList = productService.getStrategyNameList()
     	productInstance.save(flush: true, failOnError: true)
		 
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Product.label', 
						default: 'Product'), productInstance])
				render(view:'show',model:[productInstance:productInstance, priceMap:priceMap, 
						strategyNameList:strategyNameList])
				//redirect view:"show", productInstance: productInstance
            }
			render(view:'show',model:[productInstance:productInstance, priceMap:priceMap, 
					strategyNameList:strategyNameList])
            //'*'{ respond productInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Product productInstance) {
		productInstance = Product.get(params.barcode)
        if (productInstance == null) {
            notFound()
            return
        }

    //    productInstance.delete flush:true
		productService.deleteProduct(productInstance)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Product.label', 
						default: 'Product'), productInstance.barcode])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'product.label', 
						default: 'Product'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	
	
	/*new test controls*/
	def listProducts() {
		ArrayList<Product> productInstanceList = productService.listProducts(params)
		def total = Product.count()
		render(template:'productList', model:[productInstanceList:productInstanceList, productInstanceCount:total])
	}
	
}
