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
			  
			  	<s:div>
				<s:actionerror/>
				<s:actionmessage/>
				${param.notificationMessage}
				</s:div>
			
				<h1>Quotes number ${cartOrder.id}</h1>
				
			  	<table width="100%" cellspacing="0" cellpadding="0" class="tblquote">
					<!-- TABLE HEADERS -->				
					<tr class="headbox">
						<th width="20%"><strong> Ordered on</strong></th>
						<th width="5%">Qty</th>
						<th width="15%"><strong>Code</strong></th>
						<th width="50%"><strong>Item Name</strong></th>																		
						<th width="10%"><strong>Price</strong></th>						
					</tr>	
					
					<!-- item list -->
					<c:if test = "${fn:length(orderItemList) > 0}">
					<c:forEach begin="0" end="${fn:length(orderItemList)-1}"  var="orderItem" step="2">
						<tr><td colspan="5" height="2px"></td></tr>
						<tr>
							<td>${orderItemList[orderItem].createdOn}</td>
							<td class="centertxt">${orderItemList[orderItem].quantity}</td>
							<td class="centertxt">${orderItemList[orderItem].itemDetail.sku}</td>
							<td>${orderItemList[orderItem].itemDetail.name}</td>
							<td class="currency"><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${orderItemList[orderItem].itemDetail.price})))</script></td>
						</tr>
						
						<c:if test="${orderItemList[orderItem+1] ne null}">
						<tr class="alt">
							<td>${orderItemList[orderItem+1].createdOn}</td>
							<td class="centertxt">${orderItemList[orderItem+1].quantity}</td>
							<td class="centertxt">${orderItemList[orderItem+1].itemDetail.sku}</td>
							<td>${orderItemList[orderItem+1].itemDetail.name}</td>
							<td class="currency"><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${orderItemList[orderItem+1].itemDetail.price})))</script></td>
						</tr>
						</c:if>
						<c:set var="counter" value="${orderItem+1}"/>						
					</c:forEach>
					</c:if>
					
					<!-- total price -->
					<tr> <td colspan="5" height="10px"></td></tr>
					<tr><td colspan="5" height="1px" class="line"></td></tr>
					<tr><td colspan="5"></td></tr>
					
					<tr>
						<td colspan="3"></td>
						<td>Sub Total:</td>
						<td class="currency">${cartOrder.totalPriceOkFormatted }</td>
					</tr>
					<%-- 
					<tr>
						<td colspan="3"></td>
						<td>
							Home Service Charge (+10% of Total)
						</td>
						<td class="currency">
							<c:if test="${cartOrder.otherCharges ne null}">		
								<script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${cartOrder.totalPriceOk * cartOrder.otherCharges})))</script>
							</c:if>
						</td>
					</tr>
					
					<tr>
						<td colspan="3"></td>
						<td>Discount:</td>
						<td class="currency">
								<script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${cartOrder.totalPriceOk * (orderItemList[0].itemDetail.discount / 100)})))</script>
						</td>
					</tr>
					--%>
					<tr>
						<td colspan="3"></td>
						<td><strong>Final Price:</strong></td>
						<td class="currency"><strong>
						<c:if test="${cartOrder.otherCharges ne null}">
							<script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${cartOrder.totalPriceOk})))</script>
						</c:if>
						<c:if test="${cartOrder.otherCharges eq null}">
							<script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${cartOrder.totalPriceOk - (cartOrder.totalPriceOk * (orderItemList[0].itemDetail.discount / 100)) })))</script>
						</c:if>
						</strong></td>
					</tr>
					<tr>
						<td colspan="3">
							<div style="float:right">
							  	<c:set var="actionDo" value="orderitem.do"/>
							  	<jsp:include page="../../../common/includes/paging-util.jsp" />    
						  	</div>  
				  		</td>
				  	</tr>
				  	<tr> <td colspan="4" height="5"></td></tr>
				</table>
				<div style="margin:10px 0"><a href="allQuotes.do">&laquo;back to All Quotes</a>&nbsp; | &nbsp;<a href="downloadOrder.do?cart_order_id=${cartOrder.id}"><img src="images/pdf.gif"/>Print in PDF</a></div>
			  		  
			  </div>
			  <%--<p align="right">For more info, Please send us an email at: <a href="/">info@hi-precision.com</a></p>
			  &nbsp; --%>
			</div><!--//mainContent-->
		  </td>
		</tr>
	  </table>
	</div><!--//content-->
<%@include file="includes/footer.jsp" %>