<%@ page import="com.sarathi.domain.Price" %>



<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'price', 'error')} required">
	<label for="price">
		<g:message code="price.price.label" default="Price" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="" pattern="[0-9.,]+" name="price" value="${fieldValue(bean: priceInstance, field: 'price')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'product', 'error')} required">
	<label for="product">
		<g:message code="price.product.label" default="Product" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="product" name="product.barcode" from="${com.sarathi.domain.Product.list()}" optionKey="barcode" required="" value="${priceInstance?.product?.barcode}" class="many-to-one"/>

</div>

