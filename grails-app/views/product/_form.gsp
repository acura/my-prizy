<%@ page import="com.prizy.domain.Product" %>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'productName', 'error')} required">
	<label for="productName">
		<g:message code="product.productName.label" default="Product Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="productName" maxlength="50" required="" value="${productInstance?.productName}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="product.description.label" default="Description" />
		
	</label>
	<g:textField name="description" maxlength="50" value="${productInstance?.description}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'barcode', 'error')} required">
	<label for="barcode">
		<g:message code="product.barcode.label" default="Barcode" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="barcode" maxlength="50" required="" value="${productInstance?.barcode}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'prices', 'error')} ">
	<label for="prices">
		<g:message code="product.prices.label" default="Prices" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${productInstance?.prices?}" var="p">
    <li><g:link controller="price" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="price" action="create" params="['product.id': productInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'price.label', default: 'Price')])}</g:link>
</li>
</ul>

</div>