<span id="calculatedPrice" style="font: bold;quotes: inherit;font-family: cursive;" class="property-value" aria-labelledby="prices-label">
	<g:message message="Please add more than 4 prices and try again" />
</span>
<g:javascript>
$("#calculatedPrice").hover(function() {
	$(this).css('cursor','pointer').attr('title', "${strategyHint}");
}, function() {
	$(this).css('cursor','auto');
});
</g:javascript>