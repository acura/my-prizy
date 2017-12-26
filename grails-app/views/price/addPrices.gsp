<%@page import="net.sf.ehcache.util.ProductInfo"%>
<%@ page import="com.sarathi.domain.Price" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'price.label', default: 'Price')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-price" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
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
					<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'product', 'error')} required">
						<label for="product">
							<g:message code="product." default="Product Name" />
							<span class="required-indicator">*</span>
						</label>
						<g:link>${productInstance?.productName} </g:link>
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'product', 'error')} required">
						<label for="product">
							<g:message code="product." default="Product Description" />
							<span class="required-indicator">*</span>
						</label>
						<g:link>${productInstance?.description} </g:link>
					</div>
					
					<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'product', 'error')} required">
						<label for="product">
							<g:message code="price." default="Barcode" />
							<span class="required-indicator">*</span>
						</label>
						<g:link>${productInstance?.barcode} </g:link>
					</div>
					<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'price', 'error')} required">
						<label for="price">
							<g:message code="price.price.label" default="Prices List" />
							<span class="required-indicator">*</span>
						</label>
						<%--<g:textArea name="price" value=""></g:textArea>--%>
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
