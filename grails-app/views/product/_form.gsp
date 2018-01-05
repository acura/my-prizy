<%@ page import="com.sarathi.domain.Product" %>

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