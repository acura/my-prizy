<div id="result-div"  style="width: 100%;" >
<table id="result-table" border="2" style="width: 100%;text-align: center; font: medium;" >
	<thead>
		<tr>
			<td>Product Name</td>
			<td>Description</td>
			<td>Barcode</td>

		</tr>
	</thead>
	<tbody>
		<g:each in="${productInstanceList}" status="i" var="productInstance">
			<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

				<td><g:link action="show" id="${productInstance.id}">
						${fieldValue(bean: productInstance, field: "productName")}
					</g:link></td>

				<td>
					${fieldValue(bean: productInstance, field: "description")}
				</td>

				<td>
					${fieldValue(bean: productInstance, field: "barcode")}
				</td>

			</tr>
		</g:each>
	</tbody>
</table>
<div id="paginate" class="pagination">
	<g:paginate total="${productInstanceCount ?: 0}" />
</div>
</div>