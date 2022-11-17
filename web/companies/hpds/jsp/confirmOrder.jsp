<%@include file="includes/taglibs.jsp" %>

<c:set var="navMenu" value="login" />
<%@include file="includes/header.jsp" %>
<%@include file="includes/nav.jsp" %>
<div class="clear"></div>
<script type="text/javascript">
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
</script>
<div class="content2">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr valign="top">
		  <td>
		    
			<div class="mainContent3">
			<h1>${page.title}</h1>
			 
			  <div class="welcomeNote">
			  
			  	<p>
				By clicking 'Confirm Order', you certify that the shipping information is correct and you are to purchase the items.
				Please note once your Order is submitted, we will contact you and provide you the actual prices for this Order.
				</p>
				
				<form name="formName" action="addToOrder.do">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tblquote">
					<tr>
					<th width="5%"><strong>Qty</strong></th>
					<th>Code</th>
					<th width="60%"><strong>Item</strong></th>
					<th widht="20%"><strong>Price</strong></th>
					</tr>
					<tr><td colspan="4"></td></tr>
					<c:forEach begin="0" end="${fn:length(shoppingCart.items)-1}" var="i" step="2">
						<tr class="alt">
							<td align="center">${shoppingCart.items[i].quantity }</td>
							<td align="center">${catItem[i].sku}</td>
							<td>${cartItemList[i].itemDetail.name}</td>						
							<td class="currency"><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${shoppingCart.items[i].quantity * shoppingCart.items[i].itemDetail.price})))</script></td>
						</tr>
						
						<c:if test="${shoppingCart.items[i+1] ne null}">
							<tr>
								<td align="center">${shoppingCart.items[i+1].quantity }</td>
								<td align="center">${catItem[i+1].sku}</td>
								<td>${cartItemList[i+1].itemDetail.name}</td>							
								<td class="currency"><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${shoppingCart.items[i+1].quantity * shoppingCart.items[i+1].itemDetail.price})))</script></td>
							</tr>
						</c:if>
						<c:set var="counter" value="${i+1}"/>
					</c:forEach>
					
					<tr><td colspan="4"></td></tr>
					<tr><td colspan="4" height="1px" class="line"></td></tr>
					<tr><td colspan="4"></td></tr>
					
					<tr>
						<td colspan="2"></td>
						<td>Sub Total:</td>
						<td class="currency"><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${ shoppingCart.formattedTotalItemsPrice})))</script></td>
					</tr>
					<%-- 
					<tr>
						<td colspan="2"></td>
						<td>Home Service Charge (+10% of Total)</td>
						<td class="currency">
							<c:if test="${homeService ne null}">
								<script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${totalPrice * 0.1})))</script>
								<input type="hidden" name="homeService" id="homeService" value="${homeService}"/>
							</c:if>
							<c:if test="${homeService eq null}">
								0.00
							</c:if>
						</td>
					</tr>
					
					<tr>
						<td colspan="2"></td>
						<td>Discount:</td>
						<td class="currency"><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${totalPrice*(discount/100)})))</script></td>
					</tr>
					--%>
					
					<tr class="alt">
						<td colspan="2"></td>
						<td class="payamount"><strong>Final Price:  </strong></td>
						<td class="currency"><strong><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${shoppingCart.totalPrice})))</script></strong></td>
					</tr>
					
					<tr><td colspan="4"></td></tr>
					<tr><td colspan="4" height="1px" class="line"></td></tr>
					<tr><td colspan="4"></td></tr>
					
					<tr><td colspan="4"><strong>Name:</strong> ${name}</td></tr>
					<tr><td colspan="4"><strong>Phone number:</strong> ${phoneNumber} </td></tr>
					<tr><td colspan="4"><strong>Email:</strong> ${email} </td></tr>
					<tr><td class="comment" colspan="4"><strong>Remarks:</strong></td></tr>
					<tr><td colspan="4"><c:if test="${comments == ''}">none</c:if><c:if test="${comments != ''}"></c:if> ${ comments }</td></tr>
					<tr><td height="15px"></td></tr>
					<tr><td colspan="4"></td></tr>
					<tr><td>
					<div style="display:none">
						<c:forEach items="${cartItem}" var="ct">
													<input type="checkbox" name="cartItem" value="${ct }" CHECKED>
													
						</c:forEach>
						<input type="hidden" name="discount" value="${discount}"/>
					</div>
					</td></tr>							
				</table>
				<div align="right" style="margin:10px 0">
				<table border="0">
					<tr>
						<td><a href="shipping.do">&laquo;back to save to all Quotes</a></td>
						<td width="15%"><input type="submit" value="Confirm and Save"></td>
					</tr>
					<tr>
						<td><input type="hidden" id="comments" name="comments" value="${ comments }"></input></td>
						<td><input type="hidden" id="cart_order_id" name="cart_order_id" value="${ cartOrder.id }"></input></td>
					</tr>
				</table>	
				</div>
				</form>
			    
			  </div>

			</div><!--//mainContent-->
			
		  </td>
		</tr>
	  </table>
	</div><!--//content-->
<%@include file="includes/footer.jsp" %>