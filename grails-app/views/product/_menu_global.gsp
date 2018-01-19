
<li class="controller"><a class="home"
	href="${createLink(uri: '/')}"> <g:message
			code="default.home.label" /></a></li>
<li><g:link class="list" controller="product" action="index">
		<g:message code="default.list.label" args="['Product']" />
	</g:link></li>
<li><g:link class="create" controller="product" action="create">
		<g:message code="default.new.label" args="['Product']" />
	</g:link></li>
<li><g:link class="search" controller="product" action="loadPrice"
		onclick="resetBarCodeIndex()">
		<g:message code="default.loader.label" args="['Product']" />
	</g:link></li>