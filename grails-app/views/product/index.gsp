<%@ page import="com.sarathi.domain.Product" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-product" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<li style="float: right;" >
					<g:message code="sfsdf" default="Search By Barcode : " />
				<input type="text" id="searchText" name="searchText" value="${searchText}" placeholder="barcode" /></li>
				<li>
				</li>
			</ul>
		</div>
		<div id="list-product" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div id="result-div" >
			<table id="result-table">
				<thead>
						<tr>
							<g:sortableColumn property="productName" title="${message(code: 'product.productName.label', default: 'Product Name')}" />
							<g:sortableColumn property="description" title="${message(code: 'product.description.label', default: 'Description')}" />
							<g:sortableColumn property="barcode" title="${message(code: 'product.barcode.label', default: 'Barcode')}" />
						</tr>
					</thead>
					<tbody>
					<g:if test="${productInstanceList == null}" >
						<tr><td><div class="alert alert-error" style="display: block">No Products Found!</div></td></tr>
					</g:if>
					<g:else>
						<g:each in="${productInstanceList}" status="i" var="productInstance">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
								<td><g:link action="show" id="${productInstance.barcode}">${fieldValue(bean: productInstance, field: "productName")}</g:link></td>
								<td>${fieldValue(bean: productInstance, field: "description")}</td>
								<td>${fieldValue(bean: productInstance, field: "barcode")}</td>
							
							</tr>
						</g:each>
					</g:else>
					</tbody>
			</table>
			<div id="paginate" class="pagination">
				<g:paginate total="${productInstanceCount ?: 0}"/>
            </div>
			</div>
		</div>
		<g:javascript>
			$(document).ready(function(){
				alert(productInstanceCount);
			});
		</g:javascript>
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
