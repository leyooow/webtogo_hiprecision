<%@include file="includes/taglibs.jsp" %>

<c:set var="navMenu" value="login" />
<%@include file="includes/header.jsp" %>
<%@include file="includes/nav.jsp" %>
<div class="clear"></div>
<script type="text/javascript">
	function showMessageContent(messageContent) {
			currentOrderList = document.getElementById("ModalBox2");
			currentOrderList.innerHTML = "<a href='#' onclick='closeModal()'"
				+ " style=\"float:  right;\">" 
				+ "<img src=\"images\/x.gif\" alt=\'X\'></a>"
				+ messageContent;
			Effect.Appear('OverlayContainer2');
		}
	function closeModal() {
		Effect.Fade('OverlayContainer2');
	}
		
</script>
<div class="content2">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	    <tr valign="top">
		  <td>
		   
			<div class="mainContent3">
			<h1>${page.title}</h1>
			 
			  <div class="welcomeNote">
			  
			  		<h1>All Quotes</h1>
					<c:set var="actionDo" value="orderitem.do"/>
					<table width="100%" cellspacing="0" cellpadding="0" class="tblquote">
					
					<c:choose>
						<c:when test="${null != orderList }">			
									<tr>
										<th width="10%"> ID </th>
										<th width="20%"> Name </th>
										<th width="20%"> Remarks </th>
										<th width="30%"> Order Date </th>						
										<th width="20%"> Action </th>				
									</tr>
								
									<!-- TABLE CONTENTS (Orders) -->
									<%-- 
									<c:set var="size" value="${fn:length(orderList)-1}"/>
									<c:forEach begin="0" end="${size}" var="i" step="2">
									<c:if test="${orderList[size-i+1] ne null}">
										<tr class="alt">
											<td class="centertxt"><a href="orderList.do?cart_order_id=${orderList[size-i].id}">${orderList[size-i+1].id}</a></td>
											<td class="centertxt">${orderList[size-i+1].name}</td>
											<td class="centertxt"><button onMouseOver="showMessageContent('${orderList[size-i+1].comments}')" value="View"></button></td>
											<td class="centertxt">${orderList[size-i+1].createdOn}</td>		
											<td class="centertxt"><a href="quotesList.do?cart_order_id=${orderList[size-i+1].id}">View</a> | <a href="downloadOrder.do?cart_order_id=${orderList[size-i+1].id}">PDF</a>
											</td>
										</tr>
										</c:if>									
										<tr>
											<td class="centertxt"><a href="orderList.do?cart_order_id=${orderList[size-i].id}">${orderList[size-i].id}</a></td>
											<td class="centertxt">${orderList[size-i].name}</td>
											<td class="centertxt"><button onMouseOver="showMessageContent('${orderList[size-i].comments}')" value="View"></button></td>
											<td class="centertxt">${orderList[size-i].createdOn}</td>		
											<td class="centertxt"><a href="quotesList.do?cart_order_id=${orderList[size-i].id}">View</a> | <a href="downloadOrder.do?cart_order_id=${orderList[size-i].id}">PDF</a></td>
											
										</tr>
										
									</c:forEach>
									
									<!-- PAGING -->
									<tr>
										<td colspan="3">
											<div style="float:right">
											  	<c:set var="actionDo" value="order.do"/>
											  	<jsp:include page="../../../common/includes/paging-util.jsp" />    
										  	</div>  
								  		</td>
								  	</tr>
							--%>
									<c:forEach begin="0" end="${fn:length(orderListPaging)}" var="i" step="2">
										<tr class="alt">
											<td class="centertxt"><a href="orderList.do?cart_order_id=${orderListPaging[i].id}">${orderListPaging[i].id}</a></td>
											<td class="centertxt">${orderListPaging[i].name}</td>
											<td class="centertxt"><button onMouseOver="showMessageContent('${orderListPaging[i].comments}')" value="View"></button></td>
											<td class="centertxt">${orderListPaging[i].createdOn}</td>		
											<td class="centertxt"><a href="quotesList.do?cart_order_id=${orderListPaging[i].id}">View</a> | <a href="downloadOrder.do?cart_order_id=${orderListPaging[i].id}">PDF</a>
											</td>
										</tr>
										<c:if test="${orderListPaging[i+1] ne null }">
										<tr>
											<td class="centertxt"><a href="orderList.do?cart_order_id=${orderListPaging[i+1].id}">${orderListPaging[i+1].id}</a></td>
											<td class="centertxt">${orderListPaging[i+1].name}</td>
											<td class="centertxt"><button onMouseOver="showMessageContent('${orderListPaging[i+1].comments}')" value="View"></button></td>
											<td class="centertxt">${orderListPaging[i+1].createdOn}</td>		
											<td class="centertxt"><a href="quotesList.do?cart_order_id=${orderListPaging[i+1].id}">View</a> | <a href="downloadOrder.do?cart_order_id=${orderListPaging[i+1].id}">PDF</a>
											</td>
										</tr>
										</c:if>
									</c:forEach>
									
									<c:if test="${pagingUtilDesc.totalPages gt 1}">
							<tr>
							<td colspan="5">
						<c:set var="pagingAction" value="${menu['allQuotes'].url}"/>													
						<div>
			        		Page ${pagingUtilDesc.currentPageNo} of ${pagingUtilDesc.totalPages} <img src="images/iPage.gif" align="absmiddle" />&nbsp;
			        		<c:if test="${param.pageNumber gt 1}">
				        		<a href="${pagingAction}?pageNumber=1">&laquo; First</a> | &nbsp
				        		<a href="${pagingAction}?pageNumber=${param.pageNumber-1}">&laquo; Previous</a> | &nbsp
			        		</c:if>
			        		
							<c:forEach items="${pagingUtilDesc.pages}" var="pageNum">	 
								<c:set value="${fn:length(pagingUtilDesc.pages)}" var="total" />
								<c:choose>											
									<c:when test="${pagingUtilDesc.currentPageNo!= pageNum }">
										<a href="${pagingAction}?pageNumber=${pageNum}&tpages=${total}">${pageNum}</a>					
									</c:when>  
									<c:otherwise>
										<span style="color: red;">[${pageNum}]</span> 
									</c:otherwise> 
								</c:choose>		 
									<span class="small">-</span>			  
							</c:forEach>
							
							<c:if test="${param.pageNumber ne pagingUtilDesc.totalPages}">	
								| <a href="${pagingAction}?pageNumber=${not empty param.pageNumber ? param.pageNumber+1 : 2}&tpages=${pagingUtilDesc.totalPages}">Next &raquo;</a>
							 	| <a href="${pagingAction}?pageNumber=${pagingUtilDesc.totalPages}&tpages=${pagingUtilDesc.totalPages}">Last &raquo;</a>
							</c:if>	
						</div>
						</td>
						</tr>
					</c:if>
						
						
								</c:when>
						<c:otherwise>
							<ul><li>No Current Order</li></ul>
						</c:otherwise>
					</c:choose>
				  	</table>
			  			  
			  </div>
			
			  <%--<p align="right">For more info, Please send us an email at: <a href="/">info@hi-precision.com</a></p>
			  &nbsp; --%>
			</div><!--//mainContent-->
			
		  </td>
		</tr>
	  </table>
	</div><!--//content-->
	<div id="OverlayContainer2" style="float: left; display: none;">
		<div id="ModalBox2">
			<a href='#' onclick='closeModal()'>Close Me!</a>
		</div>
	</div>
<%@include file="includes/footer.jsp" %>