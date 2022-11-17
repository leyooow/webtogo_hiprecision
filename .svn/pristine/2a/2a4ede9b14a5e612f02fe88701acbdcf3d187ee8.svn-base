<%@include file="includes/taglibs.jsp" %>

<c:set var="navMenu" value="login" />
<%@include file="includes/header.jsp" %>
<%@include file="includes/nav.jsp" %>
<div class="clear"></div>

<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWRCartAction.js'></script>
<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>

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

	function homeServiceCharge(){
		var price = ${shoppingCart.totalPrice};
		var homeService = document.getElementById('homeService');
		var discountPrice = price*(document.getElementById('discount').value/100);
		var charge = price * 0.1;
		

		if(homeService.checked == true){
			document.getElementById('homeServiceCharge').innerHTML = CommaFormatted(CurrencyFormatted(price*.1));
			document.getElementById('discountedPrice').innerHTML = "<strong>"+CommaFormatted(CurrencyFormatted(price  + charge - discountPrice))+"</strong>";
		}
		else{
			document.getElementById('homeServiceCharge').innerHTML = '0.00';
			document.getElementById('discountedPrice').innerHTML = "<strong>"+CommaFormatted(CurrencyFormatted(price  - discountPrice))+"</strong>";
		}
	}

	function updateDiscount(){
		var price = ${shoppingCart.totalPrice};
		var discountPrice = price*(document.getElementById('discount').value/100);
		var discountedPrice = price - discountPrice;
		var homeService = document.getElementById('homeService');
		var charge = price * 0.1;
		
		document.getElementById('discountPrice').innerHTML = CommaFormatted(CurrencyFormatted(discountPrice));
		if(homeService.checked == true)
			document.getElementById('discountedPrice').innerHTML = "<strong>"+CommaFormatted(CurrencyFormatted(discountedPrice + charge))+"</strong>";
		else
			document.getElementById('discountedPrice').innerHTML = "<strong>"+CommaFormatted(CurrencyFormatted(discountedPrice))+"</strong>";
	}

	function checkAll(){
		var checkall = document.getElementById('checkall');
		var cartItems = document.getElementsByName('cartItem');
		if(checkall.checked == true){
			for(var i=0; i<cartItems.length; i++)
				cartItems[i].checked=true;
		}
		else{
			for(var i=0; i<cartItems.length; i++)
				cartItems[i].checked=false;
		}
	}
	
	function deleteSelectedItems(){
		
		if(confirm("Are you sure you want to delete the item/s?")){
			var checkboxList = document.getElementsByName("cartItem");
			cartItemId = "";
			//populate item list
			itemList = "[";
			for (index = 0; index < checkboxList.length; index++){
				if(checkboxList[index].checked){
					//remove checked element				
					itemList +=	checkboxList[index].value + ",";
				} else {
					cartItemId = checkboxList[index].value;
				}
			}
			itemList += "]";
			//remove cart items from database
			removeCartItems(itemList, cartItemId);
		}
	}
   
	function removeCartItems(idList, cartItemId){
		DWRCartAction.deleteCartItems(idList,{
			callback:function(notificationMessage){
				alert(notificationMessage);

				if(cartItemId != "") {
					//update total
					updateTotal(cartItemId);
				} else {
					document.getElementsByName("cartItemList")[0].innerHTML = "No Items in cart"; 
				}			
			}
		});		
	}	
	function updateTotal(cartItemId){
		var quantity = document.getElementById("quantity" + cartItemId).value;
		
		DWRCartAction.updateCartItem(cartItemId, quantity, {
			callback:function(totalPrice){		
		
				checkboxList = document.getElementsByName("cartItem");				
				//remove elements in the form
				for (index = 0; index < checkboxList.length; index++){
					if(checkboxList[index].checked){	
						//hide row 
						document.getElementById("cartItem_" + checkboxList[index].value).style.display = "none";
						//update to unchecked
						checkboxList[index].checked = false;
						//set quantity to 0
						document.getElementById("cartItem_" + checkboxList[index].value).value = 0;						
					}				
				}
				
				document.getElementById("totalPrice").innerHTML = totalPrice;
				document.getElementById("finalPrice").innerHTML = totalPrice;
			}
		});		
	}

	function updateQuantity(){
	var checkId;
	var totalPrice = 0;
	checkboxList = document.getElementsByName("cartItem");
	//remove elements in the form
		for (index = 0; index < checkboxList.length; index++){
			checkId = checkboxList[index].value;
			quantity = document.getElementById("quantity" + checkId).value;
			DWRCartAction.updateCartItem(checkId, quantity, {
				callback:function(totalPrice){
				document.getElementById("totalPrice").innerHTML = totalPrice;
				document.getElementById("finalPrice").innerHTML = totalPrice;
				}
			});	
		}
		validate();
	}
	
	var isError = false;
	
	function validate() {
		var string = "Invalid value at item number/s:";
		isError = false;
		for (index = 0; index < checkboxList.length; index++){
			checkId = checkboxList[index].value;
			var quantity = document.getElementById('quantity'+checkId).value;
  			if(!quantity.match(/^[0-9]+$/)){
  				string += "\n     " + (index+1);
  				isError = true;
  			}
		}

		if(isError){
			alert("" + string);
		}
		else
			document.getElementById('updateMsg').style.color = 'red';			
	}


	function validateSubmit() {
		var checkId;
		var totalPrice = 0;

		checkboxList = document.getElementsByName("cartItem");				
		//remove elements in the form
			for (index = 0; index < checkboxList.length; index++){
				checkId = checkboxList[index].value;
				quantity = document.getElementById("quantity" + checkId).value;
				DWRCartAction.updateCartItem(checkId, quantity, {
					callback:function(totalPrice2){
					document.getElementById("totalPrice").innerHTML = CommaFormatted(CurrencyFormatted(totalPrice2));
					totalPrice = totalPrice2;
					var string = "Invalid value at item number/s:";
					isError = false;
					var nCount = 0;
					for (index = 0; index < checkboxList.length; index++){
						checkId = checkboxList[index].value;
						var quantity = document.getElementById('quantity'+checkId).value;
			  			if(!quantity.match(/^[0-9]+$/)){
			  				string += "\n     " + (index+1);
			  				isError = true;
			  			}
			  			if(checkboxList[index].checked==true){
							nCount++;
			  	  		}
					}
							
					if(isError){
						alert("" + string);
					}
					else{
						var myForm = document.cartItemList;
						 myForm.submit();
					}			
					}
				});	
			}
			
		
	}

--></script>
<div class="content2">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr valign="top">
		  <td>
		    
			<div class="mainContent3">
			<h1>${page.title}</h1>
			  
			  <div class="welcomeNote">
			  	
			  	<img src="images/grayOvalTl.jpg" class="itl" />
		        <img src="images/grayOvalTr.jpg" class="itr" />
		        <img src="images/grayOvalBl.jpg" class="ibl2" />
		        <img src="images/grayOvalBr.jpg" class="ibr" />
		        
			  	<s:div>
				<s:actionerror/>
				<s:actionmessage/>
				${param.notificationMessage}
				</s:div>
				<c:choose>
				<c:when test="${shoppingCart.itemCount != 0}">
			<!-- cart items -->
			<form action="shipping.do" name="cartItemList" name = "shippingForm" method="post">
				<table border="0" cellspacing="1" cellpadding="0" class="tblquote" width="100%">
					<tr>
					<th width="15%" style="padding-left:40px" align="left"><input type="checkbox" name="checkall" id="checkall" onclick="checkAll();"> All</th>
					<th>Qty</th>
					<th width="10%">Code</th>
					<th width="55%">Items</th>
					<th width="20%"><strong>Price</strong></th>
					</tr>				
					<!-- cart items --> 
					<c:forEach begin="0" end="${fn:length(cartItemList)-1}" var="i" step="2">
						<tr name="cartItem_${cartItemList[i].id}" id="cartItem_${cartItemList[i].id}" class="alt">
							<td>
							${ i+1 }&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
							<input type="checkbox" name="cartItem" id="cartItem" value="${cartItemList[i].id}">
							</td>
							<td>1</td>
							<td align="center">${catItem[i].sku}</td>
							<td>
							${cartItemList[i].itemDetail.name }
							</td>
							<td align="right" class="currency"><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${cartItemList[i].itemDetail.price})))</script> <input type="hidden" id="quantity${cartItemList[i].id}" name="quantity${cartItemList[i].id}" name="cartItemQuantity" value="1"/></td>
						</tr>
						<c:if test="${cartItemList[i+1] ne null}">
							<tr name="cartItem_${cartItemList[i+1].id}" id="cartItem_${cartItemList[i+1].id}">
								<td>
								${ i+2 }&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
								<input type="checkbox" name="cartItem" id="cartItem" value="${cartItemList[i+1].id}">
								</td>
								<td>1</td>
								<td align="center">${catItem[i+1].sku}</td>
								<td>
								${cartItemList[i+1].itemDetail.name }
								</td>
								<td align="right" class="currency"> <script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${cartItemList[i+1].itemDetail.price})))</script> <input type="hidden" id="quantity${cartItemList[i+1].id}" name="quantity${cartItemList[i+1].id}" name="cartItemQuantity" value="1"/></td>
							</tr>
						</c:if>
					</c:forEach>
					
					<tr><td colspan="5"></td></tr>
					<tr><td colspan="5" height="1px" class="line"></td></tr>
					<tr><td colspan="5"></td></tr>
					<!-- total -->
					<tr>				
						<td colspan="3"></td>		
						<td>
							Sub Total : 
						</td>
						<td align="right" class="currency">
							<div id="totalPrice" style="margin-left:0"> <script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${shoppingCart.totalPrice})))</script></div>
							<input type="hidden" name="totalPrice" id="totalPrice" value="${shoppingCart.totalPrice}"/>
						</td>
					</tr>
					<%-- 
					<!-- Home Service Charge  -->
					<tr>
						<td colspan="3"></td>
						<td><input type="checkbox" name="homeService" id="homeService" <c:if test="${homeService ne null}">checked="checked"</c:if> onClick="homeServiceCharge()">Home Service Charge (+10% of Total)</td>
						<td align="right" class="currency">
							<div id="homeServiceCharge">
								<c:if test="${homeService ne null}">
									<script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${shoppingCart.totalPrice * 0.1})))</script>
								</c:if>
								<c:if test="${homeService eq null}">
									0.00
								</c:if>
							</div> 
						</td>
					</tr>
						
					<tr>
						<td colspan="3"></td>
						<td>
							Discount : <select name="discount" id="discount" onchange="updateDiscount();">
								<option value="0" <c:if test="${discount eq 0}">selected</c:if>>0%</option>
								<option value="5" <c:if test="${discount eq 5}">selected</c:if>>5%</option>
								<option value="10" <c:if test="${discount eq 10}">selected</c:if>>10%</option>
								<option value="15" <c:if test="${discount eq 15}">selected</c:if>>15%</option>
								<option value="20" <c:if test="${discount eq 20}">selected</c:if>>20%</option>
							</select>
						</td>
						<td align="right" class="currency">
							<div id="discountPrice"> <c:if test="${discount ne null}"><script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${shoppingCart.totalPrice * (discount / 100)})))</script></c:if><c:if test="${discount eq null}">0.00</c:if></div>
						</td>
					</tr>	
					--%>	
					<tr>
						<td colspan="3">
						<td>
							<strong>Final Price:</strong> 
						</td>
						<td align="right" class="currency">
							<strong><span id="finalPrice"> <script type="text/javascript">document.write(CommaFormatted(CurrencyFormatted(${shoppingCart.totalPrice})))</script></span></strong>
						</td>
					</tr>
			</table>		<!-- delete & checkout buttons -->		
			</form>
			<br>
			<div align="right">
			<table border="0">
				<tr>
				<td><input type="button" value="Delete" onclick="deleteSelectedItems()" class="loginButton"></td>												
				<td>
					<form method="post" id="theForm" name="selectionForm" action="shipping.do">
					<input type="button" value="Save this quote" id="checkout" onclick="validateSubmit()" class="loginButton">
					</form>
				</td>
			</table>
			</div>
				
		</c:when>
		<c:otherwise>
			<ul><li>No Items in Quote</li></ul>
		</c:otherwise>
	</c:choose>
				
			  </div>

			</div><!--//mainContent-->
			
		  </td>
		</tr>
	  </table>
	</div><!--//content-->
<%@include file="includes/footer.jsp" %>