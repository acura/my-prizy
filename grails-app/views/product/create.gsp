<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-product" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<g:render template="menu_global"/>
			</ul>
		</div>
		<div id="create-product" class="content scaffold-create middleDiv" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${productInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${productInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form url="[resource:productInstance, action:'save']" >
				<fieldset class="form">
					<g:render template="form"/>
					<%--<div class="fieldcontain ${hasErrors(bean: priceInstance, field: 'price', 'error')} required">
						<label for="price">
							<g:message code="price.price.label" default="Prices List" />
							<span class="required-indicator">*</span>
						</label>
						<g:textArea name="price" value=""></g:textArea>
						<g:textField size="50px" type="number" pattern="[0-9.,]+" required="true" class="form-control" name="priceList" value=""/>
						<label><g:message code="price.price.label" default="Enter comma separated list of prices." /></label>
					</div>
				--%></fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
