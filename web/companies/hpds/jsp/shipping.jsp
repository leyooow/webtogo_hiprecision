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

function validate_submit(){
	var name = document.getElementById('shippingName').value;
	var phoneNumber = document.getElementById('phoneNumber').value;
	var email = document.getElementById('email').value;
	var message = "";

	if(name.length == 0)
		message += "Invalid name.\n";
	if(phoneNumber.length > 0){
		if(!phoneNumber.match(/^[0-9]+$/))
			message += "Invalid Phone Number.\n";
	}
	if(email.length > 0){
		if(!email.match(/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]+$/))
			message += "Invalid Email Address.\n";
	}
	
	if(message != "") alert(message);
	else {
		var myForm = document.confirmOrder;
		myForm.submit();
	}
}

</script>
<div class="content2">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr valign="top">
		  <td>		    
			<div class="mainContent3">
			<h1>${page.title}</h1>			
			  <div class="welcomeNote">
			  
				<c:if test="${empty cartItemList}">
						*No orders yet			
						</c:if>
						<c:if test="${not empty cartItemList}">
							<form method="post" action="confirmOrder.do" name="confirmOrder">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tblquote">
								<tr>
									<th>Qty</th>
									<th width="10%">Code</th>
									<th width="55%">Item</th>
									<th width="15%">Price</th>
									<th width="15%">Subtotal</th>
								</tr>
								<tr><td colspan="4">	</td></tr>
								<c:forEach begin="0" end="${fn:length(cartItemList)-1}" var="i" step="2">
									<tr class="alt">
									<td><div align="center">${cartItemList[i].quantity }</div></td>
									<td align="center">${catItem[i].sku}</td>
									<td>${cartItemList[i].itemDetail.name }</td>
									<td><span style="float: right;"><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${cartItemList[i].itemDetail.price })))</script></span> </td>
									<td><span style="float: right;"><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${cartItemList[i].quantity * cartItemList[i].itemDetail.price})))</script></span> </td>
									</tr>
									<c:if test="${cartItemList[i+1] ne null}">
										<tr>
										<td><div align="center">${cartItemList[i+1].quantity }</div></td>
										<td align="center">${catItem[i+1].sku}</td>
										<td>${cartItemList[i+1].itemDetail.name }</td>
										<td><span style="float: right;"><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${cartItemList[i+1].itemDetail.price })))</script></span> </td>
										<td><span style="float: right;"><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${cartItemList[i+1].quantity * cartItemList[i+1].itemDetail.price})))</script></span> </td>
										</tr>
									</c:if>
									<c:set var="counter" value="${i+1}"/>
								</c:forEach>
								
								<tr><td colspan="5"></td></tr>
								<tr><td colspan="5" height="1px" class="line"></td></tr>
								<tr><td colspan="5"></td></tr>
								
								<tr>
									<td colspan="2"></td>
									<td>Sub Total:</td>
									<td></td>
									<td> <span style="float: right;">${ shoppingCart.formattedTotalItemsPrice}</span> </td>
								</tr>
								<%-- 
								<tr>
									<td colspan="2"></td>
									<td>
										Home Service Charge (+10% of Total)
									</td>
									<td></td>
									<td style="float: right;">
										<c:if test="${homeService ne null}">
											<script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${shoppingCart.totalPrice * 0.1})))</script>
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
									<td></td>
									<td> <span style="float: right;"><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${shoppingCart.totalPrice*(discount/100)})))</script></span> </td>
								</tr>
								--%>
								<tr>
									<td colspan="2"></td>
									<td><strong>Final Price:</strong></td>
									<td></td>
									<td><span style="float: right;"><strong><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${shoppingCart.totalPrice})))</script></strong></span> </td>
								</tr>
							
							<tr><td height="15px"></td></tr>
							<tr> <td colspan="5" height="8px"></td></tr>
								
							<!-- name  -->
							<tr><td colspan="2"><strong>*Name:</strong></td>
								<td colspan="4"><input type="text" name="shippingName" id="shippingName" size="35" value="${shippingName}"/></td>
							</tr>
							
							<tr><td colspan="2"><strong> Phone No.:</strong></td>
								<td colspan="4"><input type="text" name="phoneNumber" id="phoneNumber" size="35" value="${phoneNumber}"/></td>
							</tr>		
							
							<tr><td colspan="2"><strong> Email:</strong></td>
								<td colspan="4"><input type="text" name="email" id="email" size="35" value="${email}"/></td>
							</tr>		
									
							<!-- comments -->
							<tr><td valign="top" colspan="2"><strong> Remarks:</strong></td>
							<td colspan="3">
							<textarea id="comments" name="comments" rows="2" cols="27">${comments}</textarea><br /><br />
							<div style="display:none">
							<c:forEach items="${cartItem}" var="ct">
								<input type="checkbox" name="cartItem" value="${ct }" CHECKED>
							</c:forEach>
							</td>
							</div>
							</tr>											
							</table>
							<div align="right" style="margin:10px 0">
							<table border="0" width="100%">
								<tr>
								<td><a href="${menu['quote'].url}">&laquo;back to Quote</a></td>
								<td width="15%"><input type="button" value="Save to All Quotes" onclick="validate_submit()"/></td>							
							</tr></table>
							</div>
							
							</form>
	
					</c:if>
			  
			  </div>
			 <%-- <p align="right">For more info, Please send us an email at: <a href="/">info@hi-precision.com</a></p> --%>

			</div><!--//mainContent-->
		  </td>
		</tr>
	  </table>
	</div><!--//content-->
<%@include file="includes/footer.jsp" %>