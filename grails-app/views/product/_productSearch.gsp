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
					<g:if test="${productInstanceList.size() == 0}" >
						<tr><td><g:message code="empty.list.message" args="['Product']" /></td></tr>
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
				<g:paginate total="${productInstanceCount ?: 0}" />
			</div>
			</div>
