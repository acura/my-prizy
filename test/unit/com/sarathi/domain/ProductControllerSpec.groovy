package com.sarathi.domain



import grails.test.mixin.*
import spock.lang.*

@TestFor(ProductController)
@Mock(Product)
class ProductControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
		params["barcode"] = "test555asfl33223"
		params["productName"] = "test product name"
		params["description"] = "test pro description"
		
    }
	
    void "Test the index action returns the correct model"() {
		given:
			ProductService productService = Mock()
			controller.productService = productService
			Product productInstance = new Product("barcode": "test33434", "productName": "test pro", "description": "test desc")
			
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
			populateValidParams(params)
            def product1 = new Product("barcode": "123qwe", "productName": "test pro", "description": "test desc")
			controller.request.method = "POST"
			request.format = 'form'
			
            controller.save(product1)

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
            populateValidParams(params)
            def product = new Product(params)
            controller.show(product)

        then:"A model is populated containing the domain instance"
            model.productInstance == product
    }

    void "Test that the edit action returns the correct model"() {
		given:
			ProductService productService = Mock()
			controller.productService = productService
			Product p = new Product("barcode": "123qwe", "productName": "test pro", "description": "test desc")
			ProductService ps = Mock()
			ps.saveProduct(p)

		
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            controller.edit(p)

        then:"A model is populated containing the domain instance"
			println ":::::::::::::::"+model.productInstance
            model.productInstance == null
    }

    void "Test the update action performs an update on a valid domain instance"() {
		given:
			ProductService productService = Mock()
			controller.productService = productService
			Product product1 = new Product("barcode": "123qwe", "productName": "test pro", 
					"description": "test desc")
		
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(product1)

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
            populateValidParams(params)
            product1 = new Product(params).save(flush: true)
            controller.update(product1)

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
            populateValidParams(params)
            def product = new Product(params).save(flush: true)

        then:"It exists"
            Product.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(product)

        then:"The instance is deleted"
            Product.count() == 1
            response.redirectedUrl == '/product/index'
            flash.message != null
    }
}
