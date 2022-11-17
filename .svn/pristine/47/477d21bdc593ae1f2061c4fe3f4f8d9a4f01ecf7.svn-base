<%@include file="includes/header.jsp"  %>

<script src="../admin/javascripts/prototype.js" type="text/javascript"></script>
<script src="../admin/javascripts/scriptaculous.js" type="text/javascript"></script>

<c:choose>
<c:when test="${company.name eq 'hiprecisiononlinestore' }">
	<script type='text/javascript' src='https://store.hi-precision.com.ph/dwr/interface/DWROrderAction.js'></script>
	<script type='text/javascript' src='https://store.hi-precision.com.ph/dwr/engine.js'></script>
	<script type='text/javascript' src='https://store.hi-precision.com.ph/dwr/util.js'></script>
</c:when>
<%-- <c:when test="${company.name eq 'gurkkatest' }">
	<script type='text/javascript' src='${fn:replace(contextParams['HTTP_SERVER'], 'http:', 'https:')}/dwr/interface/DWROrderAction.js'></script>
	<script type='text/javascript' src='${fn:replace(contextParams['HTTP_SERVER'], 'http:', 'https:')}/dwr/engine.js'></script>
	<script type='text/javascript' src='${fn:replace(contextParams['HTTP_SERVER'], 'http:', 'https:')}/dwr/util.js'></script>
</c:when> --%>
<c:otherwise>
	<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWROrderAction.js'></script>
	<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>
	<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/util.js'></script>
</c:otherwise>
</c:choose>

<c:set var="menu" value="order" />
<c:set var="pagingAction" value="orderitem.do" />
<c:set var="gurkkaTotalPrice" value="${0}" />

<head>
	<script type="text/javascript">
		function closeModal() {
			Effect.Fade('OverlayContainer');
		}

		function showOrderList(cartOrderID) {
			DWROrderAction.getOrderList(cartOrderID,{
				callback:function(messageContent){
					showMessageContent(messageContent);
				}
			}); 
		}
		function showMessageContent(messageContent) {
			currentOrderList = document.getElementById("ModalBox");
			currentOrderList.innerHTML = "<a href='#' onclick='closeModal()'"
				+ " style=\"float:  right;\">" 
				+ "<img src=\"images\/x.gif\"></a>"
				+ messageContent;
			Effect.Appear('OverlayContainer');
		}
		function updateItemStatus(cartOrderID){	
			if(confirm("Are you sure you want to Update? It will change the order status.")){
				statusList1 = document.getElementsByName("allStatus");
				var orderStatus = new Array(statusList1.length);
				
				for(index1 = 0; index1 < statusList1.length; index1++){
					statusList=statusList1[index1];
					for(index = 0; index < statusList.length; index++){
						if(statusList[index].selected){
							orderStatus[index1] = statusList[index].value;	
						}
					}
				}
	
				DWROrderAction.updateOrderItemStatus(cartOrderID, orderStatus, {
					callback:function(messageContent){
					//alert(messageContent);
					var msgLoad = '';


					<c:if test="${company.name ne 'hbc' and company.name ne 'hiprecisiononlinestore' and company.name ne 'gurkkatest'}">
						Element.remove("price1");				
						msgLoad='<span id="price1">'+messageContent+'</span>';
						new Insertion.Before('price2', msgLoad);
						alert("New Total Price:  "+messageContent);
						
					</c:if>
					
			
					}
				});
			}
		}

		function updateStatus(cartOrderID){
			statusList = document.getElementsByName("orderStatus_" + cartOrderID);

			<c:if test="${company.id eq 319 or company.id eq 346}">
			var doUpdate = confirm('Are you sure you want to update status?');
			var gurkkaStat = document.getElementById('gurkkaOrderStatus');
			var stat = gurkkaStat.options[gurkkaStat.selectedIndex].value;
			
			if(doUpdate == true)
			{</c:if>
			
				paymentStatus = document.getElementById("paymentStatus").options[document.getElementById("paymentStatus").selectedIndex].value;
				DWROrderAction.updatePaymentStatus(cartOrderID, paymentStatus, {
					callback:function(messageContent){
	//					alert(messageContent);
					}
				});
	
				shippingStatus = document.getElementById("shippingStatus").options[document.getElementById("shippingStatus").selectedIndex].value;
				DWROrderAction.updateShippingStatus(cartOrderID, shippingStatus, {
					callback:function(messageContent){
	//					alert(messageContent);
					}
				});
			
				for(index = 0; index < statusList.length; index++){
					if(statusList[index].selected){
						orderStatus = statusList[index].value;	
						DWROrderAction.updateOrderStatus(cartOrderID, orderStatus, {
							callback:function(messageContent){
								<c:if test="${company.id eq 319 or company.id eq 346}">
									if(orderStatus == 'CANCELLED')
									{
										document.getElementById('gurkkaOrderStatus').disabled = true;
										document.getElementById('paymentStatus').disabled = true;
										document.getElementById('shippingStatus').disabled = true;
									}
									else if(orderStatus == 'COMPLETED' 
											&& paymentStatus == 'PAID' 
											&& shippingStatus == 'DELIVERED')
									{
										document.getElementById('gurkkaOrderStatus').disabled = true;
										document.getElementById('paymentStatus').disabled = true;
										document.getElementById('shippingStatus').disabled = true;
									}
									else
									{
										document.getElementById('gurkkaOrderStatus').value = 'PENDING'
									}
								</c:if>
								alert(messageContent);
							}
						});
					}
				}
			<c:if test="${company.id eq 319 or company.id eq 346}">
			}
			</c:if>
		}
		
		function updateName(cartOrderID){
			orderName = document.getElementById("item_name").value;	
			orderAddress1 = document.getElementById("item_address1").value;	
			orderCity = document.getElementById("item_city").value;	
			orderCountry = document.getElementById("item_country").value;	
			orderZipCode = document.getElementById("item_zipCode").value;	
			orderPhoneNumber = document.getElementById("item_phoneNumber").value;	
			orderEmailAddress = document.getElementById("item_emailAddress").value;	
			DWROrderAction.updateOrderName(cartOrderID, orderName, orderAddress1,orderCity,orderCountry,orderZipCode,orderPhoneNumber, orderEmailAddress, {
				callback:function(messageContent){
					alert(messageContent);
				}
			});	
		}
	</script>
<body>
  <div class="container">
	
    <%@include file="includes/bluetop.jsp"%>
	<%@include file="includes/bluenav.jsp"%>

<div class="contentWrapper" id="contentWrapper">
  <div class="mainContent">
	<s:actionmessage />
	<s:actionerror />
	<c:if test="${param['evt'] != null}">
	  <ul>
		<c:if test="${param['evt'] == 'save'}">
		  <li><span class="actionMessage">Group was successfully created.</span></li>
		</c:if>

		<c:if test="${param['evt'] == 'groupexist'}">
		  <li><span class="actionMessage">Category was not created because a similar category already exist.</span></li>
		</c:if>

		<c:if test="${param['evt'] == 'update'}">
		  <li><span class="actionMessage">Group was successfully updated.</span></li>
		</c:if>
							
		<c:if test="${param['evt'] == 'delete'}">
		  <li><span class="actionMessage">Group was successfully deleted.</span></li>
		</c:if>
			
		<c:if test="${param['evt'] == 'sort'}">
		  <li><span class="actionMessage">Display order of the categories was successfully updated.</span></li>
		</c:if>
			
		<c:if test="${param['evt'] == 'sortItems'}">
		  <li><span class="actionMessage">Display order of category items was successfully updated.</span></li>
		</c:if>
			
		<c:if test="${param['evt'] == 'noCategory'}">
		  <li><span class="actionMessage">Selected group has no category.</span></li>
		</c:if>							
			
		<c:if test="${param['evt'] == 'sortBrands'}">
		  <li><span class="actionMessage">Display order of the brands was successfully updated.</span></li>
		</c:if>
			
		<c:if test="${param['evt'] == 'noBrand'}">
		  <li><span class="actionMessage">Selected group has no brand.</span></li>
		</c:if>															
	  </ul>
	</c:if>	
				
	<div class="pageTitle">
	    <h1><strong>Orders</strong>: Edit Order</h1>
		</form>
				
		<div class="clear"></div>
    </div><!--//pageTitle-->

    <ul class="pagination">
	  <%@include file="includes/pagingnew.jsp"  %>
	</ul>
			
	<div class="clear"></div>
    <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
	  <tr>
		<th> Ordered on </th>
		<th > Quantity </th>
		<th> Item </th>
		<th> Description </th>
		<th > Price </th>												
		<th> Status </th>
	  </tr>	
					
	  <!-- item list -->
	  <c:set var="c" value="0" />
	  
	  <c:choose>
	  	<c:when test="${company.name eq 'gurkkatest'}">
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		<c:forEach items="${itemList}" var="item_">
	    <tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
		  <c:set var="count" value="${count+1}" />
		  <td>
		    ${item_.createdOn}
		  </td>
		  <td>
			${item_.quantity}
		  </td>
		  <td>
		  	${item_.itemDetail.name}
		  </td>
		  <td>
		    ${item_.itemDetail.descriptionWithoutTags}
		  </td>
		  <td class="newerversion1">
			<c:choose>
				<c:when test="${fn:containsIgnoreCase(item_.categoryItem.parentGroup.name, 'promo')  }" >
					<c:if test="${ not fn:containsIgnoreCase(item_.itemDetail.name, 'Freebie')}">
						<c:set var="quant_" value="${item_.quantity}" />
						<c:set var="price_" value="${item_.itemDetail.price}" />
						<fmt:formatNumber type="number" pattern="##,##0.00" value="${(price_)*quant_}" />
						<c:set var="gurkkaTotalPrice" value="${gurkkaTotalPrice + ((price_)*quant_)}" />
					</c:if>
					
				</c:when>
				<c:otherwise>
					<c:if test="${ not fn:containsIgnoreCase(item_.itemDetail.name, 'Freebie')}">
						<c:set var="quant_" value="${item_.quantity}" />
						<c:set var="price_" value="${item_.itemDetail.price}" />
						<c:set var="id_" value="${item_.categoryItem.id}" />
						<fmt:formatNumber type="number" pattern="##,##0.00" value="${(price_ - discountMap[item_.categoryItem.id])*quant_}" />
						<input type="hidden" name="test-${id_}" value="" />
						<c:set var="gurkkaTotalPrice" value="${gurkkaTotalPrice + ((price_ - discountMap[item_.categoryItem.id])*quant_)}" />
					</c:if>
				</c:otherwise>
			</c:choose>
		  </td>
		  <td>		
			<select name="allStatus">
			
			
			<c:if test="${company.name ne 'hbc'}">
					  <c:choose>
						<c:when test="${item_.status == 'OK'}">
						  <option name="itemStatus"  value="OK" selected="selected">OK</option>
						</c:when>
						<c:otherwise>
						  <option name="itemStatus"  value="OK">OK</option>
					    </c:otherwise>
					  </c:choose>
					  <c:choose>
						<c:when test="${status == 'CANCELLED'}">
						  <option name="itemStatus"  value="CANCELLED" selected="selected">CANCELLED</option>
						</c:when>
						<c:otherwise>
						  <option name="itemStatus" value="CANCELLED">CANCELLED</option>
						</c:otherwise>
					  </c:choose>
			</c:if>
			<c:if test="${company.name eq 'hbc'}">
					
					<option name="itemStatus"  value="" <c:if test="${status eq 'OK' }">selected</c:if>>NEW</option>
				
					<option name="itemStatus"  value="IN PROCESS" <c:if test="${status eq 'IN PROCESS' }">selected</c:if>>IN PROCESS</option>
					 
					<option name="itemStatus"  value="FOR DELIVERY" <c:if test="${status eq 'FOR DELIVERY' }">selected</c:if>>FOR DELIVERY</option>
					
					<option name="itemStatus"  value="DELIVERY IN TRANSIT"  <c:if test="${status eq 'DELIVERY IN TRANSIT' }">selected</c:if>>DELIVERY IN TRANSIT</option>
					
					<option name="itemStatus"  value="DELIVERED"  <c:if test="${status eq 'DELIVERED' }">selected</c:if>>DELIVERED</option>
					
			</c:if>
			</select>
		  </td>	
		</tr>
	  </c:forEach>
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  	</c:when>
	  	<c:otherwise>
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		<s:iterator value="itemList">
	    <tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
		  <c:set var="count" value="${count+1}" />
		  <td>
		    <s:property value="createdOn"/>
		  </td>
		  <td>
			<s:property value="quantity"/>
		  </td>
		  <td>
		  	<c:choose>
			  	<c:when test="${company.name eq 'hbc'}">
			  		<s:property value="itemDetail.sku"/>
			  	</c:when>
			  	<c:otherwise>
			  		<s:property value="itemDetail.name"/>
			  	</c:otherwise>
		  	</c:choose>
		  </td>
		  <td>
		    
			<s:property value="itemDetail.descriptionWithoutTags"/>
		  </td>
		  <td>
		  	<s:property value="itemDetail.generateFormattedPrice()"/>
		  </td>
		  <td>		
			<select name="allStatus">
			
			
			<c:if test="${company.name ne 'hbc'}">
					  <c:choose>
						<c:when test="${status == 'OK'}">
						  <option name="itemStatus"  value="OK" selected="selected">OK</option>
						</c:when>
						<c:otherwise>
						  <option name="itemStatus"  value="OK">OK</option>
					    </c:otherwise>
					  </c:choose>
					  <c:choose>
						<c:when test="${status == 'CANCELLED'}">
						  <option name="itemStatus"  value="CANCELLED" selected="selected">CANCELLED</option>
						</c:when>
						<c:otherwise>
						  <option name="itemStatus" value="CANCELLED">CANCELLED</option>
						</c:otherwise>
					  </c:choose>
			</c:if>
			<c:if test="${company.name eq 'hbc'}">
					
					<option name="itemStatus"  value="" <c:if test="${status eq 'OK' }">selected</c:if>>NEW</option>
				
					<option name="itemStatus"  value="IN PROCESS" <c:if test="${status eq 'IN PROCESS' }">selected</c:if>>IN PROCESS</option>
					 
					<option name="itemStatus"  value="FOR DELIVERY" <c:if test="${status eq 'FOR DELIVERY' }">selected</c:if>>FOR DELIVERY</option>
					
					<option name="itemStatus"  value="DELIVERY IN TRANSIT"  <c:if test="${status eq 'DELIVERY IN TRANSIT' }">selected</c:if>>DELIVERY IN TRANSIT</option>
					
					<option name="itemStatus"  value="DELIVERED"  <c:if test="${status eq 'DELIVERED' }">selected</c:if>>DELIVERED</option>
					
			</c:if>
			</select>
		  </td>	
		</tr>
	  </s:iterator>
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  	</c:otherwise>
	  </c:choose>
	  
	  
	  
	</table> 
	<input type="button" value="Update" onclick="updateItemStatus(${cartOrder.id})" class="btnBlue"><br/><br/>
	<ul class="pagination">
	  <%@include file="includes/pagingnew.jsp"  %>
    </ul>
    
    <c:if test="${not empty cartOrder.prescription 
    	and company.id ne 319}">
      <div class="pageTitle">
        Prescription
      </div>
<%--      
      <c:choose>
        <c:when test="">
          <img src="images/page.png"/>
        </c:when>
        <c:when test="">
          <img src="images/page_white_word.png"/>
        </c:when>
        <c:when test="">
          <img src="images/page_white_acrobat.png"/>
        </c:when>
      </c:choose>
--%>  
	  <table border="0" cellpadding="0" cellspacing="0">
	    <tr>    
      	  <td valign="middle"><a href="${httpServer}/message_attachments/${cartOrder.prescription}"><img height="30" width="30" src="images/iDownload.png"/></a></td>
      	  <td valign="middle"><a href="${httpServer}/message_attachments/${cartOrder.prescription}">Download Prescription</a></td>
      	</tr>
      </table>
    </c:if> 
  </div><!--//mainContent-->

  <c:if test="${user.userType.value == 'Super User'  or user.userType.value == 'WTG Administrator' or user.userType.value == 'Company Administrator' or user.userType.value == 'Company Staff' and company.name == 'hiprecisiononlinestore'}">
    <div class="sidenav">
	  <!-- id --><h2>Order Details</h2>
 	  <ul>
 	    <li>ID :<s:property value="cartOrder.id"/></li>
 	    <li>
 	      Order Status: 
 	      
			<c:choose>
	 	      	<c:when test="${company.name eq 'hbc'}">
		 	      	 <select>
							
							<option name="orderStatus_${cartOrder.id}"  value="NEW" <c:if test="${cartOrder.status  eq 'NEW' }">selected</c:if>>NEW</option>
						
							<option name="orderStatus_${cartOrder.id}"  value="IN_PROCESS" <c:if test="${cartOrder.status  eq 'IN_PROCESS' }">selected</c:if>>IN PROCESS</option>
							 
							<option name="orderStatus_${cartOrder.id}"  value="FOR_DELIVERY" <c:if test="${cartOrder.status  eq 'FOR_DELIVERY' }">selected</c:if>>FOR DELIVERY</option>
							
							<option name="orderStatus_${cartOrder.id}"  value="DELIVERY_IN_TRANSIT"  <c:if test="${cartOrder.status  eq 'DELIVERY_IN_TRANSIT' }">selected</c:if>>DELIVERY IN TRANSIT</option>
							
							<option name="orderStatus_${cartOrder.id}"  value="DELIVERED"  <c:if test="${cartOrder.status  eq 'DELIVERED' }">selected</c:if>>DELIVERED</option>
							
					</select>
				</c:when>
 	      
	 	      	<c:when test="${company.id eq 319 or company.id eq 346}">
			 	      <select id="gurkkaOrderStatus" ${cartOrder.status eq 'COMPLETED' or cartOrder.status eq 'CANCELLED' ? 'disabled' : ''}>
					    <c:choose>
						  <c:when test="${cartOrder.status == 'PENDING'}">
			 			    <option name="orderStatus_${cartOrder.id}" value="PENDING" selected="selected">PENDING</option>
						  </c:when>
					      <c:otherwise>
						    <option name="orderStatus_${cartOrder.id}" value="PENDING">PENDING</option>
						  </c:otherwise>
					    </c:choose>
					    <c:choose>
			 			  <c:when test="${cartOrder.status == 'COMPLETED'}">
			 			    <option name="orderStatus_${cartOrder.id}" value="COMPLETED" selected="selected" disabled>COMPLETED</option>
						  </c:when>
						  <c:otherwise>
						    <option name="orderStatus_${cartOrder.id}" value="COMPLETED">COMPLETED</option>
						  </c:otherwise>
					    </c:choose>
					    <c:choose>
						  <c:when test="${cartOrder.status == 'CANCELLED'}">
			  			    <option name="orderStatus_${cartOrder.id}" value="CANCELLED" selected="selected">CANCELLED</option>
						  </c:when>
						  <c:otherwise>
						    <option name="orderStatus_${cartOrder.id}" value="CANCELLED">CANCELLED</option>
						  </c:otherwise>
					    </c:choose>
					  </select>
				</c:when>
 	      
	 	        <c:otherwise>
			 	      <select>
					    <c:choose>
						  <c:when test="${cartOrder.status == 'NEW'}">
						    <option name="orderStatus_${cartOrder.id}" value="NEW" selected="selected">NEW</option>
						  </c:when>
						  <c:otherwise>
			 			    <option name="orderStatus_${cartOrder.id}" value="NEW">NEW</option>
						  </c:otherwise>
					    </c:choose>
					    <c:choose>
						  <c:when test="${cartOrder.status == 'PENDING'}">
			 			    <option name="orderStatus_${cartOrder.id}" value="PENDING" selected="selected">PENDING</option>
						  </c:when>
					      <c:otherwise>
						    <option name="orderStatus_${cartOrder.id}" value="PENDING">PENDING</option>
						  </c:otherwise>
					    </c:choose>
					    <c:choose>
			 			  <c:when test="${cartOrder.status == 'COMPLETED'}">
			 			    <option name="orderStatus_${cartOrder.id}" value="COMPLETED" selected="selected">COMPLETED</option>
						  </c:when>
						  <c:otherwise>
						    <option name="orderStatus_${cartOrder.id}" value="COMPLETED">COMPLETED</option>
						  </c:otherwise>
					    </c:choose>
					    <c:choose>
						  <c:when test="${cartOrder.status == 'CANCELLED'}">
			  			    <option name="orderStatus_${cartOrder.id}" value="CANCELLED" selected="selected">CANCELLED</option>
						  </c:when>
						  <c:otherwise>
						    <option name="orderStatus_${cartOrder.id}" value="CANCELLED">CANCELLED</option>
						  </c:otherwise>
					    </c:choose>
					  </select>
				</c:otherwise>
			
			</c:choose>
        </li>
 	    <li>
 	      Payment Status: 
 	      <select id="paymentStatus"
 	      		<c:if test="${(company.id eq 319 or company.id eq 346)
 	      			and (cartOrder.status eq 'COMPLETED' or cartOrder.status eq 'CANCELLED')}">
 	      			disabled
 	      		</c:if>
 	      	>
		    <c:choose>
			  <c:when test="${cartOrder.paymentStatus == 'PENDING'}">
			    <option value="PENDING" selected="selected">PENDING</option>
			  </c:when>
			  <c:otherwise>
 			    <option value="PENDING">PENDING</option>
			  </c:otherwise>
		    </c:choose>
		    <c:choose>
			  <c:when test="${cartOrder.paymentStatus == 'PAID'}">
 			    <option value="PAID" selected="selected">PAID</option>
			  </c:when>
		      <c:otherwise>
			    <option value="PAID">PAID</option>
			  </c:otherwise>
		    </c:choose>
		  </select>
		</li>
 	    <li>
 	      Shipping Status: 
 	      <select id="shippingStatus"
 	      		<c:if test="${(company.id eq 319 or company.id eq 346)
 	      			and (cartOrder.status eq 'COMPLETED' or cartOrder.status eq 'CANCELLED')}">
 	      			disabled
 	      		</c:if>
 	      	>
		    <c:choose>
			  <c:when test="${cartOrder.shippingStatus == 'PENDING'}">
			    <option value="PENDING" selected="selected">PENDING</option>
			  </c:when>
			  <c:otherwise>
 			    <option value="PENDING">PENDING</option>
			  </c:otherwise>
		    </c:choose>
		    <c:choose>
			  <c:when test="${cartOrder.shippingStatus == 'IN_TRANSIT'}">
 			    <option value="IN_TRANSIT" selected="selected">IN TRANSIT</option>
			  </c:when>
		      <c:otherwise>
			    <option value="IN_TRANSIT">IN TRANSIT</option>
			  </c:otherwise>
		    </c:choose>
		    <c:choose>
			  <c:when test="${cartOrder.shippingStatus == 'DELIVERED'}">
 			    <option value="DELIVERED" selected="selected">DELIVERED</option>
			  </c:when>
		      <c:otherwise>
			    <option value="DELIVERED">DELIVERED</option>
			  </c:otherwise>
		    </c:choose>		    	    
		  </select>
		</li>
		<li>
		  <input type="button" value="Update" onclick="updateStatus(${cartOrder.id})" class="btnBlue">
		</li>
      </ul>
 	  </table>
	  <h2>Shipping Info</h2>
	  <ul>
		<li> Name :
		  <s:property value="cartOrder.name"/>
		</li>
		<li> Address 1:
		  <s:property value="cartOrder.address1"/>
		</li>
		<li> Address 2:
		  <s:property value="cartOrder.address2"/>
		</li>
		<li> City :
		  <s:property value="cartOrder.city"/>
		</li>
		<li> Country :
		  <s:property value="cartOrder.country"/>
		</li>
		<li> State :
		  <s:property value="cartOrder.state"/>
		</li>
		<li> Zip Code :
		  <s:property value="cartOrder.zipCode"/>
		</li>
		<li> Phone Number :
		  <s:property value="cartOrder.phoneNumber"/>
		</li>
		<li> Email Address :
		  <s:property value="cartOrder.emailAddress"/>
		</li>
		<li> Comments :
		  <s:property value="cartOrder.comments"/>
		</li>
		<c:if test="${company.name eq 'mraircon'}">
		  <li>${cartOrder.prescription}</li>
		</c:if>
	  </ul>
	  <h2>Prices</h2>
	  <ul>
		
		<c:choose>
			<c:when test="${company.name eq 'gurkkatest'}" >
				<c:set var="gurkkaPromoCodeDiscount" value="${0.0}"/>
				<c:if test="${not empty cartOrder.cartOrderPromoCode }">
					<c:set var="gurkkaPromoCodeDiscount" value="${cartOrder.cartOrderPromoCode.appliedDiscount}"/>
				</c:if>
			
				<li>
				  Shipping Price: <fmt:formatNumber type="number" pattern="###,###,##0.00" value="${cartOrder.gurkkaTotalDoublePrice - gurkkaTotalPrice + gurkkaPromoCodeDiscount}" /> 
				</li>
				<li>
				  Total Item Price: <fmt:formatNumber type="number" pattern="###,###,##0.00" value="${gurkkaTotalPrice}" />
				</li>
				<li>
				  Promo Code Discount: (<fmt:formatNumber type="number" pattern="###,###,##0.00" value="${cartOrder.gurkkaPromoCodeDiscount}" /> )
				</li>
				<li>
				  Total Price: <fmt:formatNumber type="number" pattern="###,###,##0.00" value="${cartOrder.gurkkaTotalDoublePrice}" />
				</li>
			</c:when>
			<c:otherwise>
				<li>
				  Shipping Price: ${cartOrder.totalShippingPrice2} 
				</li>
				<li>	
				  Total Item Price:${cartOrder.totalPrice}
				</li>
				<li>
				  Total Price:${cartOrder.totalPrice + cartOrder.totalShippingPrice2}
				</li>
			</c:otherwise>
		</c:choose>
		
	  </ul>
	</div><!--//sidenav-->
  </c:if>
</div>
		
	<div class="clear"></div>

  </div><!--//container-->
</body>
</html>