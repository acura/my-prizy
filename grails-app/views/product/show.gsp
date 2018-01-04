
<%@ page import="com.sarathi.domain.Product" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<g:javascript library='jquery' />
		<style type="text/css">
			.prices {
			    height: auto;
			    max-height: 200px;
			    overflow-x: hidden;
			}
			#divLeft {
			    width:50%;
			    float: left;
			}
			#divRight {
			    width:50%;
			    float: right;
			}
		</style>
	</head>
	<body>
		<a href="#show-product" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div align="center" class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-product" class="content scaffold-show middleDiv" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
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
				
				<g:if test="${productInstance?.prices}">
				<li class="fieldcontain">
					<span id="prices-label" class="property-label"><g:message code="product.aaa.label" default="Number of Prices" /></span>
					<span class="property-value" aria-labelledby="prices-label"><g:link controller="price" action="search" id="numberOfPrices" params="[searchText: "${productInstance.barcode}", offset: '0', max: '10']" >${productInstance.prices.size()}</g:link></span>
				</li>
				</g:if>
				
				<g:if test="${productInstance?.prices}">
				<li style="width: 100%" class="fieldcontain">
					<span class="property-label">
						<g:select id="strategySelect" name="strategySelect" from="${strategyNameList}" optionValue="" noSelection="['null':'Select Strategy']"
										onchange="calculatePrice(this.value);" /></span>
					<span id="calculatedPrice" class="property-value" aria-labelledby="prices-label">$ 00.00</span>
				</li>
				</g:if>
				
				<g:if test="${productInstance?.prices}">
					<li class="fieldcontain">
						<div align="center" class="prices">
							<g:each in="${priceMap}" var="s">
								<li class="fieldcontain">
									<span id="productName-label" class="property-label"><g:message code="${s.key}" default="${s.key}" /></span>
						
									<span class="property-value" aria-labelledby="productName-label" style="font: bold;font-style: italic;quotes: inherit;font-family: cursive;" >
										<g:formatNumber number="${s.value}" type="currency" currencySymbol="\$" currencyCode="USD" currencySymbol="\$" locale="CANADA" maxFractionDigits="2" />
									</span>
						
								</li>
							</g:each>
						</div>
					</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:productInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" params="['barcode': "${productInstance?.barcode}"]" action="edit" resource="${productInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:link class="edit" params="['barcode': "${productInstance?.barcode}"]" action="addPrices" resource="${productInstance}"><g:message code="default.button.edit." default="Add Prices" /></g:link>
					<g:actionSubmit class="delete" params="[barcode: '${productInstance?.barcode}']" action="deleteAllPrices" value="${message(code: 'default.button.delete.', default: 'Delete All Prices')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					<g:actionSubmit class="delete" params="[barcode: '${productInstance?.barcode}']" action="delete" value="${message(code: 'default.button.delete.', default: 'Delete Product')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					<g:hiddenField name="barcode" value="${productInstance?.barcode}" />
				</fieldset>
			</g:form>
		</div>
		<g:javascript>
			function calculatePrice() {
	        $.ajax({
	            type: 'POST',
	            url: '${createLink(action: 'calculatePrice')}',
	            data: {strategy: $("#strategySelect :selected").text(),
	            		barcode: "${productInstance?.barcode}"},
	            success: function(data) {
	            	$('#calculatedPrice').replaceWith(data);
	            }
	        });
	    }
		</g:javascript>
	</body>
</html>
