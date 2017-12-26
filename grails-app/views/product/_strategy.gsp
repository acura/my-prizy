<span id="calculatedPrice" style="font: bold;font-style: italic;quotes: inherit;font-family: cursive;" class="property-value" aria-labelledby="prices-label">
<g:formatNumber number="${price}" type="currency" currencySymbol="\$" currencyCode="USD"  maxFractionDigits="2" /></span>
<g:javascript>
$("#calculatedPrice").hover(function() {
				        $(this).css('cursor','pointer').attr('title', "${strategyHint}");
				    }, function() {
				        $(this).css('cursor','auto');
				    });
				    </g:javascript>