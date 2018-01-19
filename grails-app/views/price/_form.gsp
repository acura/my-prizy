<%@ page import="com.prizy.domain.Price" %>



<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'price', 'error')} required">
	<label for="price">
		<g:message code="price.price.label" default="Price" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="" pattern="[0-9.,]+" name="price" value="${fieldValue(bean: priceInstance, field: 'price')}" required=""/>

</div>
<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'notes', 'error')} required">
	<label for="notes">
		<g:message code="price.notes.label" default="Notes" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="notes" required="" value="${priceInstance?.notes}"/>

</div>
<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'storeName', 'error')} required">
	<label for="storeName">
		<g:message code="price.storeName.label" default="Store Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="storeName" required="" value="${priceInstance?.storeName}"/>

</div>
<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'product', 'error')} required">
	<label for="product">
		<g:message code="price.product.label" default="Product" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="product" name="product.barcode" from="${com.prizy.domain.Product.list()}" optionKey="barcode" required="" value="${priceInstance?.product?.barcode}" class="many-to-one"/>

</div>

