<script type="text/javascript"><!--
	function CurrencyFormatted(amount)
	{
		var i = parseFloat(amount);
		if(isNaN(i)) { i = 0.00; }
		var minus = '';
		if(i < 0) { minus = '-'; }
		i = Math.abs(i);
		i = parseInt((i + .005) * 100);
		i = i / 100;
		s = new String(i);
		if(s.indexOf('.') < 0) { s += '.00'; }
		if(s.indexOf('.') == (s.length - 2)) { s += '0'; }
		s = minus + s;
		return s;
	}
	
	function CommaFormatted(amount)
	{
		var delimiter = ","; // replace comma if desired
		var a = amount.split('.',2)
		var d = a[1];
		var i = parseInt(a[0]);
		if(isNaN(i)) { return ''; }
		var minus = '';
		if(i < 0) { minus = '-'; }
		i = Math.abs(i);
		var n = new String(i);
		var a = [];
		while(n.length > 3)
		{
			var nn = n.substr(n.length-3);
			a.unshift(nn);
			n = n.substr(0,n.length-3);
		}
		if(n.length > 0) { a.unshift(n); }
		n = a.join(delimiter);
		if(d.length < 1) { amount = n; }
		else { amount = n + '.' + d; }
		amount = minus + amount;
		return amount;
	}
--></script>

<c:if test="${shoppingCart.totalPrice gt 0}">
	<table border="0" cellspacing="1" cellpadding="0" class="tblquote" width="297px">
		<tr>
			<th colspan = "2"><div align="center"><a href="${menu['quote'].url}">Quote</a></div></th>
		</tr>				
		<!-- cart items --> 
		<c:forEach items="${cartItemList}" var="cartItem" varStatus="counter">
			<tr class="${counter.count mod 2 eq 0 ? 'alt' : ''}">
				<td width="65%" class="left">
					${cartItem.itemDetail.name}
				</td>
				<td align="right" class="leftcurrency" width="35%"><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${cartItem.itemDetail.price})))</script></td>
			</tr>		
		</c:forEach>
		<tr><td colspan="2"></td></tr>
		<tr><td colspan="2" height="1px" class="line"></td></tr>
		
		<!-- total -->		
		<tr>
			<td class="left">
				<strong>Total:</strong> 
			</td>
			<td class="leftcurrency">
				<div id="discountedPrice"><strong><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${shoppingCart.totalPrice})))</script></strong></div>
			</td>
		</tr>
	</table>
	<div align="right" style="padding-right:20px">
	<a href="${menu['quote'].url}">view details &raquo;</a>
	</div>
</c:if>