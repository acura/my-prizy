package com.sarathi.domain



import grails.test.mixin.*
import spock.lang.*

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
	}
	
    void "Test the index action returns the correct model"() {
		given:
			ProductService productService = Mock()
			controller.productService = productService
			
        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.productInstanceList
            model.productInstanceCount == null
    }

    void "Test the create action returns the correct model"() {
		given:
			ProductService productService = Mock()
			controller.productService = productService

        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.productInstance!= null
    }

    void "Test the save action correctly persists an instance"() {
		given:
			ProductService productService = Mock()
			controller.productService = productService


        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
			controller.request.method = "POST"
			request.format = 'form'
			
            controller.save(testProduct)

        then:"The create view is rendered again with the correct model"
            model.productInstance!= null
            view == '/product/show'

    }

    void "Test that the show action returns the correct model"() {
		given:
			ProductService productService = Mock()
			controller.productService = productService

		
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 200 error is returned"
            response.status == 200

        when:"A domain instance is passed to the show action"
            controller.show(testProduct)

        then:"A model is populated containing the domain instance"
            model.productInstance == testProduct
    }

    void "Test that the edit action returns the correct model"() {
		given:
			ProductService productService = Mock()
			controller.productService = productService
			ProductService ps = Mock()
			ps.saveProduct(testProduct)

		
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            controller.edit(testProduct)

        then:"A model is populated containing the domain instance"
            model.productInstance == null
    }

    void "Test the update action performs an update on a valid domain instance"() {
		given:
			ProductService productService = Mock()
			controller.productService = productService
		
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(testProduct)

        then:"A 404 error is returned"
            response.redirectedUrl == '/product/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def product2 = new Product()
            product2.validate()
            controller.update(product2)

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

    void "Test that the delete action deletes an instance if it exists"() {
		given:
			ProductService productService = Mock()
			controller.productService = productService

		
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/product/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            testProduct.save(flush: true)

        then:"It exists"
            Product.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(testProduct)

        then:"The instance is deleted"
            Product.count() == 1
            response.redirectedUrl == '/product/index'
            flash.message != null
    }
	
	void "test search function"() {
		given:
			/*ProductService productService = Mock()
			controller.productService = new ProductService()*/
			params["searchText"] = "ANDROID"
			params["offset"] = "0"
			for(int i=1;i<=5;i++) {
				testProduct.setBarcode("NOKIA6ANDROID"+i)
				testProduct.save()
			}
			for(int i=1;i<=5;i++) {
				testProduct.setBarcode("ONEPLUS5ANDROID"+i)
				testProduct.save()
			}
			
		when:"search function is called by searchText as 'ANDROID' it should give result of 10"
			def res = controller.search(10)
			
		then:
			Product.count() == 10
            view == '/product/index'
	}
}
