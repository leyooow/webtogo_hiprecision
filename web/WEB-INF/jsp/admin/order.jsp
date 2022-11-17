<%@include file="includes/header.jsp"  %>
<%@page import="java.util.*"%>
<link href="../admin/css/modal.css" rel="stylesheet" type="text/css" />

<script src="../admin/javascripts/prototype.js" type="text/javascript"></script>
<script src="../admin/javascripts/scriptaculous.js" type="text/javascript"></script>
<script language="JavaScript" src="../javascripts/overlib.js"></script>

<c:choose>
	<c:when test="${company.name eq 'hiprecision' 
					or company.name eq 'hiprecisiononlinestore' }">
		<script type='text/javascript' src='https://www.hi-precision.com.ph/dwr/interface/DWROrderAction.js'></script>
		<script type='text/javascript' src='https://www.hi-precision.com.ph/dwr/engine.js'></script>
	</c:when>
	<%-- <c:when test="${company.name eq 'gurkkatest'}">
		<script type='text/javascript' src='${fn:replace(contextParams['HTTP_SERVER'], 'http:', 'https:')}/dwr/interface/DWROrderAction.js'></script>
		<script type='text/javascript' src='${fn:replace(contextParams['HTTP_SERVER'], 'http:', 'https:')}/dwr/engine.js'></script>
	</c:when> --%>
	<c:otherwise>
		<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/interface/DWROrderAction.js'></script>
		<script type='text/javascript' src='${contextParams['HTTP_SERVER']}/dwr/engine.js'></script>
	</c:otherwise>
</c:choose>

<c:set var="menu" value="order" />
<c:set var="submenu" value="order" />
<c:set var="pagingAction" value="order.do" />

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
		
		function findMembersOrder(){
			var memberId=document.getElementById("memberSelection").value;
			var statusNotes=document.getElementById("statusNotes").value
			<c:choose>
				<c:when test="${company.name eq 'gurkkatest'}">
					
					DWROrderAction.findMembersOrdersByMemberAndRewardStatusNotes(memberId,statusNotes,{
						callback:function(messageContent){
							displayOrders(messageContent)
						}
					});
					
					if(memberId == "0"){
						document.getElementById("paginationTop").style.display="block";
						document.getElementById("paginationBottom").style.display="block";
					}
					else{
						document.getElementById("paginationTop").style.display="none";
						document.getElementById("paginationBottom").style.display="none";
					}
					
					document.getElementById("member_id_").value = memberId;
					
				</c:when>
				<c:otherwise>
					DWROrderAction.findMembersOrders(memberId,{
						callback:function(messageContent){
							displayOrders(messageContent)
						}
					});
					
					if(memberId == "0"){
						document.getElementById("paginationTop").style.display="block";
						document.getElementById("paginationBottom").style.display="block";
					}
					else{
						document.getElementById("paginationTop").style.display="none";
						document.getElementById("paginationBottom").style.display="none";
					}
					
					document.getElementById("member_id_").value = memberId;
				</c:otherwise>
			</c:choose>
			
		}
		
		function displayOrders(messageContent){
			if(document.getElementById("orderListContainer").style.display=='none'){
				Effect.Appear('orderListContainer', { duration: 0.30 });
			}else{
				document.getElementById("orderListContainer").style.display='none'
				Effect.Appear('orderListContainer',{ duration: 0.50 })
			}

			filterMembersOrderContainer = document.getElementById("orderListContainer")
			filterMembersOrderContainer.innerHTML = messageContent
		}

		function showMessageContent(messageContent) {
			currentOrderList = document.getElementById("ModalBox");
			currentOrderList.innerHTML = "<a href='#' onclick='closeModal()'"
				+ " style=\"float:  right;\">" 
				+ "<img src=\"images\/x.gif\" alt=\'X\'></a>"
				+ messageContent;
			Effect.Appear('OverlayContainer');
		}
		
		function updateStatus(cartOrderID){
			statusList = document.getElementsByName("orderStatus_" + cartOrderID);
			for(index = 0; index < statusList.length; index++){
				if(statusList[index].selected){
					orderStatus = statusList[index].value;	
					DWROrderAction.updateOrderStatus(cartOrderID, orderStatus, {
						callback:function(messageContent){
							alert(messageContent);
						}
					});
				}
			}
		}
		
		function getShippingInfo(cartOrderID){
			DWROrderAction.getShippingInfo(cartOrderID,{
				callback:function(messageContent){
					showMessageContent(messageContent);
				}
			});
		}
		
		function getHiPreOnlineStoreShippingInfo(cartOrderID){
			DWROrderAction.getHiPreOnlineStoreShippingInfo(cartOrderID,{
				callback:function(messageContent){
					showMessageContent(messageContent);
				}
			});
		}

		function getSwapShippingInfo(cartOrderID){
			DWROrderAction.getSwapShippingInfo(cartOrderID,{
				callback:function(messageContent){
					showMessageContent(messageContent);
				}
			});
		}

		function showShippingInfo(id){
			var content = "";
			
			content += document.getElementById('name_'+id).innerHTML;
			content += "\n" + document.getElementById('name_'+id).innerHTML;
		}
		
		function filterList() {
			var e = document.getElementById("branch-filter");
			var strUser = e.options[e.selectedIndex].value;
			if(strUser == "") {
				window.location.href="order.do";
				document.getElementById('branch').value="";
			}
			else {
				window.location.href="order.do?branch="+strUser;
				document.getElementById('branch').value=strUser;
			}
		}
	</script>
	
<body>
<div class="container">
<%@include file="includes/bluetop.jsp"%>
<%@include file="includes/bluenav.jsp"%>
	
<script language="javascript" src="../javascripts/overlib.js"></script>
<script type="text/javascript">
	function showContent(id) {
		var content = document.getElementById('showContent_'+id).innerHTML;
		try{
			overlib(content, STICKY, NOCLOSE);
		}catch(e){
			alert(content);
		}
	}
</script>

<div class="contentWrapper" id="contentWrapper">
<!-- --Note:this form  ="downloadOrderList.do" is for the sake of downloading Individuals Orders-->

<%
List<String> list; 
//default settings
list= Arrays.asList("Order Id", "Order Date", "Items Total Price","Status", "Order Items Details","Order Status");
request.setAttribute("list", list);
%>

<form ${company.name eq 'hiprecisiononlinestore' ? 'action="downloadHiPreOnlineStoreOrderExcel.do"' : 'action="downloadOrderExcel.do"' } method="post">
  <c:forEach items="${list}" var="orderCaption">
	<input type="hidden" name="fieldName" value="${orderCaption}"/>
  </c:forEach>
	
  <div class="content">
    <s:actionmessage />
	<s:actionerror />
		
	<div class="pageTitle">
	  <h1><strong>Orders</strong>: View Order List</h1>		
	  <div class="clear"></div>
	</div><!--//pageTitle-->
	  
	<div class="downloadsBox">
	  <input type="hidden" name="company_id" value="${company.id}"/>
	  <input type="hidden" name="member_id_" id="member_id_" class="member_id_" value="0" />
	  <table border="0" cellspacing="0" cellpadding="5">
		<tr>
		  <td>DOWNLOAD IN EXCEL FORMAT&nbsp; &nbsp; </td>
		  <td><input type="radio" name="filter" value="all" checked="checked" /></td>
		  <td>ALL</td>
		  <td><input type="radio" name="filter" value="byDate"  /></td>
		  <td>FROM</td>
		  <td>
			<fmt:formatDate pattern="MM-dd-yyyy" value="${singlePage.itemDate}" var="idate"/>
			<input type="text" id="fromDate" name="fromDate" value="${idate}"/> 
			<a href="javascript:;" id="fromDateButton"><img src="images/iDate.gif" style="border: 0px" alt="Open Calendar" align="absmiddle"/></a>

			<script type="text/javascript">
 		      Calendar.setup({
		        inputField     :    "fromDate",     // id of the input field
				ifFormat       :    "%m-%d-%Y",      // format of the input field
				button         :    "fromDateButton",  // trigger for the calendar (button ID)
				align          :    "Tl",           // alignment (defaults to "Bl")
				singleClick    :    true
			  });
			</script>
		  </td>
		  <td>TO</td>
		  <td>
			<fmt:formatDate pattern="MM-dd-yyyy" value="${singlePage.itemDate}" var="idate"/>
			<input type="text" id="toDate" name="toDate" value="${idate}"/> 
			<a href="javascript:;" id="toDateButton"><img src="images/iDate.gif" style="border: 0px" alt="Open Calendar" align="absmiddle"/></a>

			<script type="text/javascript">
 		      Calendar.setup({
		        inputField     :    "toDate",     // id of the input field
				ifFormat       :    "%m-%d-%Y",      // format of the input field
				button         :    "toDateButton",  // trigger for the calendar (button ID)
				align          :    "Tl",           // alignment (defaults to "Bl")
				singleClick    :    true
			  });
			</script>
		  </td>
		  <td><input type="submit" value="Download" class="btnBlue"></td>
		</tr>
	  </table>
	</div><!--//downloadsBox-->		  
	  
	<div style="float:left;width:50%;padding: 15px 0px 15px 0px;">
	
		<div ${company.name eq 'hiprecisiononlinestore' ? 'style="display:none"' : ''}>
		<strong>View Member's Order : &nbsp;</strong>
		
		<select name="member" style="width:200px" id="memberSelection"  onchange="findMembersOrder()">
			<option value="0">-members-</option>
			<c:forEach items="${members}" var="member">
				<option value="${member.id}">${member.fullName}</option>
			</c:forEach>
		</select>
		</div>
		
		<c:choose>
			<c:when test="${fn:containsIgnoreCase(company.name, 'gurkkatest')}">
				<br/><br/>&nbsp;
				<strong>Record Type : &nbsp;</strong>
				<select id = "statusNotes" name="statusNotes" style="width:200px"  onchange="findMembersOrder()">
					<option value="">Product Order</option>
					<option value="Reward">Reward Order</option>
				</select>
				
			</c:when>
			<c:when test="${company.name eq 'hiprecisiononlinestore' }">
			  	<input type="hidden" id="branch" name="branch" value="${param.branch }"/>
			  	<strong>Filter By Branch &nbsp; &nbsp; </strong>
			  		<select id="branch-filter" >
			  			<option value="">Please Select</option>
			  			<c:forEach items="${singlePages }" var="item">
			  				<option ${param.branch eq item.name ? 'selected' : '' } value="${item.name }">${item.name }</option>
			  			</c:forEach>
			  		</select>
			  		&nbsp; &nbsp; <input type="button" value="Go" class="btnBlue" onclick="filterList()">
			</c:when>
			<c:otherwise>
				<%-- DO NOTHING --%>
			</c:otherwise>
		</c:choose>
	</div>
	
	  	
	<ul class="pagination" id="paginationTop">
	  <%@include file="includes/pagingnew.jsp"  %>
	</ul>
			
	<div class="clear"></div>
	<div id="orderListContainer">
	  <table width="100%" border="0" cellspacing="0" cellpadding="8" class="companiesTable">
	    <tr>
		  <th> Order Date </th>
		  <c:choose>
		  <c:when test="${company.name eq 'hiprecisiononlinestore' }">
		  	 <th> Order Number </th>
		  </c:when>
		  <c:otherwise>
		  	 <th> ID </th>
		  </c:otherwise>
		  </c:choose>
		  <th> Customer </th>
		  <th> Shipping Info </th>
		  <th> Items Price </th>
		  <th> Shipping Info </th>
		  <th> Order Status </th>	
		  <th> Payment Status </th>	
		  
		  <c:if test="${fn:contains(company.name, 'purenectar')}">
			  <th> Payment Type </th>
			  
		  </c:if>
		  
		  <c:if test="${company.name eq 'hiprecisiononlinestore' }">
				<th> Branch</th>
			</c:if>
			
			
		  <c:if test="${fn:contains(company.name, 'gurkka')}">
			  <th> Payment Type </th>
			  <th> Shipping Type </th>
		  </c:if>
		  
		  <c:if test="${fn:contains(company.name, 'mraircon')}">
			  <th> Payment Type </th>
		  </c:if>
		  
		  <c:if test="${fn:contains(company.name, 'tomato')}">
			  <th> Payment Type </th>
			  <th> Reference Number </th>
		  </c:if>

		  <th> Action </th>				
	    </tr>
	    <c:set var="count" value="0" />
	    <!-- TABLE CONTENTS (Orders) -->
	    <s:iterator value="orderList">
	    	<c:choose>
	    	<c:when test="${company.name eq 'gurkkatest' and empty items }">
	    		<!-- do not display any -->
	    	</c:when>
	    	<c:otherwise>
		      <s:if test="isValid == true">
			    <tr ${(count%2 == 1) ? 'class="oddRow"' : ''}>
			      <c:set var="count" value="${count+1}" />
			      <td>
				    <s:property value="createdOn"/>
			      </td>
			      <td style="text-align: center;">
					<c:choose>
						<c:when test="${fn:contains(company.name, 'gurkka')}">
							<s:property value="getTransactionNumber()"/>
						</c:when>
						<c:when test="${fn:contains(company.name, 'hiprecisiononlinestore')}">
							<s:property value="getTransactionNumber()"/>
						</c:when>
						<c:otherwise>
							<s:property value="id"/>
						</c:otherwise>
					</c:choose>
			      </td>
			      <td>
			      	<c:choose>
			      		<c:when test="${fn:contains(company.name, 'gurkkatest') }">
			      			<s:property value="getMemberName()" /><br/>
			      		</c:when>
			      		<c:otherwise>
			      			<s:property value="name" /><br/>
			      		</c:otherwise>
			      	</c:choose>
			      </td>
			      <td>
			      	<c:choose>
			      		<c:when test="${fn:contains(company.name, 'swapcanada')}">
				    		<a href="#" onClick="getSwapShippingInfo(${id})">View</a>
				    	</c:when>
				    	<c:when test="${fn:contains(company.name, 'hiprecisiononlinestore') }">
				    		<a href="#" onClick="getHiPreOnlineStoreShippingInfo(${id})">View</a>
				    	</c:when>
				    	<c:otherwise>	
				    		<a href="#" onClick="getShippingInfo(${id})">View</a>
				    	</c:otherwise>
				    </c:choose>
			      </td>
			      	
			      <td>
			      	<c:choose>
			      		<c:when test="${fn:contains(company.name, 'tomato')}">
				    		<s:property value="getTomatoTotalPriceFormatted()"/>
				    	</c:when>
			      		<c:when test="${fn:contains(company.name, 'swapcanada')}">
				    		<s:property value="getSwapCanadaTotalPriceFormatted()"/>
				    	</c:when>
				    	<c:when test="${fn:contains(company.name, 'gurkkatest')}">
				    		<s:property value="getGurkkaTotalPriceFormatted()"/>
				    	</c:when>
				    	<c:otherwise>
				    		<s:property value="getTotalPriceFormatted()"/>
				    	</c:otherwise>
				    </c:choose>
			      </td>
			      <td>
			        <s:property value="getTotalShippingPrice2()"/>
			      </td>
			      <td>
				    <s:property value="status"/>
			      </td>
			      <td>
				    <s:property value="paymentStatus"/>
			      </td>
			      
			      <c:if test="${company.name eq 'hiprecisiononlinestore' }">
						<td> <s:property value="getShippingInfo().getShippingInfo().getAddress1()"/></td>
					</c:if>
					
			      <c:if test="${fn:contains(company.name, 'gurkka')}">
				      <td>
					    <s:property value="paymentType"/>
				      </td>
				      <td>
					    <s:property value="shippingType"/>
				      </td>
			      </c:if>
			      <c:if test="${fn:contains(company.name, 'mraircon')}">
				      <td>
					    <s:property value="paymentType"/>
					   
				      </td>
			      </c:if>
			       <c:if test="${fn:contains(company.name, 'purenectar')}">
				      <td>
					    <s:property value="getPaymentType()"/>
				      </td>
			      </c:if>
			      <c:if test="${fn:contains(company.name, 'tomato')}">
				      <td>
					    <s:property value="paymentType"/>
				      </td>
				      <td>
					    <s:property value="transactionNumber"/>
				      </td>
			      </c:if>
			      <td>
				    <a href="#" onclick="showOrderList(${id})">View</a> | <a href="orderdetail.do?order_id=${id}" >Edit</a> |

				    <c:choose> 
				      <c:when test="${company.id eq 152}">
				        <a href="HPDSdownloadOrder.do?cart_order_id=${id}" target="_blank">
				      </c:when>
				      <c:when test="${company.id eq 268}">
				        <a href="giftcardDownloadOrder.do?cart_order_id=${id}" target="_blank">
				      </c:when>
				      <c:when test="${company.id eq 247}">
				        <a href="ecommerceDownloadOrder.do?cart_order_id=${id}" target="_blank">
				      </c:when>
				      <c:when test="${company.id eq 346 }">
				      	<a href="gurkkaDownloadOrder.do?cart_order_id=${id}" target="_blank">
				      </c:when>
				      <c:otherwise>
				        <a href="downloadOrder.do?cart_order_id=${id}" target="_blank">
				      </c:otherwise>
				    </c:choose> 
			        PDF</a>
			      </td>
			    </tr>
		      </s:if>
		     </c:otherwise>
		    </c:choose>
	    </s:iterator>
      </table>
	</div>
	<div id="filterMembersOrderContainer" style="display:none">
	</div>
	
	<ul class="pagination" id="paginationBottom">
	   <%@include file="includes/pagingnew.jsp"  %>
    </ul>
  </div><!--//mainContent-->
</form>
</div>

<div class="clear"></div>

</div><!--//container-->

<div id="OverlayContainer" style="float: left; display: none; height: 100%;">
  <div id="ModalBox">
    <a href='#' onclick='closeModal()'>Close Me!</a>
  </div>
</div>
</body>
</html>