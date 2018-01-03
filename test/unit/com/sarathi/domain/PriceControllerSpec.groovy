package com.sarathi.domain



import grails.test.mixin.*
import spock.lang.*


@TestFor(PriceController)
@Mock([Price, Product])
class PriceControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params["price"] = new BigDecimal(555)
		
    }
	
    void "Test the index action returns the correct model"() {
		given:
			PriceService priceService = Mock()
			controller.priceService = priceService
			Product product = new Product(barcode: "NOKIA6ANDROID", productName: "Nokia 6", 
				description: "Android Phone")
			product.save()
			Price price
			for(int i=11;i<=20;i++) {
				price = new Price(price: i, product: product)
				price.save()
			}

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            model.priceInstanceCount == 10
    }

    void "Test the create action returns the correct model"() {
		given:
			PriceService priceService = Mock()
			controller.priceService = priceService

		
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.priceInstance!= null
    }

    void "Test the save action correctly persists an instance"() {
		given:
			PriceService priceService = Mock()
			controller.priceService = priceService
			Product product = new Product(barcode: "NOKIA6ANDROID", productName: "Nokia 6", 
				description: "Android Phone")
			product.save()
			Price price
		
        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
			price = new Price(product: product)
            price.validate()
            controller.save(price)

        then:"The create view is rendered again with the correct model"
            model.priceInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            price = new Price(price: new BigDecimal("10"), product: product)
			
			request.method = 'POST'
			request.format = 'form'
			
            controller.save(price)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/price/show/1'
            controller.flash.message != null
            Price.count() == 1
    }

    void "Test that the show action returns the correct model"() {
		given:
			PriceService priceService = Mock()
			controller.priceService = priceService

		
		when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def price = new Price(params)
            controller.show(price)

        then:"A model is populated containing the domain instance"
            model.priceInstance == price
    }

    void "Test that the edit action returns the correct model"() {
		given:
			PriceService priceService = Mock()
			controller.priceService = priceService

		
		when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def price = new Price(params)
            controller.edit(price)

        then:"A model is populated containing the domain instance"
            model.priceInstance == price
    }

    void "Test the update action performs an update on a valid domain instance"() {
		given:
			PriceService priceService = Mock()
			controller.priceService = priceService
			Product product = new Product(barcode: "NOKIA6ANDROID", productName: "Nokia 6", 
				description: "Android Phone")
			product.save()
			Price price

		
		when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/price/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            price = new Price()
            price.validate()
            controller.update(price)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.priceInstance == price

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            price = new Price(price:new BigDecimal("123"), product: product)
			price.save(flush: true)
			price.setPrice(new BigDecimal(555))
            controller.update(price)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/price/show/1"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
		given:
			PriceService priceService = Mock()
			controller.priceService = priceService
			Product product = new Product(barcode: "NOKIA6ANDROID", productName: "Nokia 6", 
				description: "Android Phone")
			product.save()
			Price price

		when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/price/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            price = new Price(price:123,product: product)
			price.save(flush: true)

        then:"It exists"
            Price.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(price)

        then:"The instance is deleted"
            Price.count() == 0
            response.redirectedUrl == '/price/index'
            flash.message != null
    }
}
