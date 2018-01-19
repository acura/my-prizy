<%@page import="net.sf.ehcache.util.ProductInfo"%>
<%@ page import="com.prizy.domain.Price" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-price" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div align="center" class="nav" role="navigation">
			<ul>
				<g:render template="menu_global"/>
			</ul>
		</div>
		<div id="create-price" class="content scaffold-create middleDiv" role="main">
			<h1><g:message code="" default="Add prices" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${priceInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${priceInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form url="[resource:productInstance, controller: 'price', action:'savePrices']">
				<fieldset class="form">
					<ol class="property-list product">
			
						<g:if test="${productInstance?.productName}">
							<li class="fieldcontain">
								<span id="productName-label" class="property-label"><g:message code="product.productName.label" default="Product Name" /></span>
								
								<span class="property-value" aria-labelledby="productName-label"><g:fieldValue bean="${productInstance}" field="productName"/></span>
								
							</li>
						</g:if>
					
						<g:if test="${productInstance?.description}">
						<li class="fieldcontain">
							<span id="description-label" class="property-label"><g:message code="product.description.label" default="Description" /></span>
							
							<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${productInstance}" field="description"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${productInstance?.barcode}">
						<li class="fieldcontain">
							<span id="barcode-label" class="property-label"><g:message code="product.barcode.label" default="Barcode" /></span>
							
							<span class="property-value" aria-labelledby="barcode-label"><g:fieldValue bean="${productInstance}" field="barcode"/></span>
							
						</li>
						</g:if>
						
						
					</ol>
					<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'price', 'error')} required">
						<label for="price">
							<g:message code="price.price.label" default="Prices List" />
							<span class="required-indicator">*</span>
						</label>
						<g:textField size="50px" type="number" pattern="[0-9.,]+" required="true" class="form-control" name="priceList" value=""/>
						<label><g:message code="price.price.label" default="Enter comma separated list of prices." /></label>
					</div>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.', default: 'Add Prices List')}" />
					<g:hiddenField name="barcode" value="${productInstance?.barcode}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
