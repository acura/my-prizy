package com.prizy.test



import com.prizy.controller.ProductController;
import com.prizy.domain.Price;
import com.prizy.domain.Product;
import com.prizy.service.PriceService;
import com.prizy.service.ProductService;

import grails.test.mixin.*
import spock.lang.*
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED


@TestFor(ProductController)
@grails.test.mixin.Mock([Product,ProductService,Price,PriceService])
class ProductControllerSpec extends Specification {

	Product testProduct
	
	static doWithSpring = {
		productService ProductService
		priceService PriceService
	}
	
    def populateValidParams(params) {
        assert params != null
    }
	
	def setup() {
		testProduct = new Product("barcode": "NOKIA6ANDROID", "productName": "Nokia 6",
			"description": "Android Phone")
		testProduct.save()
		for(int i=11;i<=20;i++) {
			new Price(price: i, product:testProduct, storeName: "Store 1", notes: "this is for testing purpose").save()
		}
	}
	
	@Unroll
    void "Test the index action returns the correct model"() {
		given:
			ProductService productService = Mock()
			params["max"] = "10"
			
        when:"The index action is executed for 0 products in db"
            controller.index()

        then:"The model is correct"
            model.productInstanceCount == 1
			
		when:"The index action is executed when added 5 more products in db"
			for(int i=1;i<=5;i++) {
				testProduct.setBarcode("NOKIA6ANDROID"+i)
				testProduct.save()
			}
			controller.index()

		then:"The model is correct"
			model.productInstanceCount == 6
    }

	@Unroll
    void "Test the create action returns the correct model"() {
		given:
			ProductService productService = Mock()

        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.productInstance!= null
			view == '/product/create'
    }

	@Unroll
    void "Test the save action correctly persists an instance"() {
		given:
			ProductService productService = Mock()
			controller.productService = productService

        when:"The save action is executed with an valid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
			controller.request.method = "POST"
			request.format = 'form'
			
            controller.save(testProduct)

        then:"The show view is rendered with the correct model"
            model.productInstance == testProduct
			response.status == 200
            view == '/product/show'
			
		when:"The save action is executed with an null instance"
			response.reset()
			request.contentType = FORM_CONTENT_TYPE
			request.method = 'POST'
			controller.request.method = "POST"
			request.format = 'form'
			model.clear()
			
			controller.save(null)

		then:"The index view is rendered"
			model.productInstance == null
			response.status.value == 302
			response.redirectedUrl == '/product/index'
			
		when:"The save action is executed with an invalid instance"
			response.reset()
			request.contentType = FORM_CONTENT_TYPE
			request.method = 'POST'
			controller.request.method = "POST"
			request.format = 'form'
			model.clear()
			Product invalidProduct = new Product()
			invalidProduct.validate()
			
			controller.save(invalidProduct)

		then:"The create view is rendered with the same model"
			model.productInstance == invalidProduct
			view == 'create'
			
		when:"The save action is executed with an invalid method"
			response.reset()
			request.contentType = FORM_CONTENT_TYPE
			request.method = 'method'
			controller.request.method = "method"
			request.format = 'form'
			model.clear()
			
			controller.save(testProduct)

		then:"The create view is rendered with the same model"
			response.status == SC_METHOD_NOT_ALLOWED
			
		where:
			method << ['PATCH', 'DELETE', 'GET', 'PUT']
			
			

    }

	@Unroll
    void "Test that the show action returns the correct model"() {
		given:
			ProductService productService = Mock()
		
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 200 error is returned"
            response.status == 404
			model.priceMap == null
			model.strategyNameList == null
			model.productInstanvce == null

        when:"The show action is executed with a valid instance"
            controller.show(testProduct)

        then:"A model is populated containing the domain instance"
            model.productInstance == testProduct
			view == '/product/show'
			model.priceMap == [Average:15.50, Max:20.00, Min:11.00]
			model.strategyNameList == ['Ideal Price', 'Mean Price']
    }

	@Unroll
    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404
			view == null

        when:"A domain instance is passed to the edit action"
			response.reset()
			params["barcode"] = testProduct.getBarcode()
            controller.edit(testProduct)

        then:"A model is populated containing the domain instance"
			response.status == 200
            model.productInstance == testProduct
			view == '/product/edit'
    }

	@Unroll
    void "Test the update action performs an update on a valid domain instance"() {
		given:
			ProductService productService = Mock()
		
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(new Product(barcode: '123qwe', productName: 'product', 
				description: 'product does not exists'))

        then:"A 404 error is returned"
            response.redirectedUrl == '/product/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def invalidProduct = new Product()
            invalidProduct.validate()
            controller.update(invalidProduct)

        then:"The edit view is rendered again with the invalid instance"
            response.redirectedUrl == '/product/index'
            model.productInstance == null

        when:"A valid domain instance is passed to the update action"
            response.reset()
            controller.update(testProduct)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/product/index"
            flash.message != null
    }

	@Unroll
    void "Test that the delete action deletes an instance if it exists"() {
		given:
			ProductService productService = Mock()
			def product = new Product(barcode: '123qwe', productName: 'product',
				description: 'product does not exists')
		
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
			params['barcode'] = null
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/product/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
			product.save(flush: true)

        then:"It exists"
            Product.count() == 2

        when:"The domain instance is passed to the delete action"
			params['barcode'] = product.getBarcode()
            controller.delete(product)

        then:"The instance is deleted"
            Product.count() == 1
            response.redirectedUrl == '/product/index'
            flash.message != null
    }
	
	@Unroll
	void "test search function"() {
		given:
			ProductService productService = Mock()
			for(int i=1;i<=5;i++) {
				testProduct.setBarcode("NOKIA6ANDROID"+i)
				testProduct.save()
			}
			for(int i=1;i<=5;i++) {
				testProduct.setBarcode("ONEPLUS5ANDROID"+i)
				testProduct.save()
			}
			
		when:"search function is called by searchText as null it should give result of 10"
			response.reset()
			model.clear()
			params.clear()
			params["searchText"] = null
			params["offset"] = "0"
			def res = controller.search(10)
			
		then:
			model.productInstanceList.size() == 10
			view == '/product/index'
			
		when:"search function is called by searchText as ' ' it should give result of 10"
			response.reset()
			model.clear()
			params.clear()
			params["searchText"] = " "
			params["offset"] = "0"
			res = controller.search(10)
			
		then:
			model.productInstanceList.size() == 10
			view == '/product/index'
			
		when:"search function is called by searchText as ' ' and max is sent as : 20"
			response.reset()
			model.clear()
			params.clear()
			params["searchText"] = " "
			params["offset"] = "0"
			res = controller.search(20)
			
		then:
			model.productInstanceList.size() == 11
			view == '/product/index'
			
		when:"search function is called by searchText as 'NOKIA' it should give result of 10"
			params["searchText"] = "NOKIA"
			params["offset"] = "0"
			res = controller.search(10)
			
		then:
			model.productInstanceList.size() == 6
            view == '/product/index'
			
	}
	
	@Unroll
	void "test calculatePrice"() {
		given:
			ProductService productService = Mock()
			def res
			Price.count()
			
		when:"calculate Idfeal Price for testProduct"
			params.clear()
			params["barcode"] = testProduct.getBarcode()
			params["strategy"] = "Ideal Price"
			controller.calculatePrice()
			
		then:
			response.text.trim().contains("18.60")
			
		when:"calculate Mean Price for testProduct"
			params.clear()
			params["barcode"] = testProduct.getBarcode()
			params["strategy"] = "Mean Price"
			controller.calculatePrice()
			
		then:
			response.text.trim().contains("18.60")
		
	}
	
	@Unroll
	void "test calculatePrice for Product having less prices than 4"() {
		given:
			ProductService productService = Mock()
			def res
			Product productHavingLessPrices = new Product(barcode: "123qwe",productName: "test pro", 
				description: "desc")
			for(int i =11;i<=13;i++) {
				new Price(price: i, product: productHavingLessPrices)
			}
			int n = Price.count()
			
		when:"calculate Idfeal Price for testProduct"
			params.clear()
			params["barcode"] = productHavingLessPrices.getBarcode()
			params["strategy"] = "Ideal Price"
			controller.calculatePrice()
			
		then:
			response.text.trim().contains("add.more.than.four.prices")
			
		when:"calculate Mean Price for testProduct"
			params.clear()
			params["barcode"] = productHavingLessPrices.getBarcode()
			params["strategy"] = "Mean Price"
			controller.calculatePrice()
			
		then:
			response.text.trim().contains("add.more.than.four.prices")
		
	}
	
}
