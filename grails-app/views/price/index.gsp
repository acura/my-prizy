
<%@ page import="com.sarathi.domain.Price" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'price.label', default: 'Price')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-price" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li style="float: right;" >
					<g:message code="sfsdf" default="Search By Barcode : " />
					<input type="text" id="searchText" name="searchText" value="${searchText}" placeholder="barcode" />
				</li>
			</ul>
		</div>
		<div id="list-price" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div id="result-div" >
			<table id="result-table">
			<thead>
					<tr>
						<th><g:message code="price.product.label" default="Barcode" /></th>
						<g:sortableColumn property="product" title="${message(code: 'price.product.label', default: 'Product')}" />
						<g:sortableColumn style="text-align: right;" property="price" title="${message(code: 'price.price.label', default: 'Price')}" />
					</tr>
				</thead>
				<tbody>
				<g:if test="${priceInstanceList == null}" >
						<tr><td><div class="alert alert-error" style="display: block">No Price Found!</div></td></tr>
				</g:if>
				<g:else>
					<g:each in="${priceInstanceList}" status="i" var="priceInstance">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							<td>${fieldValue(bean: priceInstance, field: "product.barcode")}</td>
							<td>${fieldValue(bean: priceInstance, field: "product.productName")}</td>
							<td style="text-align: right;" ><g:link action="show" id="${priceInstance.id}" style="font: bold;font-style: italic;quotes: inherit;font-family: cursive;" >
									<g:formatNumber number="${priceInstance?.price}" type="currency" currencySymbol="\$" currencyCode="USD"  maxFractionDigits="2" />
							</g:link></td>
						</tr>
					</g:each>
				</g:else>
				</tbody>
			</table>
			<div id="paginate" class="pagination" >
				<g:paginate total="${priceInstanceCount ?: 0}" />
			</div>
			</div>
		</div>
		<g:javascript>
				$("#searchText").keyup(function (e) {
				    $.ajax({
			            type: 'POST',
			            url: '${createLink(action: 'search')}',
			            data: {	
			            	searchText: $("#searchText").val(),
			            	max: 10
			            }, success: function(data) {
			            	$('#result-div').replaceWith(data);
			            }
			        });
				});
		</g:javascript>
	</body>
</html>
