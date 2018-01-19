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
					<g:render template="menu_global"/>
				</ul>
			</div>
			<div id="create-price" class="content scaffold-create middleDiv" role="main">
				<h1><g:message code="default.create.label" args="[entityName]" /></h1>
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
				<g:form url="[resource:priceInstance, action:'save']" >
					<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'barcode', 'error')} required">
						<label for="barcode">
							<g:message code="product.barcode.label" default="Barcode" />
							<span class="required-indicator">*</span>
						</label>
						<g:textField name="barcode" maxlength="50" required="" value="${productInstance?.barcode}"/>
					</div>
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
					<fieldset class="buttons">
						<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
