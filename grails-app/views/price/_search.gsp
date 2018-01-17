		<div id="result-div" >
			<table id="result-table">
			<thead>
					<tr>
						<th><g:message code="price.product.barcode.label" default="Barcode" /></th>
						<th><g:message code="price.product.label" default="Product" /></th>
						<g:sortableColumn property="price" title="${message(code: 'price.price.label', default: 'Price')}" />
					</tr>
				</thead>
				<tbody>
				<g:if test="${priceInstanceList.size() == 0}" >
					<tr><td><div class="alert alert-error" style="display: block">No Prices Found!</div></td></tr>
				</g:if>
				<g:else>
					<g:each in="${priceInstanceList}" status="i" var="priceInstance">
					<tr align="right" class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>${fieldValue(bean: priceInstance, field: "product.barcode")}</td>
						<td>${fieldValue(bean: priceInstance, field: "product.productName")}</td>
						<td>
							<g:link action="show" id="${priceInstance.id}">
								${fieldValue(bean: priceInstance, field: "price")}
							</g:link>
						
						</td>
					</tr>
				</g:each>
				</g:else>	
				</tbody>
			</table>
			<div id="paginate" class="pagination" >
				<g:paginate total="${priceInstanceCount ?: 0}" />
			</div>
			</div>